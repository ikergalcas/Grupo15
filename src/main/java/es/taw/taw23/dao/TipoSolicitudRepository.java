package es.taw.taw23.dao;

import es.taw.taw23.entity.TipoSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipoSolicitudRepository extends JpaRepository<TipoSolicitudEntity, Integer> {

    @Query("select t from TipoSolicitudEntity t where t.tipo = 'activacion'")
    public TipoSolicitudEntity buscarTipoActivacion();

    @Query("select t from TipoSolicitudEntity t where t.tipo = 'desbloqueo'")
    public TipoSolicitudEntity buscarTipoDesbloqueo();

    @Query("select t from TipoSolicitudEntity t where t.tipo = 'alta_empresa'")
    TipoSolicitudEntity buscarTipoAltaEmpresa();
}
