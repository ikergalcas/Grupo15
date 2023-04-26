package es.taw.taw23.service;

import es.taw.taw23.dao.EmpleadoRepository;
import es.taw.taw23.dto.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsistenteService {
    @Autowired
    protected EmpleadoRepository empleadoRepository;

    public Empleado buscarEmpleado(int id){
        Empleado empleado = empleadoRepository.findById(id).orElse(null).toDTO();
        return empleado;
    }
}
