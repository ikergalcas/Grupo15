package es.taw.taw23.dao;

import es.taw.taw23.entity.ClienteEntity;
import es.taw.taw23.entity.SolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Hecho por:
 * Álvaro Yuste Moreno 40%
 * Carla Serracant Guevara 30%
 * Rocío Gómez Mancebo 30%
 */
public interface SolicitudRepository extends JpaRepository<SolicitudEntity,Integer> {
    @Query("select s from SolicitudEntity s where s.estadoSolicitudByEstadoSolicitudId.estado = 'pendiente' and s.empleadoByEmpleadoId.id = :idGestor")
    public List<SolicitudEntity> buscarSolicitudesPendientesDeUnGestor(@Param("idGestor") Integer id);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado = 'pendiente'")
    public List<SolicitudEntity> buscarSolicitudesPendientesPorCliente(@Param("idCliente") Integer idCliente);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado <> 'aceptada' and s.tipoSolicitudByTipoSolicitudId.tipo = 'alta_empresa'")
    SolicitudEntity buscarSolicitudDeAltaEmpresaPendienteoDenegada(@Param("idCliente") Integer id);

    @Query("select s from SolicitudEntity s where s.estadoSolicitudByEstadoSolicitudId.estado <> 'resuelta' and s.tipoSolicitudByTipoSolicitudId.tipo = 'alta_empresa'")
    List<SolicitudEntity> buscarTodasSolicitudDeAltaEmpresaPendienteoDenegada();

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado = 'pendiente' and s.tipoSolicitudByTipoSolicitudId.tipo = 'desbloqueo_individual'")
    List<SolicitudEntity> buscarSolicitudesPendientesPorClienteTipoDesbloqueoIndividual(@Param("idCliente") Integer id);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado = 'pendiente' and s.tipoSolicitudByTipoSolicitudId.tipo = 'activa_individual'")
    List<SolicitudEntity> buscarSolicitudesPendientesPorClienteTipoActivacionIndividual(@Param("idCliente") Integer id);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.tipoSolicitudByTipoSolicitudId.tipo = 'alta_cliente' and s.estadoSolicitudByEstadoSolicitudId.estado <> 'aceptada'" )
    public SolicitudEntity buscarSolicitudAltaClientePorIdCliente(@Param("idCliente") Integer idCliente);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.tipoSolicitudByTipoSolicitudId.tipo = 'alta_empresa' and s.estadoSolicitudByEstadoSolicitudId.estado <> 'aceptada'" )
    public SolicitudEntity buscarSolicitudAltaEmpresaPorIdCliente(@Param("idCliente") Integer idCliente);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId in :asociados and s.estadoSolicitudByEstadoSolicitudId.estado <> 'aceptada' and s.tipoSolicitudByTipoSolicitudId.tipo = 'alta_empresa'")
    SolicitudEntity buscarSolicitudEmpresaInicioSesion(@Param("asociados")List<ClienteEntity> asociados);


    /* Carla Serracant Guevara */
    @Query("select s from SolicitudEntity s where s.estadoSolicitudByEstadoSolicitudId.estado = 'aceptada' and s.empleadoByEmpleadoId.id = :idGestor")
    public List<SolicitudEntity> buscarSolicitudesResueltasDeUnGestor(@Param("idGestor") Integer id);

    /* Carla Serracant Guevara */
    @Query("select s from SolicitudEntity s where s.estadoSolicitudByEstadoSolicitudId.estado = 'denegada' and s.empleadoByEmpleadoId.id = :idGestor")
    public List<SolicitudEntity> buscarSolicitudesNegadasDeUnGestor(@Param("idGestor") Integer id);
}
