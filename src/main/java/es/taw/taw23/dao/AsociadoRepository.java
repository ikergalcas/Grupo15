package es.taw.taw23.dao;

import es.taw.taw23.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsociadoRepository extends JpaRepository<Cliente, Integer> {

    @Query("select c from Cliente c where c.rolclienteByRolclienteId.tipo = 'autorizado' or c.rolclienteByRolclienteId.tipo = 'socio'")
    public List<Cliente> buscarPorTipoEmpresa ();

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa")
    public List<Cliente> buscarSociosAutorizadosDeMiEmpresa(@Param("idempresa") Integer idEmpresa);

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa and c.primerNombre like CONCAT('%', :primerNombre, '%')")
    public List<Cliente> buscarPorPrimerNombre(@Param("idempresa") Integer idEmpresa, @Param("primerNombre") String nombre);

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa and c.primerApellido like CONCAT('%', :primerApellido, '%')")
    public List<Cliente> buscarPorPrimerApellido(@Param("idempresa") Integer idEmpresa, @Param("primerApellido") String apellido);

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa and c.nif like CONCAT('%', :nif, '%') ")
    public List<Cliente> buscarPorNif(@Param("idempresa") Integer idEmpresa, @Param("nif") String nif);

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa and c.primerNombre like CONCAT('%', :primerNombre, '%') and c.primerApellido like CONCAT('%', :primerApellido, '%')")
    public List<Cliente> buscarPorPrimerNombreyPrimerApellido(@Param("idempresa") Integer idEmpresa, @Param("primerNombre") String nombre, @Param("primerApellido") String apellido);

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa and c.nif like CONCAT('%', :nif, '%') and c.primerNombre like CONCAT('%', :primerNombre, '%')")
    public List<Cliente> buscarPorNifyPrimerNombre(@Param("idempresa") Integer idEmpresa, @Param("nif") String nif, @Param("primerNombre") String nombre);

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa and c.nif like CONCAT('%', :nif, '%') and c.primerApellido like CONCAT('%', :primerApellido, '%')")
    public List<Cliente> buscarPorNifyPrimerApellido(@Param("idempresa") Integer idEmpresa, @Param("nif") String nif, @Param("primerApellido") String apellido);
}

