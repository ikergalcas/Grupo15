package es.taw.taw23.dao;

import es.taw.taw23.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.net.Inet4Address;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer> {
    @Query("select e from EmpleadoEntity e where e.numeroEmpleado = :numero and e.contraseña = :contrasena")
    public EmpleadoEntity inicioSesion (@Param("numero") int num, @Param("contrasena") String contrasena);
}
