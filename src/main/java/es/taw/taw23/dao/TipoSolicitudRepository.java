package es.taw.taw23.dao;

import es.taw.taw23.entity.TipoSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * Hecho por:
 * Rocío Gómez Mancebo: 80%
 * Alvaro Yuste Moreno: 20%
 */

public interface TipoSolicitudRepository extends JpaRepository<TipoSolicitudEntity, Integer> {

    @Query("select t from TipoSolicitudEntity t where t.tipo = 'activa_individual'")
    public TipoSolicitudEntity buscarTipoActivacionIndividual();

    @Query("select t from TipoSolicitudEntity t where t.tipo = 'desbloqueo_individual'")
    public TipoSolicitudEntity buscarTipoDesbloqueoIndividual();

    @Query("select t from TipoSolicitudEntity t where t.tipo = 'alta_empresa'")
    TipoSolicitudEntity buscarTipoAltaEmpresa();

    @Query("select t from TipoSolicitudEntity t where t.tipo = 'alta_cliente'")
    public TipoSolicitudEntity buscarTipoAltaIndividual();
}
