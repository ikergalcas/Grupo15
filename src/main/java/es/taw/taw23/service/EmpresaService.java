package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.entity.*;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmpresaService {
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

    public EmpresaEntity buscarEmpresa(Integer id) {
        EmpresaEntity empresaEntity = this.empresaRepository.findById(id).orElse(null);
        if(empresaEntity != null) {
            return empresaEntity;
        } else {
            return null;
        }
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

    public List<Cliente> listarAsociadosDeMiEmpresa(Integer idEmpresa, String nif, String primerNombre, String primerApellido) {
        List<ClienteEntity> asociados = null;
        if(nif.isEmpty() && primerApellido.isEmpty()) { //Busco por primer nombre
            asociados = this.asociadoRepository.buscarPorPrimerNombre(idEmpresa, primerNombre);
        } else if(primerApellido.isEmpty() && primerNombre.isEmpty()) { //Busco por nif
            asociados = this.asociadoRepository.buscarPorNif(idEmpresa, nif);
        } else if(nif.isEmpty() && primerNombre.isEmpty()) {    //Busco por primer apellido
            asociados = this.asociadoRepository.buscarPorPrimerApellido(idEmpresa, primerApellido);
        } else if(primerApellido.isEmpty()) {   //Busco por primer nombre y nif
            asociados = this.asociadoRepository.buscarPorNifyPrimerNombre(idEmpresa, nif, primerNombre);
        } else if(primerNombre.isEmpty()) {   //Busco por primer apellido y nif
            asociados = this.asociadoRepository.buscarPorNifyPrimerApellido(idEmpresa, nif, primerApellido);
        } else {  //Buscar por primer nombre y primer apellido
            asociados = this.asociadoRepository.buscarPorPrimerNombreyPrimerApellido(idEmpresa, primerNombre, primerApellido);
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

    /**
     * En este caso no he hecho un dto para empresa porque tiene muy pocos atributos
     * Tan solo tengo que pasar la lista de asociados de la empresa (de la tabla Cliente)
     * del objeto empresaBD a empresaForm.
     */
    public void guardarEmpresa(EmpresaEntity empresaForm, EmpresaEntity empresaBD) {
        empresaForm.setClientesById(empresaBD.getClientesById());
        this.empresaRepository.save(empresaForm);
    }

    public String transferencia(MovimientoTransferencia transferencia) {
        MovimientosEntity movimiento = new MovimientosEntity();
        String error = "";

        //Primero pillo las cuentas y miro si no hay errores en los datos
        CuentaEntity cuentaOrigen = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaOrigen());
        CuentaEntity cuentaDestino = this.cuentaRepository.buscarCuentaPorNumeroCuenta(transferencia.getCuentaDestino());

        if(cuentaDestino == null) {
            error = "cuentaDestino";
        } else if(cuentaOrigen == null) {
            error = "cuentaOrigen";
        } else if(cuentaOrigen.getDinero() < transferencia.getImporte()) {
            error = "dineroInsuficiente";
        } else {
            TipoMovimientoEntity tipoMov = this.tipoMovimientoRepository.buscarTipoTransferencia();
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMov);

            Date fecha = new Date();
            Timestamp timestamp = new Timestamp(fecha.getTime());
            movimiento.setTimeStamp(timestamp);

            movimiento.setImporteDestino(transferencia.getImporte());
            movimiento.setImporteOrigen(transferencia.getImporte());
            movimiento.setCuentaByCuentaDestinoId(cuentaDestino);
            movimiento.setCuentaByCuentaOrigenId(cuentaOrigen);

            cuentaOrigen.setDinero(cuentaOrigen.getDinero() - transferencia.getImporte());
            cuentaDestino.setDinero(cuentaDestino.getDinero() + transferencia.getImporte());

            this.cuentaRepository.save(cuentaOrigen);
            this.cuentaRepository.save(cuentaDestino);
            this.movimientoRepository.save(movimiento);
        }
        return error;
    }
}
