package com.logistica_austral.la.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class CotizacionRequestDTO {
    // Datos del formulario de Cotizacion.jsx
    private String nombreCliente;
    private String rutCliente;
    private String telefonoCliente;
    private String emailCliente;
    private String region;
    private LocalDate fechaInicioEstimada;

    // El ID del usuario que hace la solicitud (si está logueado)
    private Integer idUsuario; 

    // Lista de items del carrito
    private List<ItemCotizacionDTO> items;
}