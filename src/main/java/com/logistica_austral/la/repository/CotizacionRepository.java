package com.logistica_austral.la.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.logistica_austral.la.dto.Cotizacion;
import com.logistica_austral.la.dto.Usuario;


@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer > {
    
    /*
     * Busca las cotizaciones hechas por un cliente específico.
     */
    List<Cotizacion> findByUsuarioCotizacion(Usuario usuario);

}
