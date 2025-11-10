package com.logistica_austral.la.service;

import com.logistica_austral.la.dto.*;
import com.logistica_austral.la.repository.*;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private DetalleCotizacionRepository detalleCotizacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CamionRepository camionRepository;

    @Transactional
    public Cotizacion crearCotizacion(CotizacionRequestDTO request) { 
        // 1. Crea la cotizacion
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setNombreClienteCotizacion(request.getNombreCliente());
        cotizacion.setRutClienteCotizacion(request.getRutCliente());
        cotizacion.setCorreoClienteCotizacion(request.getEmailCliente());
        cotizacion.setTelefonoClienteCotizacion(request.getTelefonoCliente());
        cotizacion.setRegionCotizacion(request.getRegion());
        cotizacion.setFechaInicioCotizacion(request.getFechaInicioEstimada());
        cotizacion.setEstadoCotizacion("Pendiente");
        
        Cotizacion cotizacionGuardada = cotizacionRepository.save(cotizacion);

        return cotizacionGuardada;
    }

}
