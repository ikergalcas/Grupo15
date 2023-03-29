package es.taw.taw23.dao;

import es.taw.taw23.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Cliente, Integer> {

    @Query("select c from Cliente c where c.rolclienteByRolclienteId.tipo = 'autorizado' or c.rolclienteByRolclienteId.tipo = 'socio'")
    public List<Cliente> buscarPorTipoEmpresa ();

    @Query("select c from Cliente c where c.empresaByEmpresaIdEmpresa.idEmpresa = :idempresa")
    public List<Cliente> buscarSociosAutorizadosDeMiEmpresa(@Param("idempresa") Integer idEmpresa);
}

