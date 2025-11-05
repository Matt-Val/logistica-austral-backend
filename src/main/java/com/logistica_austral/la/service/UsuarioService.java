package com.logistica_austral.la.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.logistica_austral.la.dto.Usuario;
import com.logistica_austral.la.repository.UsuarioRepository;

@Service
public class UsuarioService {
    // Encargado de conectar la lógica de negocio.

    @Autowired
    private UsuarioRepository repository;
    // Cuando se inicializa uno, también se inicializa el otro, ninguno se desconecta.

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public List<Usuario> getAllUsuarios() { 
        return repository.findAll();
    }

    public Usuario registrarUsuario(Usuario usuario) { 
        // 1. Validamos que no exista
        if (repository.existsByCorreoUsuario(usuario.getCorreoUsuario())) { 
            throw new RuntimeException("El correo ya está registrado");
        }
        if (repository.existsByRutUsuario(usuario.getRutUsuario())) { 
            throw new RuntimeException("El RUT ya está registrado");
        }
        // 2. Hashear password, no se guarda en texto plano
        String passwordHash = passwordEncoder.encode(usuario.getPasswordUsuario());
        usuario.setPasswordUsuario(passwordHash);

        // 3. Aseguramos que no sea admin.
        usuario.setEsAdmin(false);

        // 4. Guardamos el usuario
        return repository.save(usuario);
    }
    
}
