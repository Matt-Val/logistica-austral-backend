package com.logistica_austral.la.controller;

import com.logistica_austral.la.dto.Camion;
import com.logistica_austral.la.service.CamionService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

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
     * Endpoint: /api/camiones/admin/{id}
     * Actualiza un camión por su ID (solo para admin).
     */
    @PutMapping("/admin/{id}")
    public ResponseEntity<Camion> editarCamion(@PathVariable Integer id, @RequestBody Camion camion) { 
        Camion camionEditado = camionService.actualizarCamion(id, camion);

        return ResponseEntity.ok(camionEditado);
    }

    /*
     * Endpoint: /api/camiones/admin/{id}
     * Elimina un camión por su ID (solo para admin).
     */
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> eliminarCamion (@PathVariable Integer id) { 
        camionService.eliminarCamion(id);
        return ResponseEntity.noContent().build();
    }

    /*
    * Endpoint: /api/camiones/admin
    * Obtiene todos los camiones (solo para admin).
    */
    @GetMapping("/admin/todos")
    public List<Camion> obtenerTodosLosCamiones() { 
        return camionService.obtenerTodosLosCamionesAdmin();
    }
}
