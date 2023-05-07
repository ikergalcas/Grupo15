package es.taw.taw23.dao;

import es.taw.taw23.entity.CuentaClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Hecho por:
 * Álvaro Yuste Moreno
 * Carla Serracant Guevara (25%)
 */
public interface CuentaClienteRepository extends JpaRepository<CuentaClienteEntity, Integer> {

    @Query("select c from CuentaClienteEntity c where c.clienteByClienteId.id = :idCliente")
    List<CuentaClienteEntity> buscarCuentaClientePorIdCliente(@Param("idCliente") Integer id);

    @Query("select c from CuentaClienteEntity c where c.clienteByClienteId.id = :idCliente and c.cuentaByCuentaId.estadoCuentaByEstadoCuentaId.estadoCuenta = 'activa'")
    List<CuentaClienteEntity> buscarCuentasActivas(@Param("idCliente") Integer id);

    @Query("select c from CuentaClienteEntity c where c.clienteByClienteId.id = :idCliente")
    List<CuentaClienteEntity> buscarCuentasCliente(@Param("idCliente") Integer id);

    @Query("select c from CuentaClienteEntity c where c.clienteByClienteId.id = :idCliente and c.cuentaByCuentaId.id = :idCuenta")
    CuentaClienteEntity buscarPorClienteYCuenta(@Param("idCliente") Integer id, @Param("idCuenta") Integer id1);

}
