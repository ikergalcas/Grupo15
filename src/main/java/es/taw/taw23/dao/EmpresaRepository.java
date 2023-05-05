package es.taw.taw23.dao;

import es.taw.taw23.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

    /**
     * Esta consulta la usare exclusivamente para el registro ya que aunque haya varias empresas
     * con el mismo nombre se que todas deben de tener un socio salvo la que este en el proceso
     * de registro
     */
    @Query("select e from EmpresaEntity e where e.nombre = :name and e.clientesById.size = 0")
    public EmpresaEntity buscarEmpresaPorNombreRegistro(@Param("name") String name);

    @Query("select e from EmpresaEntity e where e.cif like concat(:cif, '%')")
    List<EmpresaEntity> findByFiltroCif(@Param("cif") String cif); /* Carla Serracant Guevara */
}
