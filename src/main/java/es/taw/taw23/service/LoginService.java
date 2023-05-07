package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Empresa;
import es.taw.taw23.dto.Solicitud;
import es.taw.taw23.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Hecho por:
 * Álvaro Yuste Moreno: 20%
 * Iker Gálvez Castillo: 20%
 * Carla Serra Cant: 20%
 * Pablo Alarcón Carrión: 20%
 * Rocío Gómez: 20%
 */
@Service
public class LoginService {

    @Autowired
    protected SolicitudRepository solicitudRepository;

    @Autowired
    protected RolClienteRepository rolClienteRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected AsociadoRepository asociadoRepository;

    @Autowired
    protected EmpresaRepository empresaRepository;
    @Autowired
    protected EmpleadoRepository empleadoRepository;

    @Autowired
    protected EstadoSolicitudRepository estadoSolicitudRepository;
    @Autowired
    protected TipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    protected EmpresaService empresaService;

    public Cliente buscarCliente(String nif, String contrasena) {
        ClienteEntity entity = this.clienteRepository.inicioSesion(nif, contrasena);
        Cliente cliente = null;
        if(entity != null) {    //Busco que el nif y la contraseÃ±a sean conrrectos
            SolicitudEntity solicitud = this.solicitudRepository.buscarSolicitudDeAltaEmpresaPendienteoDenegada(entity.getId());
            if(solicitud == null) { //Esto es solo para el caso en el que se ha registrado una empresa y el socio esta en la bd pero no se ha aceptado la solicitud de alta
                cliente = entity.toDTO();
            }
        }
        return cliente;
    }

    public void registrarEmpresa(Empresa empresa, Cliente socio) {
        EmpresaEntity empresaEntity = new EmpresaEntity();
        ClienteEntity clienteEntity = new ClienteEntity();

        //Relleno los campos de la empresa
        empresaEntity.setNombre(empresa.getNombre());
        empresaEntity.setCif(empresa.getCif());
        empresaEntity.setContrasena(empresa.getContrasenaEmpresa());

        this.empresaRepository.save(empresaEntity);
        EmpresaEntity empresaBD = this.empresaRepository.buscarEmpresaPorNombreRegistro(empresa.getNombre());

        clienteEntity.setNif(socio.getNif());
        clienteEntity.setPrimerNombre(socio.getPrimerNombre());
        clienteEntity.setSegundoNombre(socio.getSegundoNombre());
        clienteEntity.setSegundoApellido(socio.getSegundoApellido());
        clienteEntity.setFechaNacimiento(socio.getFechaNacimiento());
        clienteEntity.setCalle(socio.getCalle());
        clienteEntity.setNumero(socio.getNumero());
        clienteEntity.setPuerta(socio.getPuerta());
        clienteEntity.setCiudad(socio.getCiudad());
        clienteEntity.setPais(socio.getPais());
        clienteEntity.setRegion(socio.getRegion());
        clienteEntity.setCp(socio.getCp());
        clienteEntity.setContrasena(socio.getContrasena());
        clienteEntity.setAcceso(1);
        clienteEntity.setEmpresaByEmpresaId(empresaBD);
        clienteEntity.setRolClienteByRolclienteId(this.rolClienteRepository.buscarRol("socio"));

        this.clienteRepository.save(clienteEntity);
        ClienteEntity clienteBD = this.clienteRepository.buscarPorNif(socio.getNif());
        List<ClienteEntity> asociados = new ArrayList<>();
        asociados.add(clienteBD);
        empresaBD.setClientesById(asociados);

        SolicitudEntity solicitud = new SolicitudEntity();
        EstadoSolicitudEntity estadoPendiente = this.estadoSolicitudRepository.buscarEstadoPendiente();
        TipoSolicitudEntity tipoAltaEmpresa = this.tipoSolicitudRepository.buscarTipoAltaEmpresa();
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoPendiente);
        solicitud.setTipoSolicitudByTipoSolicitudId(tipoAltaEmpresa);
        solicitud.setEmpleadoByEmpleadoId(this.empresaService.buscarGestorMenosOcupado());
        solicitud.setClienteByClienteId(clienteBD);
        this.solicitudRepository.save(solicitud);
    }

    public Empleado buscarEmpleado(String numero, String contrasena) {
        Empleado empleado = null;
        try {
            EmpleadoEntity entity = this.empleadoRepository.inicioSesion(Integer.parseInt(numero), contrasena);
            if(entity != null) {
                empleado = entity.toDto();
            }
        } catch (NumberFormatException e) {

        }
        return empleado;
    }

    public Solicitud buscarSolicitudAltaPorIdCliente(Integer id) {
        SolicitudEntity solicitud = this.solicitudRepository.buscarSolicitudAltaClientePorIdCliente(id);
        if(solicitud != null) {
            return solicitud.toDTO();
        } else {
            return null;
        }
    }

    public Empresa buscarEmpresa(String cif, String contrasena) {
        EmpresaEntity entity = this.empresaRepository.inicioSesion(cif, contrasena);
        Empresa empresa = null;

        if(entity != null) {
            SolicitudEntity solicitud = this.solicitudRepository.buscarSolicitudEmpresaInicioSesion(entity.getClientesById());
            if(solicitud == null) {
                empresa = entity.toDTO();
            }
        }

        return empresa;
    }
}
