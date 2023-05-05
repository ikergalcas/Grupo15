package es.taw.taw23.service;

import es.taw.taw23.dao.AsistenteRepository;
import es.taw.taw23.dao.ChatRepository;
import es.taw.taw23.dao.EmpleadoRepository;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.entity.EmpleadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Hecho por: Iker Gálvez
 */
@Service
public class AsistenteService {
    @Autowired
    protected EmpleadoRepository empleadoRepository;

    @Autowired
    protected AsistenteRepository asistenteRepository;

    @Autowired
    protected ChatRepository chatRepository;

    public Empleado buscarEmpleado(int id){
        Empleado empleado = empleadoRepository.findById(id).orElse(null).toDto();
        return empleado;
    }

    public int buscarAsistenteMenosOcupadoId() {
        List<EmpleadoEntity> asistentes = asistenteRepository.findAsistentes();
        EmpleadoEntity asistenteMenosOcupado = null;
        for (EmpleadoEntity asistente : asistentes) {
            if (asistenteMenosOcupado == null) {
                asistenteMenosOcupado = asistente;
            } else {
                if (chatRepository.findChatsAbiertos(asistenteMenosOcupado.getId()).size() > chatRepository.findChatsAbiertos(asistente.getId()).size()) {
                    asistenteMenosOcupado = asistente;
                }
            }
        }
        return asistenteMenosOcupado.getId();
    }
}
