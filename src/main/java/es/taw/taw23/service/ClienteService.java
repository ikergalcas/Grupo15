package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Divisa;
import es.taw.taw23.entity.*;
import es.taw.taw23.ui.MovimientoCambioDivisa;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    protected DivisaRepository divisaRepository;
    @Autowired
    protected CuentaClienteRepository cuentaClienteRepository;
    @Autowired
    protected CambioDivisaRepository cambioDivisaRepository;
    @Autowired
    protected EstadoCuentaRepository estadoCuentaRepository;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected MovimientoRepository movimientoRepository;
    @Autowired
    protected TipoMovimientoRepository tipoMovimientoRepository;
    @Autowired
    protected RolClienteRepository rolClienteRepository;

    @Autowired
    protected IndividualRepository individualRepository;

    @Autowired
    protected EstadoSolicitudRepository estadoSolicitudRepository;

    @Autowired
    protected TipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    protected SolicitudRepository solicitudRepository;

    @Autowired
    protected GestorRepository gestorRepository;

    public Cliente buscarCliente(Integer id) {
        ClienteEntity individualEntity = this.individualRepository.findById(id).orElse(null);
        if(individualEntity != null) {
            return individualEntity.toDTO();
        } else {
            return null;
        }
    }

    /*
    public Cuenta buscarCuentaConIdCliente(Integer id){
        CuentaEntity cuentaIndividualEntity = this.cuentaRepository.buscarCuentaIndividualPorIdCliente(id);
        if(cuentaIndividualEntity != null) {
            return cuentaIndividualEntity.toDTO();
        } else {
            return null;
        }
    }
     */

     public Cuenta obtenerCuenta(Integer idCliente) {
       //ClienteEntity clienteEntity = this.individualRepository.findById(idCliente).orElse(null);
       //CuentaEntity cuenta = clienteEntity.getCuentaClientesById().get(0).getCuentaByCuentaId();

         List<CuentaClienteEntity> cuentaClienteList = this.cuentaClienteRepository.buscarCuentaClientePorIdCliente(idCliente);
         CuentaEntity cuenta = new CuentaEntity();

         for(CuentaClienteEntity cuentaCliente : cuentaClienteList) {
             if(cuentaCliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId().getId().equals(2)) {
                 cuenta = cuentaCliente.getCuentaByCuentaId();
             }
         }
         return cuenta.toDTO();
    }

    public String transferencia(MovimientoTransferencia transferencia) {
        MovimientoEntity movimiento = new MovimientoEntity();
        String error = "";

        //Primero pillo las cuentas y miro si no hay errores en los datos
        CuentaEntity cuentaOrigen = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaOrigen());
        CuentaEntity cuentaDestino = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaDestino());



        //cuenta no existe o esta bloqueada o inactiva
        if(cuentaDestino == null || transferencia.getImporte()<0) {
            error = "cuentaDestino";
        } else if(cuentaOrigen.getDinero() < transferencia.getImporte()) {
            error = "dineroInsuficiente";
        }else if(cuentaDestino.getEstadoCuentaByEstadoCuentaId().getEstadoCuenta().equals("bloqueada") || cuentaDestino.getEstadoCuentaByEstadoCuentaId().getEstadoCuenta().equals("inactiva")){
            error = "cuentaDestinoBloqueadaOInactiva";
        }else {
            TipoMovimientoEntity tipoMov = this.tipoMovimientoRepository.buscarTipoTransferencia();
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMov);

            Date fecha = new Date();
            Timestamp timestamp = new Timestamp(fecha.getTime());
            movimiento.setTimeStamp(timestamp);

            movimiento.setCuentaByCuentaDestinoId(cuentaDestino);
            movimiento.setCuentaByCuentaOrigenId(cuentaOrigen);
            movimiento.setDivisaByMonedaOrigenId(cuentaOrigen.getDivisaByDivisaId());
            movimiento.setDivisaByMonedaDestinoId(cuentaDestino.getDivisaByDivisaId());

            //La transferencia es sin cambio de divisa
            if(cuentaOrigen.getDivisaByDivisaId().getMoneda().equals(cuentaDestino.getDivisaByDivisaId().getMoneda())) {
                movimiento.setImporteDestino(transferencia.getImporte());
                movimiento.setImporteOrigen(transferencia.getImporte());
                cuentaOrigen.setDinero(cuentaOrigen.getDinero() - transferencia.getImporte());
                cuentaDestino.setDinero(cuentaDestino.getDinero() + transferencia.getImporte());
            } else {    //La transferencia es con cambio de divisa
                movimiento.setImporteOrigen(transferencia.getImporte());
                CambioDivisaEntity cambio = this.cambioDivisaRepository.buscarCambioDivisa(cuentaOrigen.getDivisaByDivisaId().getMoneda(), cuentaDestino.getDivisaByDivisaId().getMoneda());
                Double dineroMonedaDestino = transferencia.getImporte() * cambio.getCambio();
                movimiento.setImporteDestino(dineroMonedaDestino);
                cuentaOrigen.setDinero(cuentaOrigen.getDinero() - transferencia.getImporte());
                cuentaDestino.setDinero(cuentaDestino.getDinero() + dineroMonedaDestino);
            }

            this.cuentaRepository.save(cuentaOrigen);
            this.cuentaRepository.save(cuentaDestino);
            this.movimientoRepository.save(movimiento);
        }
        return error;
    }

    public List<Divisa> buscarDivisas() {
        List<DivisaEntity> divisaEntities = this.divisaRepository.findAll();

        return divisasADTO(divisaEntities);
    }



    private List<Divisa> divisasADTO(List<DivisaEntity> divisaEntities) {
        List<Divisa> divisas = new ArrayList<>();

        for(DivisaEntity entity : divisaEntities) {
            divisas.add(entity.toDTO());
        }

        return divisas;
    }

    public void cambioDivisa(MovimientoCambioDivisa divisa) {
        CuentaEntity cuenta = this.cuentaRepository.buscarCuentaPorNumeroCuenta(divisa.getCuenta());
        DivisaEntity divisaNueva = this.divisaRepository.buscarDivisaPorNombre(divisa.getDivisa());
        CambioDivisaEntity cambio = this.cambioDivisaRepository.buscarCambioDivisa(cuenta.getDivisaByDivisaId().getMoneda(), divisa.getDivisa());

        Double dineroMonedaNueva = cuenta.getDinero() * cambio.getCambio();

        MovimientoEntity movimiento = new MovimientoEntity();
        CuentaEntity cuentaOrigen = this.cuentaRepository.buscarCuentaPorNumeroCuenta(cuenta.getNumeroCuenta());
        CuentaEntity cuentaDestino = this.cuentaRepository.buscarCuentaPorNumeroCuenta(cuenta.getNumeroCuenta());
        TipoMovimientoEntity tipoMov = this.tipoMovimientoRepository.buscarTipoCambioDivisa();

        Date fecha = new Date();
        Timestamp timestamp = new Timestamp(fecha.getTime());
        movimiento.setTimeStamp(timestamp);
        movimiento.setTipoMovimientoByTipoMovimientoId(tipoMov);
        movimiento.setCuentaByCuentaDestinoId(cuentaDestino);
        movimiento.setCuentaByCuentaOrigenId(cuentaOrigen);
        movimiento.setImporteOrigen(cuentaOrigen.getDinero());
        movimiento.setImporteDestino(dineroMonedaNueva);
        movimiento.setDivisaByMonedaOrigenId(cuentaOrigen.getDivisaByDivisaId());
        movimiento.setDivisaByMonedaDestinoId(divisaNueva);

        cuenta.setDivisaByDivisaId(divisaNueva);
        cuenta.setDinero(dineroMonedaNueva);

        this.cuentaRepository.save(cuenta);
        this.movimientoRepository.save(movimiento);
    }

    public void guardarCliente(Cliente editado) {
        ClienteEntity cliente = this.individualRepository.findById(editado.getId()).orElse(null);

        cliente.setId(editado.getId());
        cliente.setNif(editado.getNif());
        cliente.setPrimerNombre(editado.getPrimerNombre());
        cliente.setSegundoNombre(editado.getSegundoNombre());
        cliente.setPrimerApellido(editado.getPrimerApellido());
        cliente.setSegundoApellido(editado.getSegundoApellido());
        cliente.setFechaNacimiento(editado.getFechaNacimiento());
        cliente.setNumero(editado.getNumero());
        cliente.setPuerta(editado.getPuerta());
        cliente.setCiudad(editado.getCiudad());
        cliente.setCalle(editado.getCalle());
        cliente.setPais(editado.getPais());
        cliente.setRegion(editado.getRegion());
        cliente.setCp(editado.getCp());

        this.individualRepository.save(cliente);
    }

    //cambio estado cuenta
    public void solicitarDesbloqueo(Integer idCliente) {
        ClienteEntity cliente = this.individualRepository.findById(idCliente).orElse(null);
        SolicitudEntity solicitud = new SolicitudEntity();

        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoDesbloqueo = this.tipoSolicitudRepository.buscarTipoDesbloqueoIndividual();
            //desbloqueo_individual

        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoDesbloqueo);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);
    }
    public void solicitarActivacion(Integer idCliente) {
        ClienteEntity cliente = this.individualRepository.findById(idCliente).orElse(null);
        SolicitudEntity solicitud = new SolicitudEntity();

        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoActivacion = this.tipoSolicitudRepository.buscarTipoActivacionIndividual();
            //activa_individual

        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoActivacion);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);
    }
    protected EmpleadoEntity buscarGestorMenosOcupado() {
        List<EmpleadoEntity> gestores = this.gestorRepository.buscarGestores();

        int min = Integer.MAX_VALUE;
        EmpleadoEntity gestorElegido = null;
        for(EmpleadoEntity gestor : gestores) {
            List<SolicitudEntity> solicitudes = this.solicitudRepository.buscarSolicitudesPendientesDeUnGestor(gestor.getId());
            if(solicitudes.size() < min) {
                min = solicitudes.size();
                gestorElegido = gestor;
            }
        }
        return gestorElegido;
    }

    public boolean comprobarSolicitudDesbloqueoEnviada(Integer id) {
        List<SolicitudEntity> solicitudesCliente = this.solicitudRepository.buscarSolicitudesPendientesPorClienteTipoDesbloqueoIndividual(id);
        return solicitudesCliente.size() > 0;
    }
    public boolean comprobarSolicitudActivacionEnviada(Integer id) {
        List<SolicitudEntity> solicitudesCliente = this.solicitudRepository.buscarSolicitudesPendientesPorClienteTipoActivacionIndividual(id);
        return solicitudesCliente.size() > 0;
    }

    public void registrarCliente(Cliente editado) {
        ClienteEntity cliente = new ClienteEntity();
        RolClienteEntity rol = this.rolClienteRepository.buscarRol("individual");

       // cliente.setId(editado.getId());
        cliente.setNif(editado.getNif());
        cliente.setPrimerNombre(editado.getPrimerNombre());
        cliente.setSegundoNombre(editado.getSegundoNombre());
        cliente.setPrimerApellido(editado.getPrimerApellido());
        cliente.setSegundoApellido(editado.getSegundoApellido());
        cliente.setFechaNacimiento(editado.getFechaNacimiento());
        cliente.setNumero(editado.getNumero());
        cliente.setPuerta(editado.getPuerta());
        cliente.setCiudad(editado.getCiudad());
        cliente.setCalle(editado.getCalle());
        cliente.setPais(editado.getPais());
        cliente.setRegion(editado.getRegion());
        cliente.setCp(editado.getCp());
        cliente.setContrasena(editado.getContrasena());
        cliente.setRolClienteByRolclienteId(rol);

        this.individualRepository.save(cliente);

        SolicitudEntity solicitud = new SolicitudEntity();
        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoAlta = this.tipoSolicitudRepository.buscarTipoAltaIndividual();
        //activa_individual
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoAlta);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);
    }

}