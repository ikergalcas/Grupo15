package es.taw.taw23.dao;

import es.taw.taw23.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Hecho por:
 * Rocío Gómez Mancebo
 */
public interface IndividualRepository extends JpaRepository<ClienteEntity,Integer> {

}
