package com.logistica_austral.la.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ItemCotizacionDTO {
    private Long idCamion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer meses;
}