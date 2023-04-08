package es.taw.taw23.dao;

import es.taw.taw23.entity.Tipomovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMovimientoRepository extends JpaRepository<Tipomovimiento, Integer> {
}
