package com.logistica_austral.la.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.logistica_austral.la.dto.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /*
     * Busca un usuario por su correo.
     * Se usa optional para manejar de mejor manera si el usuario no existe.
     */
    Optional<Usuario> findByCorreoUsuario(String correoUsuario);

    /*
     * Verifica si el usuario ya existe por su correo.
     */
    Boolean existsByCorreoUsuario(String correoUsuario);

    /*
     * Verifica si el usuario ya existe por su RUT.
     */
    Boolean existsByRutUsuario(String rutUsuario);

}
