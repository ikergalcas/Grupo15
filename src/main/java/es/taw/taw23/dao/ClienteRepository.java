package es.taw.taw23.dao;

import es.taw.taw23.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query("select c from Cliente c where c.nif = :nif and c.contrasena = :contrasena")
    public Cliente inicioSesion (@Param("nif") String nif, @Param("contrasena") String contrasena);
}
