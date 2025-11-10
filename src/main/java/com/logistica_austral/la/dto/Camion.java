package com.logistica_austral.la.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "camiones")
public class Camion {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer idCamion;

    private String marcaCamion;
    private String nombreCamion;

    @Column(name = "annio_camion")
    private Integer annioCamion;

    @Column(length = 250)
    private String descripcionCamion;

    private String imagenCamion; // String ya que colocamos el URL.
    private String imagenLateralCamion; // String ya que colocamos el URL.
    private String traccionCamion;
    private String motorCamion;

    @Column(name = "longitud_max_camion")
    private String longitudMaxCamion; // Cuanto es lo que mide el camion en metros.

    @Column(name = "eje_camion")
    private String ejeCamion; // 4x2 - 6x4 - 8x4.

    private Double pesoCamion; // en toneladas.
    private String tipoCamion; // "Tracto" - "Rigido" - "Tolva".
    private Boolean disponibleCamion;

}
