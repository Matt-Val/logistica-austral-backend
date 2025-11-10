package com.logistica_austral.la.controller;

import com.logistica_austral.la.dto.Camion;
import com.logistica_austral.la.service.CamionService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/camiones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite las peticiones desde cualquier origen
public class CamionController {

    private final CamionService camionService;


    /*
     * Endpoint: /api/camiones
     * Obtiene todos los camiones disponibles, opcionalmente filtrados por tipo.
     */
    @GetMapping
    public List<Camion> obtenerCamionesDisponibles( @RequestParam(required = false) String tipo) {
        return camionService.obtenerCamionesDisponibles(tipo);
    }

    /*
     * Endpoint: /api/camiones/{id}
     * Obtiene un camión por su ID.
     */
    @GetMapping("/{id}")
    public Camion obtenerCamionPorId(@PathVariable Integer id) { 
        return camionService.obtenerCamionesPorId(id);
    }

    /*
     * Endpoint: /api/camiones/admin/disponible=false
     * Actualiza la disponibilidad de un camión (solo para admin).
     */
    @PutMapping("/admin/{id}")
    public Camion actualizarDisponibilidadCamion(@PathVariable Integer id, @RequestParam boolean disponible) { 
        return camionService.actualizarDisponibilidadCamion(id, disponible);
    }
}
