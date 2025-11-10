package com.logistica_austral.la.service;

import com.logistica_austral.la.dto.*;
import com.logistica_austral.la.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CotizacionService {


    private final CotizacionRepository cotizacionRepository;
    private final DetalleCotizacionRepository detalleCotizacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CamionRepository camionRepository;
    

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
        
        // 2. Si el User estaba logeado, lo asociamos
        if (request.getIdUsuario() != null) { 
            Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElse(null);
            cotizacion.setUsuarioCotizacion(usuario);
        }

        // 3. Guardamos la cotización para obtener su ID.
        Cotizacion cotizacionGuardada = cotizacionRepository.save(cotizacion);

        // 4. Guardamos los items del carrito
        for (ItemCotizacionDTO itemDto : request.getItems()) { 
            
            Camion camion = camionRepository.findById(itemDto.getIdCamion())
                .orElseThrow( () -> new RuntimeException("Camion no encontrado: ID " + itemDto.getIdCamion()));
            
            DetalleCotizacion detalle = new DetalleCotizacion();
            detalle.setCotizacion(cotizacionGuardada);
            detalle.setCamion(camion);
            detalle.setFechaInicio(itemDto.getFechaInicio());
            detalle.setFechaFin(itemDto.getFechaFin());
            detalle.setCantidadMeses(itemDto.getMeses());

            detalleCotizacionRepository.save(detalle);
        }
        return cotizacionGuardada;
    }

}
