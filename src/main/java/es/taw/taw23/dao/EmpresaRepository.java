package es.taw.taw23.dao;

import es.taw.taw23.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    /**
     * Esta consulta la usare exclusivamente para el registro ya que aunque haya varias empresas
     * con el mismo nombre se que todas deben de tener un socio salvo la que este en el proceso
     * de registro
     */
    @Query("select e from Empresa e where e.nombre = :name and e.clientesByIdEmpresa.size = 0")
    public Empresa buscarEmpresaPorNombreRegistro(@Param("name") String name);
}
