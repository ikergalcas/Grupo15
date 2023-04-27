package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.*;
import es.taw.taw23.entity.*;
import es.taw.taw23.dto.Empresa;
import es.taw.taw23.ui.MovimientoCambioDivisa;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    protected SolicitudRepository solicitudRepository;
    @Autowired
    protected GestorRepository gestorRepository;
    @Autowired
    protected EstadoSolicitudRepository estadoSolicitudRepository;
    @Autowired
    protected TipoSolicitudRepository tipoSolicitudRepository;
    @Autowired
    protected DivisaRepository divisaRepository;
    @Autowired
    protected CuentaClienteRepository cuentaClienteRepository;
    @Autowired
    protected CambioDivisaRepository cambioDivisaRepository;
    @Autowired
    protected AsociadoRepository asociadoRepository;
    @Autowired
    protected EstadoCuentaRepository estadoCuentaRepository;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected EmpresaRepository empresaRepository;
    @Autowired
    protected MovimientoRepository movimientoRepository;
    @Autowired
    protected TipoMovimientoRepository tipoMovimientoRepository;

    @Autowired
    protected RolClienteRepository rolClienteRepository;

    public Cliente buscarCliente(Integer id) {
        ClienteEntity asociadoEntity = this.asociadoRepository.findById(id).orElse(null);
        if(asociadoEntity != null) {
            return asociadoEntity.toDTO();
        } else {
            return null;
        }
    }

    public Empresa buscarEmpresa(Integer id) {
        EmpresaEntity empresaEntity = this.empresaRepository.findById(id).orElse(null);
        if(empresaEntity != null) {
            return empresaEntity.toDTO();
        } else {
            return null;
        }
    }

    public Cuenta buscarCuentaPorIBAN(String iban) {
        CuentaEntity cuentaEntity = this.cuentaRepository.buscarCuentaPorNumeroCuenta(iban);
        return cuentaEntity.toDTO();
    }

    public Empresa buscarEmpresaAPartirDeCliente(Cliente cliente) {

        EmpresaEntity empresa = empresaRepository.findById(cliente.getEmpresa()).orElse(null);

        return empresa.toDTO();
    }

    /**
     * En este metodo miro si la cuenta individual del asociado esta activa y si esta bloqueado o no de la empresa
     */
    public List<Cuenta> buscarCuentasAsociadoActivas(Integer id) {
        //Hago este cambio de CuentaClienteEntity -> CuentaEntity porque en CuentaRepository no me deja hacer un query
        //Buscando cuentas por el id del cliente. Supongo que sera por la tabla intermedia (CuentaClienteEntity)
        List<CuentaClienteEntity> cuentaClienteList = this.cuentaClienteRepository.buscarCuentasActivas(id);
        List<CuentaEntity> cuentaList = new ArrayList<>();
        ClienteEntity asociado = this.asociadoRepository.findById(id).orElse(null);
        boolean bloqueado = asociado.getAcceso() == 0;

        //Paso la lista al tipo CuentaEntity
        for(CuentaClienteEntity cuentaCliente : cuentaClienteList) {
            if(bloqueado && cuentaCliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId().getTipo().equals("empresa")) {
            } else {
                cuentaList.add(cuentaCliente.getCuentaByCuentaId());
            }
        }

        return listaCuentasADTO(cuentaList);
    }

    public List<Cuenta> buscarCuentasAsociado(Integer id) {
        List<CuentaClienteEntity> cuentaClienteList = this.cuentaClienteRepository.buscarCuentasCliente(id);
        List<CuentaEntity> cuentaList = new ArrayList<>();

        //Paso la lista al tipo CuentaEntity
        for(CuentaClienteEntity cuentaCliente : cuentaClienteList) {
            cuentaList.add(cuentaCliente.getCuentaByCuentaId());
        }

        return listaCuentasADTO(cuentaList);
    }

    protected List<Cuenta> listaCuentasADTO(List<CuentaEntity> listaEntity) {
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for(CuentaEntity entity : listaEntity) {
            cuentasDTO.add(entity.toDTO());
        }
        return cuentasDTO;
    }

    public List<Empresa> listarEmpresas() {
        List<EmpresaEntity> empresaEntities = this.empresaRepository.findAll();

        return listaEmpresasADTO(empresaEntities);
    }

    protected List<Empresa> listaEmpresasADTO(List<EmpresaEntity> entities) {
        List<Empresa> empresas = new ArrayList<>();

        for(EmpresaEntity entity : entities) {
            empresas.add(entity.toDTO());
        }

        return empresas;
    }

    public List<Cliente> listarAsociados() {
        List<ClienteEntity> asociados = this.asociadoRepository.buscarPorTipoEmpresa();
        return listaAsociadosADTO(asociados);
    }

    public List<Cliente> listarAsociados(String nombreEmpresa) {
        List<ClienteEntity> asociados  = new ArrayList<>();
        if(nombreEmpresa.isEmpty()) {
            asociados = this.asociadoRepository.buscarPorTipoEmpresa();
        } else {
            asociados = this.asociadoRepository.buscarPorEmpresa(nombreEmpresa);
        }
        return listaAsociadosADTO(asociados);
    }

    public List<Cliente> listarAsociadosDeMiEmpresa(Integer id) {
        List<ClienteEntity> asociados = this.asociadoRepository.buscarSociosAutorizadosDeMiEmpresa(id);
        return listaAsociadosADTO(asociados);
    }

    public Cliente buscarAsociadoPorNif(Integer idEmpresa, String nif) {
        Cliente cliente = new Cliente();
        ClienteEntity entity = this.asociadoRepository.buscarPorNif(idEmpresa, nif);

        return entity.toDTO();
    }

    public List<Cliente> listarAsociadosDeMiEmpresa(Integer idEmpresa, String primerNombre, String primerApellido, String puesto) {
        List<ClienteEntity> asociados = null;
        if(primerApellido.isEmpty() && puesto.isEmpty()) { //Busco por primer nombre
            asociados = this.asociadoRepository.buscarPorPrimerNombre(idEmpresa, primerNombre);
        } else if(primerNombre.isEmpty() && puesto.isEmpty()) {    //Busco por primer apellido
            asociados = this.asociadoRepository.buscarPorPrimerApellido(idEmpresa, primerApellido);
        } else if(primerNombre.isEmpty() && primerApellido.isEmpty()) {    //Busco por puesto
          asociados = this.asociadoRepository.buscarPorPuesto(idEmpresa, puesto);
        } else if(puesto.isEmpty()) {   //Busco por primer nombre y primer apellido
            asociados = this.asociadoRepository.buscarPorPrimerNombreyPrimerApellido(idEmpresa, primerNombre, primerApellido);
        } else if(primerApellido.isEmpty()) {   //Busco por primer nombre y puesto
            asociados = this.asociadoRepository.buscarPorPrimerNombreyPuesto(idEmpresa, primerNombre, puesto);
        } else if(primerNombre.isEmpty()) {     //Busco por primer apellido y puesto
            asociados = this.asociadoRepository.buscarPorPrimerApellidoyPuesto(idEmpresa, primerApellido, puesto);
        } else {  //Buscar por todo
            asociados = this.asociadoRepository.buscarPorTodo(idEmpresa, primerNombre, primerApellido, puesto);
        }

        return listaAsociadosADTO(asociados);
    }

    protected List<Cliente> listaAsociadosADTO(List<ClienteEntity> listaEntity) {
        List<Cliente> listaDTO = new ArrayList<>();
        listaEntity.forEach((ClienteEntity entity) -> listaDTO.add(entity.toDTO()));
        return listaDTO;
    }


    //Ahora mismo este guardar solo es para el editar asi que aprovecho para cogerlo de la bd
    //y sobrescribo los datos editables solo. El resto (cuentas, empresa,...) se quedan como estaban en la bd
    public void guardarAsociado(Cliente editado) {
        ClienteEntity cliente = this.asociadoRepository.findById(editado.getId()).orElse(null);

        cliente.setId(editado.getId());
        cliente.setNif(editado.getNif());
        cliente.setPrimerNombre(editado.getPrimerNombre());
        cliente.setSegundoNombre(editado.getSegundoNombre());
        cliente.setPrimerApellido(editado.getPrimerApellido());
        cliente.setSegundoApellido(editado.getSegundoApellido());
        cliente.setFechaNacimiento(editado.getFechaNacimiento());
        cliente.setCalle(editado.getCalle());
        cliente.setNumero(editado.getNumero());
        cliente.setPuerta(editado.getPuerta());
        cliente.setCiudad(editado.getCiudad());
        cliente.setPais(editado.getPais());
        cliente.setRegion(editado.getRegion());
        cliente.setCp(editado.getCp());

        this.asociadoRepository.save(cliente);
    }

    public void guardarEmpresa(Empresa empresa) {
        EmpresaEntity empresaEntity = this.empresaRepository.findById(empresa.getId()).orElse(null);

        //Hago set de todos los atributos que son editables
        empresaEntity.setNombre(empresa.getNombre());

        this.empresaRepository.save(empresaEntity);
    }

    public String transferencia(MovimientoTransferencia transferencia) {
        MovimientoEntity movimiento = new MovimientoEntity();
        String error = "";

        //Primero pillo las cuentas y miro si no hay errores en los datos
        CuentaEntity cuentaOrigen = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaOrigen());
        CuentaEntity cuentaDestino = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaDestino());

        if(cuentaDestino == null) {
            error = "cuentaDestino";
        } else if(cuentaOrigen.getDinero() < transferencia.getImporte()) {
            error = "dineroInsuficiente";
        } else if(noEstaActiva(cuentaDestino)) {
            error = "cuentaDestinoNoActiva";
        } else {
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

                //Aplico el cambio de cantidad de una moneda a otra y redondeo a 3 decimales
                CambioDivisaEntity cambio = this.cambioDivisaRepository.buscarCambioDivisa(cuentaOrigen.getDivisaByDivisaId().getMoneda(), cuentaDestino.getDivisaByDivisaId().getMoneda());
                Double dineroMonedaDestino = transferencia.getImporte() * cambio.getCambio();
                DecimalFormat formato = new DecimalFormat("#.###");
                String redondeo = formato.format(dineroMonedaDestino);
                Double dineroMonedaDestinoRedondeado = Double.parseDouble(redondeo);
                movimiento.setImporteDestino(dineroMonedaDestinoRedondeado);

                cuentaOrigen.setDinero(cuentaOrigen.getDinero() - transferencia.getImporte());
                cuentaDestino.setDinero(cuentaDestino.getDinero() + dineroMonedaDestinoRedondeado);

                //En este caso hago tambien un movimiento por el cambio de divisa
                MovimientoEntity movimientoCambioDivisa = new MovimientoEntity();
                TipoMovimientoEntity tipoMov2 = this.tipoMovimientoRepository.buscarTipoCambioDivisa();
                movimientoCambioDivisa.setTipoMovimientoByTipoMovimientoId(tipoMov2);
                movimientoCambioDivisa.setImporteOrigen(transferencia.getImporte());
                movimientoCambioDivisa.setImporteDestino(dineroMonedaDestino);
                movimientoCambioDivisa.setTimeStamp(timestamp);
                movimientoCambioDivisa.setCuentaByCuentaDestinoId(cuentaDestino);
                movimientoCambioDivisa.setCuentaByCuentaOrigenId(cuentaOrigen);
                movimientoCambioDivisa.setDivisaByMonedaOrigenId(cuentaOrigen.getDivisaByDivisaId());
                movimientoCambioDivisa.setDivisaByMonedaDestinoId(cuentaDestino.getDivisaByDivisaId());

                this.movimientoRepository.save(movimientoCambioDivisa);
            }

            this.cuentaRepository.save(cuentaOrigen);
            this.cuentaRepository.save(cuentaDestino);
            this.movimientoRepository.save(movimiento);
        }
        return error;
    }

    //TERMINARLO CUANDO ESTE LA BD CAMBIADA (Con cuentaEmpresa y cuentaIndividual)
    protected boolean noEstaActiva(CuentaEntity cuenta) {
        return false;
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
        MovimientoEntity movimiento = new MovimientoEntity();

        TipoMovimientoEntity tipoMov = this.tipoMovimientoRepository.buscarTipoCambioDivisa();
        CuentaEntity cuenta = this.cuentaRepository.buscarCuentaPorNumeroCuenta(divisa.getCuenta());
        DivisaEntity divisaNueva = this.divisaRepository.buscarDivisaPorNombre(divisa.getDivisa());
        CambioDivisaEntity cambio = this.cambioDivisaRepository.buscarCambioDivisa(cuenta.getDivisaByDivisaId().getMoneda(), divisa.getDivisa());

        Date fecha = new Date();
        Timestamp timestamp = new Timestamp(fecha.getTime());
        movimiento.setTimeStamp(timestamp);
        movimiento.setCuentaByCuentaOrigenId(cuenta);
        movimiento.setCuentaByCuentaDestinoId(cuenta);
        movimiento.setTipoMovimientoByTipoMovimientoId(tipoMov);
        Double dineroMonedaNueva = cuenta.getDinero() * cambio.getCambio();
        DecimalFormat formato = new DecimalFormat("#.###");
        String redondeo = formato.format(dineroMonedaNueva).replace(",",".");
        Double dineroMonedaDestinoRedondeado = Double.parseDouble(redondeo);
        movimiento.setImporteOrigen(cuenta.getDinero());
        movimiento.setImporteDestino(dineroMonedaDestinoRedondeado);
        movimiento.setDivisaByMonedaOrigenId(cuenta.getDivisaByDivisaId());
        movimiento.setDivisaByMonedaDestinoId(divisaNueva);
        cuenta.setDivisaByDivisaId(divisaNueva);
        cuenta.setDinero(dineroMonedaDestinoRedondeado);

        this.movimientoRepository.save(movimiento);
        this.cuentaRepository.save(cuenta);
    }

    public void bloquear(Integer idBloq) {
        ClienteEntity cliente = this.asociadoRepository.findById(idBloq).orElse(null);
        cliente.setAcceso(0);

        this.asociadoRepository.save(cliente);
    }

    public void desbloquear(Integer idBloq) {
        ClienteEntity cliente = this.asociadoRepository.findById(idBloq).orElse(null);
        cliente.setAcceso(1);

        this.asociadoRepository.save(cliente);
    }

    public Cuenta buscarCuentaPorId(Integer idCuenta) {
        CuentaEntity entity = this.cuentaRepository.findById(idCuenta).orElse(null);

        return entity.toDTO();
    }

    public List<Movimiento> listarMovimientosCuenta(Integer idCuenta) {
        List<MovimientoEntity> movimientos = this.movimientoRepository.buscarMovimientosPorIdCuenta(idCuenta);

        return movimientosADTO(movimientos);
    }

    private List<Movimiento> movimientosADTO(List<MovimientoEntity> movimientosEntities) {
        List<Movimiento> movimientos = new ArrayList<>();

        for(MovimientoEntity entity : movimientosEntities) {
            movimientos.add(entity.toDTO());
        }

        return movimientos;
    }

    public void solicitarDesbloqueo(Integer idAsociado) {
        ClienteEntity cliente = this.asociadoRepository.findById(idAsociado).orElse(null);
        SolicitudEntity solicitud = new SolicitudEntity();

        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoDesbloqueo = this.tipoSolicitudRepository.buscarTipoDesbloqueo();
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoDesbloqueo);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);

    }

    public void solicitarActivacion(Integer idAsociado) {
        ClienteEntity cliente = this.asociadoRepository.findById(idAsociado).orElse(null);
        SolicitudEntity solicitud = new SolicitudEntity();

        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoActivacion = this.tipoSolicitudRepository.buscarTipoActivacion();
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
        List<SolicitudEntity> solicitudesCliente = this.solicitudRepository.buscarSolicitudesPendientesPorClienteTipoDesbloqueo(id);

        return solicitudesCliente.size() > 0;
    }

    public boolean comprobarSolicitudActivacionEnviada(Integer id) {
        List<SolicitudEntity> solicitudesCliente = this.solicitudRepository.buscarSolicitudesPendientesPorClienteTipoActivacion(id);

        return solicitudesCliente.size() > 0;
    }

    //TERMINAR CUANDO ESTE TERMINADA LA BD
    public void registrarAsociado(Cliente asociado) {
        ClienteEntity entity = new ClienteEntity();
        EmpresaEntity empresaEntity = this.empresaRepository.findById(asociado.getEmpresa()).orElse(null);
        RolClienteEntity rol = this.rolClienteRepository.buscarRol(asociado.getTipo());

        entity.setNif(asociado.getNif());
        entity.setPrimerNombre(asociado.getPrimerNombre());
        entity.setSegundoNombre(asociado.getSegundoNombre());
        entity.setPrimerApellido(asociado.getPrimerApellido());
        entity.setSegundoApellido(asociado.getSegundoApellido());
        entity.setFechaNacimiento(asociado.getFechaNacimiento());
        entity.setCalle(asociado.getCalle());
        entity.setNumero(asociado.getNumero());
        entity.setPuerta(asociado.getPuerta());
        entity.setCiudad(asociado.getCiudad());
        entity.setPais(asociado.getPais());
        entity.setCp(asociado.getCp());
        entity.setContrasena(asociado.getContrasena());
        entity.setAcceso(asociado.getAcceso());
        entity.setEmpresaByEmpresaId(empresaEntity);
        entity.setRolClienteByRolclienteId(rol);

        //Ahora tendria que asignarle la cuenta de empresa y una individual
    }
}
