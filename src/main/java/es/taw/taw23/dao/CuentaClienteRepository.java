package es.taw.taw23.dao;

import es.taw.taw23.entity.CuentaClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuentaClienteRepository extends JpaRepository<CuentaClienteEntity, Integer> {

    @Query("select c from CuentaClienteEntity c where c.clienteByClienteId.id = :idCliente")
    List<CuentaClienteEntity> buscarCuentaClientePorIdCliente(@Param("idCliente") Integer id);
}
