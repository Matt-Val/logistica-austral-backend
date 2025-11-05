package com.logistica_austral.la.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.logistica_austral.la.dto.DetalleCotizacion;
import com.logistica_austral.la.dto.Cotizacion;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Integer> {
    
    /*
     * Busca todos los items que pertenecen a una cotización específica
     */
    List<DetalleCotizacion> findByCotizacion(Cotizacion cotizacion);

}
