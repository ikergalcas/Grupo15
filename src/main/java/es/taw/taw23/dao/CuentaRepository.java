package es.taw.taw23.dao;

import es.taw.taw23.entity.Cuenta;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

    @Query("select c from Cuenta c where c.numeroCuenta = :numero")
    public Cuenta buscarCuentaPorNumeroCuenta(@Param("numero") String numeroCuenta);
}
