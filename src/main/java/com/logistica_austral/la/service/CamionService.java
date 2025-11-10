package com.logistica_austral.la.service;

import com.logistica_austral.la.dto.Camion;
import com.logistica_austral.la.repository.CamionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamionService {
    
    @Autowired
    private CamionRepository repository;

    public List<Camion> obtenerCamionesDisponibles(String tipo) { 
        // tipo
        if (tipo == null ||  tipo.trim().isEmpty()) { 
            return repository.findByDisponibleCamion(true);
        } else { 
            return repository.findByTipoCamionAndDisponibleCamion(tipo, true);
        }
    }
}
