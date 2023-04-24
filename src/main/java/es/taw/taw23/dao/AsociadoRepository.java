package es.taw.taw23.dao;

import es.taw.taw23.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsociadoRepository extends JpaRepository<ClienteEntity, Integer> {

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.nombre = :nombreEmpresa")
    public List<ClienteEntity> buscarPorEmpresa(@Param("nombreEmpresa") String nombre);

    @Query("select c from ClienteEntity c where c.rolClienteByRolclienteId.tipo = 'autorizado' or c.rolClienteByRolclienteId.tipo = 'socio'")
    public List<ClienteEntity> buscarPorTipoEmpresa ();

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.id = :idempresa")
    public List<ClienteEntity> buscarSociosAutorizadosDeMiEmpresa(@Param("idempresa") Integer idEmpresa);

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.id = :idempresa and c.primerNombre like CONCAT('%', :primerNombre, '%')")
    public List<ClienteEntity> buscarPorPrimerNombre(@Param("idempresa") Integer idEmpresa, @Param("primerNombre") String nombre);

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.id = :idempresa and c.primerApellido like CONCAT('%', :primerApellido, '%')")
    public List<ClienteEntity> buscarPorPrimerApellido(@Param("idempresa") Integer idEmpresa, @Param("primerApellido") String apellido);

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.id = :idempresa and c.nif like CONCAT('%', :nif, '%') ")
    public List<ClienteEntity> buscarPorNif(@Param("idempresa") Integer idEmpresa, @Param("nif") String nif);

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.id = :idempresa and c.primerNombre like CONCAT('%', :primerNombre, '%') and c.primerApellido like CONCAT('%', :primerApellido, '%')")
    public List<ClienteEntity> buscarPorPrimerNombreyPrimerApellido(@Param("idempresa") Integer idEmpresa, @Param("primerNombre") String nombre, @Param("primerApellido") String apellido);

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.id = :idempresa and c.nif like CONCAT('%', :nif, '%') and c.primerNombre like CONCAT('%', :primerNombre, '%')")
    public List<ClienteEntity> buscarPorNifyPrimerNombre(@Param("idempresa") Integer idEmpresa, @Param("nif") String nif, @Param("primerNombre") String nombre);

    @Query("select c from ClienteEntity c where c.empresaByEmpresaId.id = :idempresa and c.nif like CONCAT('%', :nif, '%') and c.primerApellido like CONCAT('%', :primerApellido, '%')")
    public List<ClienteEntity> buscarPorNifyPrimerApellido(@Param("idempresa") Integer idEmpresa, @Param("nif") String nif, @Param("primerApellido") String apellido);
}

