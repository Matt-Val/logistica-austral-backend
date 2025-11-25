package com.logistica_austral.la.service;

import java.util.List;

import com.logistica_austral.la.dto.Usuario;
import com.logistica_austral.la.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public List<Usuario> getAllUsuarios() { 
        return repository.findAll();
    }

    public Usuario registrarUsuario(Usuario usuario) { 
        // 1. Validamos que no exista
        if (repository.existsByCorreoUsuario(usuario.getCorreoUsuario())) { 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        if (repository.existsByRutUsuario(usuario.getRutUsuario())) { 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado");
        }

        // 2. Se guarda la contraseña.
        usuario.setPasswordUsuario(usuario.getPasswordUsuario());

        // 3. Aseguramos que no sea admin.
        usuario.setEsAdmin(false);

        // 4. Guardamos el usuario
        return repository.save(usuario);
    }

    public Usuario loginUsuario(String correo, String password) { 
        Usuario usuario = repository.findByCorreoUsuario(correo)
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos"));
        
        if (usuario.getPasswordUsuario().equals(password)) { 
            // Si la contraseña es correcta
            return usuario;
        } else { 
            // Si la contraseña es incorrecta
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o Contraseña incorrectos");
        }
    }
    
    public Usuario registrarUsuarioAdmin(Usuario usuario) { 
        // 1. Validamos que no exista
        if (repository.existsByCorreoUsuario(usuario.getCorreoUsuario())) { 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        if (repository.existsByRutUsuario(usuario.getRutUsuario())) { 
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado");
        }

        // 2. Se guarda la contraseña.
        usuario.setPasswordUsuario(usuario.getPasswordUsuario());

        // 3. Aseguramos que sea admin.
        usuario.setEsAdmin(true);

        // 4. Guardamos el usuario
        return repository.save(usuario);
    }
}
