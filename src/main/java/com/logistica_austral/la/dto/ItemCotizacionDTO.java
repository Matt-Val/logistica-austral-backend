package com.logistica_austral.la.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ItemCotizacionDTO {
    // Atributo ID
    private Integer idCamion;
    
    // Fechas que el usuario elige
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Integer meses;
}