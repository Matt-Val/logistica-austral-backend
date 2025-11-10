package com.logistica_austral.la.service;

import com.logistica_austral.la.dto.Camion;
import com.logistica_austral.la.repository.CamionRepository;
import com.logistica_austral.la.repository.CotizacionRepository;
import com.logistica_austral.la.repository.DetalleCotizacionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamionService {

    private final DetalleCotizacionRepository detalleCotizacionRepository;

    private final CotizacionRepository cotizacionRepository;
    
    @Autowired
    private CamionRepository repository;

    CamionService(CotizacionRepository cotizacionRepository, DetalleCotizacionRepository detalleCotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.detalleCotizacionRepository = detalleCotizacionRepository;
    }

    public List<Camion> obtenerCamionesDisponibles(String tipo) { 
        // tipo
        if (tipo == null ||  tipo.trim().isEmpty()) { 
            return repository.findByDisponibleCamion(true);
        } else { 
            return repository.findByTipoCamionAndDisponibleCamion(tipo, true);
        }

    }
    
    public Camion obtenerCamionesPorId(Integer id) { 
        Optional<Camion> camionOptional = repository.findById(id);
        // Si no lo encuentra, que lance un error
        if (camionOptional.isEmpty()) { 
            throw new RuntimeException("Camión no encontrado con ID: " + id);
        }
        return camionOptional.get();
    }

    public Camion actualizarDisponibilidadCamion(Integer id, boolean disponible) { 
        // 1. Busca un camión por ID.
        Camion camion = obtenerCamionesPorId(id);

        // 2. Actualiza su disponibilidad.
        camion.setDisponibleCamion(disponible);

        // 3. Guarda los cambios en la base de datos.
        return repository.save(camion);
    }

    public List<Camion> obtenerTodosLosCamionesAdmin() { 
        return repository.findAll();
    }



}
