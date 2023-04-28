package es.taw.taw23.dao;

import java.util.List;

import es.taw.taw23.entity.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MensajeRepository extends JpaRepository<MensajeEntity,Integer> {
    @Query("select m from MensajeEntity m where m.chatByChatId.id = :chatId")
    public List<MensajeEntity> findByChatId(@Param("chatId") int chatId);
}
