package com.logistica_austral.la.controller;

import com.logistica_austral.la.dto.Cotizacion;
import com.logistica_austral.la.dto.CotizacionRequestDTO;
import com.logistica_austral.la.service.CotizacionService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/cotizaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CotizacionController {
    
    private final CotizacionService cotizacionService;

    /*
     *  Endpoint: /api/cotizaciones
     *  Recibe el formulario completo y lo guarda en la base de datos
     *  
     */
    @PostMapping
    public ResponseEntity<Cotizacion> crearCotizacion(@RequestBody CotizacionRequestDTO cotizacionRequest) { 
        Cotizacion nuevaCotizacion = cotizacionService.crearCotizacion(cotizacionRequest);
        return ResponseEntity.ok(nuevaCotizacion);
    }

    /* 
     *  Endpoint: /api/cotizaciones/{id}
     *  Elimina una cotización por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCotizacion(@PathVariable Integer id) { 
        cotizacionService.eliminarCotizacion(id);
        return ResponseEntity.noContent().build();
    }

    /*
    *  Endpoint: /api/cotizaciones/admin/todas
    *  Obtiene todas las cotizaciones (solo para admin).
    */
    @GetMapping("/admin")
    public List<Cotizacion> verTodasCotizaciones() { 
        return cotizacionService.listarTodasCotizaciones();
    }

    /*
    *  Endpoint: /api/cotizaciones/admin/{id}/estado
    *  Actualiza el estado de una cotización (solo para admin).
    */

    @PutMapping("/admin/{id}/estado")
    public ResponseEntity<Cotizacion> actualizarEstado(@PathVariable Integer id, @RequestParam String estado) {
        Cotizacion coti = cotizacionService.cambiarEstadoCotizacion(id, estado);
        return ResponseEntity.ok(coti);
    }

}
