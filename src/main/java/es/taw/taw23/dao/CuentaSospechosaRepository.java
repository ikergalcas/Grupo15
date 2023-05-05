package es.taw.taw23.dao;

import es.taw.taw23.entity.CuentaSospechosaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaSospechosaRepository extends JpaRepository<CuentaSospechosaEntity,Integer> {

    /* Carla Serracant Guevara */
    @Query("Select cs from CuentaSospechosaEntity cs where cs.cuentaByCuentaId.id = :id")
    public CuentaSospechosaEntity findCuentaSospechosaByIdCuenta(@Param("id") Integer id);
}
