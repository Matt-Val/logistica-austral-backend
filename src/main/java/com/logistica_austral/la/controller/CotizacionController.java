package com.logistica_austral.la.controller;

import com.logistica_austral.la.dto.Cotizacion;
import com.logistica_austral.la.dto.CotizacionRequestDTO;
import com.logistica_austral.la.service.CotizacionService;

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
}
