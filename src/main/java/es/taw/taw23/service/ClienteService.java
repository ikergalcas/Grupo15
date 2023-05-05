package es.taw.taw23.service;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    protected ClienteRepository clienteRepository;

    public List<Cliente> buscarTodosLosClientes() {
        /* Carla Serracant Guevara */
        List<Cliente> listaClienteDTO = new ArrayList<>();
        List<ClienteEntity> clientesEntity = clienteRepository.findAll();
        for (ClienteEntity c : clientesEntity) {
            listaClienteDTO.add(c.toDTO());
        }
        return listaClienteDTO;
    }

    public Cliente buscarClientePorId(Integer id) {
        /* Carla Serracant Guevara */
        ClienteEntity cliente = clienteRepository.findById(id).orElse(null);
        return cliente.toDTO();
    }

    public List<Cliente> buscarClientePorFiltroNif(String nif) {
        /* Carla Serracant Guevara */
        List<ClienteEntity> clienteEntities = clienteRepository.findByFiltroNif(nif);
        List<Cliente> clientes = new ArrayList<>();
        for (ClienteEntity c : clienteEntities) {
            clientes.add(c.toDTO());
        }

        return clientes;
    }

}
