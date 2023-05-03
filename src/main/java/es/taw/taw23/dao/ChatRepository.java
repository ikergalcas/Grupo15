package es.taw.taw23.dao;

import es.taw.taw23.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity,Integer> {
    @Query("select c from ChatEntity c where c.empleadoByEmpleadoId.id = :empleadoId")
    public List<ChatEntity> findByEmpleadoId(@Param("empleadoId") int empleadoId);

    @Query("select c from ChatEntity c where c.clienteByClienteId.id = :clienteId and c.cerrado = 0")
    public ChatEntity findChatAbierto(@Param("clienteId") int clienteId);

    @Query("select c from ChatEntity c where c.empleadoByEmpleadoId.id = :empleadoId and c.clienteByClienteId.nif = :nif order by c.cerrado asc")
    public List<ChatEntity> findByEmpleadoIdFiltrarByNifOrdernadByCerrado(@Param("empleadoId") int empleadoId, @Param("nif") String nif);

    @Query("select c from ChatEntity c where c.empleadoByEmpleadoId.id = :empleadoId and c.clienteByClienteId.nif = :nif order by c.cerrado desc")
    public List<ChatEntity> findByEmpleadoIdFiltrarByNifOrdernadByAbierto(@Param("empleadoId") int empleadoId, @Param("nif") String nif);

    @Query("select c from ChatEntity c where c.empleadoByEmpleadoId.id = :empleadoId order by c.cerrado asc")
    public List<ChatEntity> findByEmpleadoIdCerrado(@Param("empleadoId") int empleadoId);

    @Query("select c from ChatEntity c where c.empleadoByEmpleadoId.id = :empleadoId order by c.cerrado desc")
    public List<ChatEntity> findByEmpleadoIdAbierto(@Param("empleadoId") int empleadoId);

    @Query("select c from ChatEntity c where c.empleadoByEmpleadoId.id = :empleadoId and c.clienteByClienteId.nif = :nif")
    public List<ChatEntity> findByEmpleadoIdFiltrarByNif(@Param("empleadoId") int empleadoId, @Param("nif") String nif);

    @Query("select c from ChatEntity c where c.empleadoByEmpleadoId.id = :asistenteId and c.cerrado = 0")
    public List<ChatEntity> findChatsAbiertos(@Param("asistenteId") int asistenteId);

    @Query("select c from ChatEntity c where c.clienteByClienteId.id = :clienteId and c.cerrado = 0")
    public List<ChatEntity> findChatsAbiertosCliente(@Param("clienteId") int clienteId);
}
