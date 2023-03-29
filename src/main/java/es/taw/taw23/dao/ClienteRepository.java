package es.taw.taw23.dao;

import es.taw.taw23.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("select c from Cliente c where c.rolclienteByRolclienteId.tipo = 'autorizado' or c.rolclienteByRolclienteId.tipo = 'empresa'")
    public List<Cliente> buscarPorTipoEmpresa ();

}
