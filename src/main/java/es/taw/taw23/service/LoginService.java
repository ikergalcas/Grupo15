package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Cliente buscarCliente(String nif, String contrasena) {
        ClienteEntity entity = this.clienteRepository.inicioSesion(nif, contrasena);
        if(entity != null) {
            return entity.toDTO();
        } else {
            return null;
        }
    }
}
