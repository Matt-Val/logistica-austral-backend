package com.logistica_austral.la.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuarios")

public class Usuario {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false)
    private String nombreUsuario;

    @Column(unique = true, nullable = false)
    private String rutUsuario;

    @Column(unique = true, nullable = false)
    private String correoUsuario;

    @Column(nullable = false)
    private String telefonoUsuario;

    @Column(nullable = false)
    private String passwordUsuario;


    private boolean esAdmin = false;

}
