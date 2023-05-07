package es.taw.taw23.dao;

import es.taw.taw23.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Hecho por:
 * Álvaro Yuste Moreno (66%)
 * Carla Serracant (34%)
 */
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    @Query("select c from ClienteEntity c where c.nif = :nif")
    public ClienteEntity buscarPorNif(@Param("nif") String nif);

    @Query("select c from ClienteEntity c where c.nif = :nif and c.contrasena = :contrasena")
    public ClienteEntity inicioSesion (@Param("nif") String nif, @Param("contrasena") String contrasena);

    @Query("select c from ClienteEntity c where c.nif like concat(:nif, '%')")
    List<ClienteEntity> findByFiltroNif(@Param("nif") String nif); /* Carla Serracant Guevara */
}
