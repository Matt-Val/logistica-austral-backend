package com.logistica_austral.la.service;

import com.logistica_austral.la.dto.*;
import com.logistica_austral.la.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camion no encontrado: ID " + itemDto.getIdCamion()));
            
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

    public void eliminarCotizacion(Integer id) { 

        // Se busca la cotización
        Cotizacion coti = cotizacionRepository.findById(id)
            .orElseThrow( () -> new RuntimeException("Cotización no encontrada con ID: " + id));

        // buscar y eliminar detalles asociados
        List<DetalleCotizacion> detalles = detalleCotizacionRepository.findByCotizacion(coti);
        detalleCotizacionRepository.deleteAll(detalles);

        // Eliminar la cotizacion
        cotizacionRepository.delete(coti);
        
    }

    public List<Cotizacion> listarTodasCotizaciones() { 
        return cotizacionRepository.findAll();
    }

    public Cotizacion cambiarEstadoCotizacion(Integer id, String nuevoEstado) { 
        Cotizacion coti = cotizacionRepository.findById(id)
            .orElseThrow( () -> new RuntimeException("Cotizacion no encontrada"));
        
        coti.setEstadoCotizacion(nuevoEstado);
        return cotizacionRepository.save(coti);
    }

}
