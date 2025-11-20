package com.logistica_austral.la.service;

import java.util.List;
import com.logistica_austral.la.dto.Camion;
import com.logistica_austral.la.repository.CamionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CamionService {

    private final CamionRepository repository;

    public List<Camion> obtenerCamionesDisponibles(String tipo) { 
        // tipo
        if (tipo == null ||  tipo.trim().isEmpty()) { 
            return repository.findByDisponibleCamion(true);
        } else { 
            return repository.findByTipoCamionAndDisponibleCamion(tipo, true);
        }

    }
    
    public Camion obtenerCamionesPorId(Integer id) { 
        return repository.findById(id)
            .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND, "Camion no encontrado con ID: " + id));
    }

    public Camion actualizarCamion(Integer id, Camion camionActualizado) { 
        // Verificar si el camión existe
        Camion cam = repository.findById(id)
            .orElseThrow ( () -> new ResponseStatusException( HttpStatus.NOT_FOUND, "Camión no encontrado con ID: " + id));

        // Actualiza campo por campo
        cam.setMarcaCamion(camionActualizado.getMarcaCamion());
        cam.setNombreCamion(camionActualizado.getNombreCamion());
        cam.setAnnioCamion(camionActualizado.getAnnioCamion());

        // Images
        cam.setImagenCamion(camionActualizado.getImagenCamion());
        cam.setImagenLateralCamion(camionActualizado.getImagenLateralCamion());

        // Detalles tecnicos
        cam.setTraccionCamion(camionActualizado.getTraccionCamion());
        cam.setMotorCamion(camionActualizado.getMotorCamion());
        cam.setLongitudMaxCamion(camionActualizado.getLongitudMaxCamion());
        cam.setEjeCamion(camionActualizado.getEjeCamion());
        cam.setPesoCamion(camionActualizado.getPesoCamion());
        cam.setTipoCamion(camionActualizado.getTipoCamion());

        // Disponibilidad
        cam.setDisponibleCamion(camionActualizado.getDisponibleCamion());

        // Guardamos los cambios
        return repository.save(cam);
    }

    public List<Camion> obtenerTodosLosCamionesAdmin() { 
        return repository.findAll();
    }

    public void eliminarCamion(Integer id) { 
        if (!repository.existsById(id)) { 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Camión no encontrado con ID: " + id);
        } else { 
            repository.deleteById(id);
        }
    }

}
