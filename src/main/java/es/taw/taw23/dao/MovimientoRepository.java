package es.taw.taw23.dao;

import es.taw.taw23.entity.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimientos, Integer> {
}
