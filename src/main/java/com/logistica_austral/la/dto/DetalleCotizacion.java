package com.logistica_austral.la.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalles_cotizaciones")
public class DetalleCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;
    
    // Relación con Cotización.
    // Un detalle pertenece a UNA cotización.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cotizacion", nullable = false)
    private Cotizacion cotizacion;
    
    // Relación con Camion.
    // Un detalle se refiere a UN camión.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_camion", nullable = false)
    private Camion camion;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cantidadMeses;
    
}
