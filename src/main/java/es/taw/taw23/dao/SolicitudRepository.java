package es.taw.taw23.dao;

import es.taw.taw23.entity.SolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<SolicitudEntity,Integer> {
    @Query("select s from SolicitudEntity s where s.estado = 'pendiente' and s.empleadoByEmpleadoId.id = :idGestor")
    public List<SolicitudEntity> buscarSolicitudesPendientesDeUnGestor(@Param("idGestor") Integer id);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado = 'pendiente'")
    public List<SolicitudEntity> buscarSolicitudesPendientesPorCliente(@Param("idCliente") Integer idCliente);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado <> 'resuelta' and s.tipoSolicitudByTipoSolicitudId.tipo = 'alta_empresa'")
    SolicitudEntity buscarSolicitudDeAltaEmpresaPendienteoDenegada(@Param("idCliente") Integer id);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado = 'pendiente' and s.tipoSolicitudByTipoSolicitudId.tipo = 'desbloqueo'")
    List<SolicitudEntity> buscarSolicitudesPendientesPorClienteTipoDesbloqueo(@Param("idCliente") Integer id);

    @Query("select s from SolicitudEntity s where s.clienteByClienteId.id = :idCliente and s.estadoSolicitudByEstadoSolicitudId.estado = 'pendiente' and s.tipoSolicitudByTipoSolicitudId.tipo = 'activacion'")
    List<SolicitudEntity> buscarSolicitudesPendientesPorClienteTipoActivacion(@Param("idCliente") Integer id);
}
