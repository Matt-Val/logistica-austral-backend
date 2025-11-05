package com.logistica_austral.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.logistica_austral.la.dto.Camion;
import java.util.List;

@Repository
public interface CamionRepository extends JpaRepository<Camion, Integer> {
    

    /*
     * Busca todos los camiones que estén disponibles para arrendar.
     */
    List<Camion> findByDisponibleCamion(Boolean disponibleCamion);

    /*
     * Busca todos los camiones de un tipo específico.
     * Y además que estén disponibles.
     */
    List<Camion> findByTipoCamionAndDisponibleCamion(String tipo, Boolean disponibleCamion);
}
