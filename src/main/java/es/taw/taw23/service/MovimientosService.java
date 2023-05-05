package es.taw.taw23.service;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.MovimientoRepository;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.entity.MovimientoEntity;
import es.taw.taw23.ui.FiltroMovimientosIndiv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosService {
    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected MovimientoRepository movimientoRepository;

    public List<Movimiento> buscarMovimientosConClienteId(Integer idCuenta) {

        List<MovimientoEntity> movimientosEntityList = this.movimientoRepository.buscarMovimientosPorIdCuenta(idCuenta);
        List<Movimiento> movimientosDTO = new ArrayList<>();

        for(MovimientoEntity entity : movimientosEntityList){
            movimientosDTO.add(entity.toDTO());
        }
        return movimientosDTO;
    }

    public List<Movimiento> buscarMovimientosConClienteIdyFiltro (Integer id,Integer idCuenta, FiltroMovimientosIndiv filtro, List<String> opciones, String orden){

        List<MovimientoEntity> movimientosEntityList = new ArrayList<>();
        List<Movimiento> movimientosDTO = new ArrayList<>();

        //Filtro vacio
        if(filtro.getTipo().isEmpty() && filtro.getCuentaDestino().isEmpty() && filtro.getDivisaCuentaDestino().isEmpty() && filtro.getDivisaCuentaOrigen().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosPorIdCuenta(idCuenta);

        } else if(filtro.getCuentaDestino().isEmpty() && filtro.getDivisaCuentaDestino().isEmpty() && filtro.getDivisaCuentaOrigen().isEmpty()){
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipo(idCuenta, filtro.getTipo());    //Buscar por tipo

        } else if(filtro.getTipo().isEmpty() && filtro.getDivisaCuentaDestino().isEmpty() && filtro.getDivisaCuentaOrigen().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorCuentaDestino(idCuenta, filtro.getCuentaDestino());  //Buscar por Cuenta Destino

        } else if(filtro.getTipo().isEmpty() && filtro.getCuentaDestino().isEmpty() && filtro.getDivisaCuentaOrigen().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorDivisaDestino(idCuenta, filtro.getDivisaCuentaDestino());  //Buscar por Divisa Destino

        } else if(filtro.getTipo().isEmpty() && filtro.getCuentaDestino().isEmpty() && filtro.getDivisaCuentaDestino().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorDivisaOrigen(idCuenta, filtro.getDivisaCuentaOrigen());    //Buscar por Divisa Origen

        } else if(filtro.getDivisaCuentaDestino().isEmpty() && filtro.getDivisaCuentaOrigen().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipoYCuentaDestino(idCuenta, filtro.getTipo(), filtro.getCuentaDestino());  //Busco por Tipo y Cuenta Destino

        } else if(filtro.getCuentaDestino().isEmpty() && filtro.getDivisaCuentaOrigen().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipoYDivisaDestino(idCuenta, filtro.getTipo(), filtro.getDivisaCuentaDestino());   //Busco por Tipo y Divisa Destino

        } else if(filtro.getCuentaDestino().isEmpty() && filtro.getDivisaCuentaDestino().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipoYDivisaOrigen(idCuenta, filtro.getTipo(), filtro.getDivisaCuentaOrigen());   //Busco por Tipo y Divisa Origen

        } else if(filtro.getTipo().isEmpty() && filtro.getDivisaCuentaOrigen().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorCuentaDestinoYDivisaDestino(idCuenta, filtro.getCuentaDestino(), filtro.getDivisaCuentaDestino()); //Busco por Cuenta Destino y Divisa Destino

        } else if(filtro.getTipo().isEmpty() && filtro.getDivisaCuentaDestino().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorCuentaDestinoYDivisaOrigen(idCuenta, filtro.getCuentaDestino(), filtro.getDivisaCuentaOrigen());  //Busco por Cuenta Destino y Divisa Origen

        } else if(filtro.getTipo().isEmpty() && filtro.getCuentaDestino().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorDivisaDestinoYDivisaOrigen(idCuenta, filtro.getDivisaCuentaDestino(), filtro.getDivisaCuentaOrigen());   //Busco por Divisa Destino y Divisa Origen

        } else if(filtro.getDivisaCuentaOrigen().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipoCuentaDestinoDivisaDestino(idCuenta, filtro.getTipo(), filtro.getCuentaDestino(), filtro.getDivisaCuentaDestino());    //Busco port Tipo, Cuenta Destino y Divisa Destino

        } else if(filtro.getDivisaCuentaDestino().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipoCuentaDestinoDivisaOrigen(idCuenta, filtro.getTipo(), filtro.getCuentaDestino(), filtro.getDivisaCuentaOrigen());      //Buscar por Tipo, Cuenta Destino y Divisa Origen

        } else if(filtro.getCuentaDestino().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipoDivisaDestinoDivisaOrigen(idCuenta, filtro.getTipo(), filtro.getDivisaCuentaDestino(), filtro.getDivisaCuentaOrigen());      //Buscar por Tipo, Divisa Destino y Divisa Origen

        } else if(filtro.getTipo().isEmpty()) {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorCuentaDestinoDivisaDestinoDivisaOrigen(idCuenta, filtro.getCuentaDestino(), filtro.getDivisaCuentaDestino(), filtro.getDivisaCuentaOrigen());      //Buscar por Cuenta Destino, Divisa Destino y Divisa Origen

        } else {
            movimientosEntityList = this.movimientoRepository.buscarMovimientosClientePorTipoCuentaDestinoDivisaDestinoDivisaOrigen(idCuenta, filtro.getTipo(), filtro.getCuentaDestino(), filtro.getDivisaCuentaDestino(), filtro.getDivisaCuentaOrigen()); //Buscar por Tipo, Cuenta Destino, Divisa Destino y Divisa Origen
        }



        //Ahora que tengo la lista la ordeno según el criterio dado por el filtro
        if(opciones != null && !opciones.isEmpty()) {
            if(opciones.size() == 2) {
                if(orden.equals("ascendente")) {
                    movimientosEntityList = this.movimientoRepository.ordenarPorImporteOrigenYDestinoAscendente(movimientosEntityList);
                } else {
                    movimientosEntityList = this.movimientoRepository.ordenarPorImporteOrigenYDestinoDescendente(movimientosEntityList);
                }
            } else {
                if(opciones.get(0).equals("origen")) {     //Ordeno por Importe Origen
                    if(orden.equals("ascendente")) {
                        movimientosEntityList = this.movimientoRepository.ordenarPorImporteOrigenAscendente(movimientosEntityList);
                    } else {
                        movimientosEntityList = this.movimientoRepository.ordenarPorImporteOrigenDescendente(movimientosEntityList);
                    }
                } else {            //Ordeno por Importe Destino
                    if(orden.equals("ascendente")) {
                        movimientosEntityList = this.movimientoRepository.ordenarPorImporteDestinoAscendente(movimientosEntityList);
                    } else {
                        movimientosEntityList = this.movimientoRepository.ordenarPorImporteDestinoDescendente(movimientosEntityList);
                    }
                }
            }
        }

        for(MovimientoEntity entity : movimientosEntityList){
            movimientosDTO.add(entity.toDTO());
        }
        return movimientosDTO;
    }
}
