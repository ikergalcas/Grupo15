//Pablo Alarcón Carrión
package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.*;
import es.taw.taw23.entity.*;
import es.taw.taw23.ui.FiltroCajero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class CajeroService {
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
    protected CajeroRepository cajeroRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EstadoSolicitudRepository estadoSolicitudRepository;
    @Autowired
    private TipoSolicitudRepository tipoSolicitudRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    //Busqueda de cosas
    public Cliente buscarCliente(Integer id){
        ClienteEntity cliente = clienteRepository.findById(id).orElse(null);
        return (cliente!=null) ? cliente.toDTO() : null;
    }

    public List<Cuenta> buscarCuentasAsociadas(Integer id){
        ClienteEntity cliente = clienteRepository.findById(id).orElse(null);
        List<CuentaClienteEntity> cuentas = cliente.getCuentaClientesById();

        List<Cuenta> cuentasAsociadas = new ArrayList<>();
        for (CuentaClienteEntity x : cuentas){
            cuentasAsociadas.add(x.getCuentaByCuentaId().toDTO());
        }
        return cuentasAsociadas;
    }
    public Cuenta buscarCuenta(Integer id){
        CuentaEntity cuenta = cuentaRepository.findById(id).orElse(null);
        return (cuenta!=null) ? cuenta.toDTO() : null;
    }

    public Cuenta buscarCuentaPorNumero(String numero){
        CuentaEntity cuenta = cajeroRepository.findByAccountNumber(numero);
        return (cuenta!=null) ? cuenta.toDTO() : null;
    }

    public List<Cuenta> obtenerTodasLasCuentasMenosOrigen(Integer id){
        List<CuentaEntity> cuentas = this.cuentaRepository.findAll();
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for (CuentaEntity x : cuentas){
            if (!x.getId().equals(id)) //Esto debería hacerlo con SQL pero no se honestamente
                cuentasDTO.add(x.toDTO());
        }
        return cuentasDTO;
    }

    public List<Divisa> obtenerTodasLasDivisas(){
        List<DivisaEntity> divisas = this.divisaRepository.findAll();
        List<Divisa> divisasDTO = new ArrayList<>();
        for (DivisaEntity x : divisas){
            divisasDTO.add(x.toDTO());
        }
        return divisasDTO;
    }

    public List<Cuenta> obtenerTodasLasCuentas(){
        List<CuentaEntity> cuentas = this.cuentaRepository.findAll();
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for (CuentaEntity x : cuentas){
            cuentasDTO.add(x.toDTO());
        }
        return cuentasDTO;
    }

    public Solicitud buscarSolicitud(Integer clienteId){
        return (this.cajeroRepository.buscarSolicitudPorIdCliente(clienteId) == null) ? null :
                this.cajeroRepository.buscarSolicitudPorIdCliente(clienteId).toDTO();
    }

    //Establecer nuevas operaciones
    private Double redondear(Double cantidad){
        return Double.valueOf(Math.round((cantidad.doubleValue()*100d) / 100));
    }

    public void setNewCliente(Cliente cliente){
        this.clienteRepository.save(guardarAux(cliente));
    }

    private ClienteEntity guardarAux(Cliente cliente){
        ClienteEntity aux = this.clienteRepository.findById(cliente.getId()).orElse(null);
        aux.setNif(cliente.getNif());
        aux.setPrimerNombre(cliente.getPrimerNombre());
        aux.setSegundoNombre(cliente.getSegundoNombre());
        aux.setPrimerApellido(cliente.getPrimerApellido());
        aux.setSegundoApellido(cliente.getSegundoApellido());
        aux.setFechaNacimiento((Date) cliente.getFechaNacimiento());
        aux.setCalle(cliente.getCalle());
        aux.setNumero(cliente.getNumero());
        aux.setPuerta(cliente.getPuerta());
        aux.setCiudad(cliente.getCiudad());
        aux.setPais(cliente.getPais());
        aux.setRegion(cliente.getRegion());
        aux.setCp(cliente.getCp());
        aux.setContrasena(cliente.getContrasena());
        return aux;
    }

    public void setNewMovimiento(Cuenta origen, Cuenta destino,
                                 Double cantidadOrigen, Integer clienteId){
        ClienteEntity cliente = clienteRepository.findById(clienteId).orElse(null);
        CuentaEntity origenAux = cuentaRepository.findById(origen.getId()).orElse(null);
        CuentaEntity destinoAux = cuentaRepository.findById(destino.getId()).orElse(null);
        MovimientoEntity movimiento = new MovimientoEntity();
        List<MovimientoEntity> movimientosTotales = movimientoRepository.findAll();
        movimiento.setId(movimientosTotales.get(movimientosTotales.size()-1).getId()+1);
        movimiento.setCuentaByCuentaOrigenId(origenAux);
        movimiento.setDivisaByMonedaOrigenId(origenAux.getDivisaByDivisaId());
        if (origen.getId().equals(destino.getId()) && origen.getMoneda().equals(destino.getMoneda())){
            TipoMovimientoEntity tipoMovimiento = cajeroRepository.findByMovementName("sacarDinero");
            movimiento.setImporteOrigen(redondear(origenAux.getDinero()));
            movimiento.setImporteDestino(redondear(origenAux.getDinero()-cantidadOrigen));
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMovimiento);
            movimiento.setDivisaByMonedaDestinoId(destinoAux.getDivisaByDivisaId());
        }
        else if(origen.getId().equals(destino.getId()) && !origen.getMoneda().equals(destino.getMoneda())){
            TipoMovimientoEntity tipoMovimiento = cajeroRepository.findByMovementName("cambioDivisa");
            DivisaEntity divisaNueva = cajeroRepository.findByMoneyName(destino.getMoneda());
            CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(
                    cajeroRepository.findByMoneyName(origen.getMoneda()).getId(),
                    cajeroRepository.findByMoneyName(destino.getMoneda()).getId());
            movimiento.setImporteOrigen(redondear(origenAux.getDinero()));
            movimiento.setImporteDestino(redondear(cambioDivisa.getCambio()*origenAux.getDinero()));
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMovimiento);
            movimiento.setDivisaByMonedaDestinoId(divisaNueva);
        }
        else{
            TipoMovimientoEntity tipoMovimiento = cajeroRepository.findByMovementName("pago");
            movimiento.setImporteOrigen(redondear(cantidadOrigen));
            if (origen.getMoneda().equals(destino.getMoneda())){
                movimiento.setImporteDestino(redondear(cantidadOrigen));
            }else{
                CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(
                        cajeroRepository.findByMoneyName(origen.getMoneda()).getId(),
                        cajeroRepository.findByMoneyName(destino.getMoneda()).getId());
                movimiento.setImporteDestino(redondear(cambioDivisa.getCambio()*cantidadOrigen));

            }
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMovimiento);
            movimiento.setDivisaByMonedaDestinoId(destinoAux.getDivisaByDivisaId());

        }
        movimiento.setCuentaByCuentaDestinoId(destinoAux);
        movimiento.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        movimiento.setClienteByClienteId(cliente);
        movimientoRepository.save(movimiento);
    }

    public void setNewSaldo(Cuenta cuenta, Double cantidad){
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());
        aux.setDinero(redondear(cuenta.getDinero() - cantidad));
        this.cuentaRepository.save(aux);
    }

    public void setNewSaldoDivisaDistinta(Cuenta origen, Cuenta destino, Double cantidad){
        CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(
                cajeroRepository.findByMoneyName(origen.getMoneda()).getId(),
                cajeroRepository.findByMoneyName(destino.getMoneda()).getId());
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(destino.getNumeroCuenta());
        aux.setDinero(redondear(destino.getDinero()+(cantidad*cambioDivisa.getCambio())));
        this.cuentaRepository.save(aux);
    }

    public void setNewDivisa(String divisaNombre, Cuenta cuenta){
        DivisaEntity divisa = this.cajeroRepository.findByMoneyName(divisaNombre);
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());

        Integer divisaOrigenId = this.cajeroRepository.findByMoneyName(aux.getDivisaByDivisaId().getMoneda()).getId();
        Integer divisaDestinoId = divisa.getId();

        CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(divisaOrigenId, divisaDestinoId);

        aux.setDivisaByDivisaId(divisa);
        aux.setDinero(redondear(cambioDivisa.getCambio()*aux.getDinero()));
        this.cuentaRepository.save(aux);
    }

    public void setNewDesbloqueo(Integer idCliente) {
        ClienteEntity cliente = this.clienteRepository.findById(idCliente).orElse(null);
        SolicitudEntity solicitud = new SolicitudEntity();

        EstadoSolicitudEntity pendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoDesbloqueo = this.tipoSolicitudRepository.buscarTipoDesbloqueoIndividual();

        solicitud.setEstadoSolicitudByEstadoSolicitudId(pendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoDesbloqueo);
        solicitud.setEmpleadoByEmpleadoId(buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);
    }

    protected EmpleadoEntity buscarGestorMenosOcupado() {
        List<EmpleadoEntity> gestores = this.cajeroRepository.buscarGestores();

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



    //A partir de aqui son filtros

    public List<Movimiento> filtrarToDTO(Integer idCuenta, FiltroCajero filtroCajero){
        List<MovimientoEntity> movimientos = filtrarGeneral(idCuenta,filtroCajero);
        List<Movimiento> movimientosDTO = new ArrayList<>();
        for (MovimientoEntity x : movimientos){
            movimientosDTO.add(x.toDTO());
        }
        return movimientosDTO;
    }
    public List<MovimientoEntity> filtrarGeneral(Integer idCuenta, FiltroCajero filtroCajero) {
        List<MovimientoEntity> movimientos = new ArrayList<>();

        String filtrarDivisa = filtroCajero.getFiltrarPorDivisa();
        String filtrarNumero = filtroCajero.getFiltrarPorNumeroDeCuenta();
        String filtrarMovimiento = filtroCajero.getFiltrarPorMovimiento();

        boolean ordenarFecha = filtroCajero.isOrdenarFecha();
        boolean ordenarTipo = filtroCajero.isOrdenarTipo();
        boolean ordenarImporte = filtroCajero.isOrdenarImporte();


        //El array de binario es XYZ, X para divisa, Y para numero, Z para movimiento
        if (!filtrarDivisa.equals("")) { //DIVISA 1
            if (!filtrarNumero.equals("")) { //CUENTA 1
                if (!filtrarMovimiento.equals("")) { // MOVIMIENTO 1
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroMovimiento(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroMovimientoOrdenarImporte(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroMovimientoOrdenarTipo(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.filtrarDivisaNumeroMovimientoOrdenarTipoImporte(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroMovimientoOrdenarFecha(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroMovimientoOrdenarFechaImporte(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroMovimientoOrdenarFechaTipo(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroMovimientoOrdenarAll(idCuenta,filtrarDivisa,filtrarNumero,filtrarMovimiento);
                    }

                } else if (filtrarMovimiento.equals("")) { //MOVIMIENTO 0
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumero(idCuenta,filtrarDivisa,filtrarNumero);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroOrdenarPorImporteOrigenAsc(idCuenta,filtrarDivisa,filtrarNumero);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroOrdenarPorTipoMovimientoAsc(idCuenta,filtrarDivisa,filtrarNumero);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.filtrarDivisaNumeroOrdenarTipoImporteOrigen(idCuenta,filtrarDivisa,filtrarNumero);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroOrdenarPorTimeStampAsc(idCuenta,filtrarDivisa,filtrarNumero);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroOrdenarTimeStampImporteOrigen(idCuenta,filtrarDivisa,filtrarNumero);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroOrdenarTimeStampTipo(idCuenta,filtrarDivisa,filtrarNumero);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaNumeroOrdenarAll(idCuenta,filtrarDivisa,filtrarNumero);
                    }
                }
            } else if (filtrarNumero.equals("")) { //CUENTA 0
                if (!filtrarMovimiento.equals("")) { //MOVIMIENTO 1
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaMovimiento(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaMovimientoOrdenarImporte(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaMovimientoOrdenarPorTipoMovimiento(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.filtrarDivisaMovimientoOrdenarTipoImporte(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaMovimientoOrdenarPorTimeStamp(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaMovimientoOrdenarTimeStampImporteOrigen(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaMovimientoOrdenarTimeStampTipoMovimiento(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaMovimientoOrdenarAll(idCuenta,filtrarDivisa,filtrarMovimiento);
                    }
                } else if (filtrarMovimiento.equals("")) { //MOVIMIENTO 0
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisa(idCuenta,filtrarDivisa);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaOrdenarPorImporteOrigen(idCuenta,filtrarDivisa);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaOrdenarPorTipoMovimiento(idCuenta,filtrarDivisa);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.filtrarDivisaOrdenarPorTipoMovimientoImporteOrigen(idCuenta,filtrarDivisa);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaOrdenarPorTimeStamp(idCuenta,filtrarDivisa);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaOrdenarPorTimeStampImporteOrigen(idCuenta,filtrarDivisa);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaOrdenarPorTimeStampTipoMovimiento(idCuenta,filtrarDivisa);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarDivisaOrdenarAll(idCuenta,filtrarDivisa);
                    }
                }
            }
        } else if (filtrarDivisa.equals("")) { //DIVISA 0
            if (!filtrarNumero.equals("")) { //CUENTA 1
                if (!filtrarMovimiento.equals("")) { // MOVIMIENTO 1
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroMovimiento(idCuenta,filtrarNumero,filtrarMovimiento);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroMovimientoOrdenarPorImporteOrigen(idCuenta,filtrarNumero,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroMovimientoOrdenarPorTipoMovimiento(idCuenta,filtrarNumero,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.filtrarNumeroMovimientoOrdenarPorTipoImporte(idCuenta,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroMovimientoOrdenarPorTimeStamp(idCuenta,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroMovimientoOrdenarPorFechaImporte(idCuenta,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroMovimientoOrdenarPorFechaTipo(idCuenta,filtrarNumero,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroMovimientoOrdenarAll(idCuenta,filtrarNumero,filtrarMovimiento);
                    }
                } else if (filtrarMovimiento.equals("")) { //MOVIMIENTO 0
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumero(idCuenta,filtrarNumero);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroOrdenarPorImporteOrigen(idCuenta,filtrarNumero);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroOrdenarPorTipoMovimiento(idCuenta,filtrarNumero);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.filtrarNumeroOrdenarPorTipoMovimientoEImporteOrigen(idCuenta,filtrarNumero);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroOrdenarPorTimeStamp(idCuenta,filtrarNumero);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroOrdenarPorFechaEImporteOrigen(idCuenta,filtrarNumero);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroOrdenarPorTimeStampYTipoMovimiento(idCuenta,filtrarNumero);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarNumeroOrdenarAll(idCuenta,filtrarNumero);
                    }
                }
            } else if (filtrarNumero.equals("")) { //CUENTA 0
                if (!filtrarMovimiento.equals("")) { //MOVIMIENTO 1
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarMovimiento(idCuenta,filtrarMovimiento);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarMovimientoOrdenarImporteOrigen(idCuenta,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarMovimientoOrdenarTipoMovimiento(idCuenta,filtrarMovimiento);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.filtrarMovimientoOrdenarTipoImporteOrigen(idCuenta,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarMovimientoOrdenarTimeStamp(idCuenta,filtrarMovimiento);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarMovimientoOrdenarFechaImporteOrigen(idCuenta,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.filtrarMovimientoOrdenarFechaTipo(idCuenta,filtrarMovimiento);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.filtrarMovimientoOrdenarAll(idCuenta,filtrarMovimiento);
                    }
                } else if (filtrarMovimiento.equals("")) { //MOVIMIENTO 0
                    if (!ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.findAllMovimientos(idCuenta);
                    }else if (!ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.OrdenarImporte(idCuenta);
                    }else if (!ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.OrdenarTipo(idCuenta);
                    }else if (!ordenarFecha && ordenarTipo && ordenarImporte) {
                        return this.cajeroRepository.OrdenarTipoImporte(idCuenta);
                    }else if (ordenarFecha && !ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.OrdenarFecha(idCuenta);
                    }else if (ordenarFecha && !ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.OrdenarFechaImporte(idCuenta);
                    }else if (ordenarFecha && ordenarTipo && !ordenarImporte){
                        return this.cajeroRepository.OrdenarFechaTipo(idCuenta);
                    }else if (ordenarFecha && ordenarTipo && ordenarImporte){
                        return this.cajeroRepository.OrdenarAll(idCuenta);
                    }
                }
            }
        }
        return movimientos;
    }
}

