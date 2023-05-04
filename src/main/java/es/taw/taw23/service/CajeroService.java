//Pablo Alarcón Carrión
package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.*;
import es.taw.taw23.entity.*;
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

    public Solicitud buscarSolicitud(Integer idCliente){
        return (this.cajeroRepository.buscarSolicitudPorIdCliente(idCliente) == null) ?
                null : this.cajeroRepository.buscarSolicitudPorIdCliente(idCliente).toDTO();
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
        solicitud.setClienteByClienteId(cliente);

        this.solicitudRepository.save(solicitud);
    }



    //A partir de aqui son filtros
    public List<Movimiento> filtrarPorDivisa(Cuenta cuenta, String divisa){
        DivisaEntity moneda = this.cajeroRepository.findByMoneyName(divisa);
        CuentaEntity cuentaAux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());
        List<MovimientoEntity> movs = this.cajeroRepository.findAllMovimientos(cuentaAux.getId());
        List<MovimientoEntity> filtrado = new ArrayList<>();
        boolean alreadyAdd;
        for (MovimientoEntity mov : movs){
            alreadyAdd=false;
            if (mov.getDivisaByMonedaOrigenId().equals(moneda)){
                filtrado.add(mov);
                alreadyAdd=true;
            }
            if (mov.getDivisaByMonedaDestinoId().equals(moneda) && !alreadyAdd){
                filtrado.add(mov);
            }
        }
        List<Movimiento> movimientos = new ArrayList<>();
        for (MovimientoEntity x : filtrado){
            movimientos.add(x.toDTO());
        }
        return movimientos;
    }

    public List<Movimiento> filtrarPorNumeroDeCuenta(Cuenta cuenta, String numeroCuenta, List<Movimiento> movimientos){
        if (movimientos==null){
            movimientos=new ArrayList<>();
            CuentaEntity cuentaAux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());
            List<MovimientoEntity> movimientosTotales = this.cajeroRepository.findAllMovimientos(cuentaAux.getId());
            boolean alreadyAdd;
            for (MovimientoEntity x : movimientosTotales){
                alreadyAdd=false;
                if (x.getCuentaByCuentaOrigenId().getNumeroCuenta().equals(numeroCuenta)){
                    movimientos.add(x.toDTO());
                    alreadyAdd=true;
                }
                else if (x.getCuentaByCuentaDestinoId().getNumeroCuenta().equals(numeroCuenta) && !alreadyAdd)
                    movimientos.add(x.toDTO());
            }
        }
        else{
            List<Movimiento> filtrar = new ArrayList<>();
            boolean alreadyAdd;
            for (Movimiento x : movimientos){
                alreadyAdd=false;
                if (x.getCuentaOrigen().equals(numeroCuenta)){
                    filtrar.add(x);
                }else if (x.getCuentaDestino().equals(numeroCuenta) && !alreadyAdd)
                    filtrar.add(x);
            }
            movimientos = filtrar;
        }
        return movimientos;
    }

    public List<Movimiento> filtrarPorTipoMovimiento(Cuenta cuenta, String tipoMovimiento, List<Movimiento> movimientos){
        if (movimientos==null){
            movimientos = new ArrayList<>();
            CuentaEntity cuentaAux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());
            List<MovimientoEntity> movimientosTotales = cuentaAux.getMovimientosById();
            for (MovimientoEntity x : movimientosTotales){
                if (x.getTipoMovimientoByTipoMovimientoId().getTipo().equals(tipoMovimiento))
                    movimientos.add(x.toDTO());
            }
        }else{
            List<Movimiento> filtrar = new ArrayList<>();
            for (Movimiento x : movimientos){
                if (x.getTipo().equals(tipoMovimiento))
                    filtrar.add(x);
            }
            movimientos = filtrar;
        }
        return movimientos;
    }



    public List<Movimiento> ordenarPorCriterio(Cuenta cuenta, String orden, List<Movimiento> movimientos){
        if (movimientos==null){
            movimientos = new ArrayList<>();
            List <MovimientoEntity> movimientosTotales = new ArrayList<>();
            if (orden.equals("Fecha")){
                movimientosTotales = this.cajeroRepository.findByFechaMovimientoAsc(cuenta.getId());
            } else if (orden.equals("Importe")){
                movimientosTotales = this.cajeroRepository.findAllMovimientos(cuenta.getId());
                movimientosTotales.sort(new SortByAmount());
            } else if (orden.equals("Tipo de movimiento")){
                movimientosTotales = this.cajeroRepository.findByTipoDeMovimientoAsc(cuenta.getId()); //No ordena bien por nombre, pero los agrupa
            }
            for (MovimientoEntity x : movimientosTotales){
                movimientos.add(x.toDTO());
            }
        }else{
            List<MovimientoEntity> movimientosTotales = new ArrayList<>();
            for (Movimiento x : movimientos){
                movimientosTotales.add(this.movimientoRepository.findById(x.getId()).orElse(null));
            }
            if (orden.equals("Fecha")){
                movimientosTotales.sort(new SortByDate());
            } else if (orden.equals("Importe")){
                movimientosTotales.sort(new SortByAmount());
            } else if (orden.equals("Tipo de movimiento")){
                movimientosTotales.sort(new SortByType());
            }
        }
        return movimientos;
    }

    private class SortByAmount implements Comparator<MovimientoEntity> {
        public int compare(MovimientoEntity a, MovimientoEntity b){
            return (cambioDivisaAEuro(a).compareTo(cambioDivisaAEuro(b)));
        }

        private Double cambioDivisaAEuro(MovimientoEntity x){
            Double importeOrigen = x.getImporteOrigen();
            Double importeDestino = x.getImporteDestino();

            CambioDivisaEntity cambioDivisaOrigen = cajeroRepository.cambiarDivisa(cajeroRepository.findByMoneyName(x.getDivisaByMonedaOrigenId().getMoneda()).getId(),
                    cajeroRepository.findByMoneyName("euro").getId());
            CambioDivisaEntity cambioDivisaDestino = cajeroRepository.cambiarDivisa(cajeroRepository.findByMoneyName(x.getDivisaByMonedaDestinoId().getMoneda()).getId(),
                    cajeroRepository.findByMoneyName("euro").getId());
            if (cambioDivisaOrigen!=null)
                importeOrigen *= cambioDivisaOrigen.getCambio();
            if (cambioDivisaDestino!=null)
                importeDestino *= cambioDivisaDestino.getCambio();
            return Math.max(importeOrigen,importeDestino);
        }
    }

    private class SortByDate implements Comparator<MovimientoEntity> {
        public int compare(MovimientoEntity a, MovimientoEntity b){
            return a.getTimeStamp().compareTo(b.getTimeStamp());
        }
    }

    private class SortByType implements Comparator<MovimientoEntity> {
        public int compare(MovimientoEntity a, MovimientoEntity b){
            return a.getTipoMovimientoByTipoMovimientoId().getTipo().compareTo(b.getTipoMovimientoByTipoMovimientoId().getTipo());
        }
    }

}

