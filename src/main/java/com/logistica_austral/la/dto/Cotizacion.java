package com.logistica_austral.la.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer idCotizacion;

    // Establecemos relacion con Usuario.
    // Un usuario puede tener muchas cotizaciones.
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: No cargar el usuario hasta que se necesite.
    @JoinColumn(name = "idUsuario") // Nombre de la columna Foreign Key.
    private Usuario usuarioCotizacion;
    

    // Datos de formulario de cotización.
    // Se guardan en caso que el usuario estaba logueado.
    private String nombreClienteCotizacion;
    private String rutClienteCotizacion;
    private String correoClienteCotizacion;
    private String telefonoClienteCotizacion;
    private String regionCotizacion;

    private LocalDate fechaInicioCotizacion;

    private String estadoCotizacion; // "Pendiente" - "Aprobada" - "Rechazada".

    private LocalDateTime fechaSolicitudCotizacion = LocalDateTime.now(); // Se setea al crearla.
    


}
