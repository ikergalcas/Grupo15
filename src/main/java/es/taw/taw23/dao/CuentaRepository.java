package es.taw.taw23.dao;

import es.taw.taw23.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {

    @Query("select c from CuentaEntity c where c.numeroCuenta = :numero")
    public CuentaEntity buscarCuentaPorNumeroCuenta(@Param("numero") String numeroCuenta);
}
