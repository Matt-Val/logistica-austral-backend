package com.logistica_austral.la.controller;

import com.logistica_austral.la.dto.LoginRequestDTO;
import com.logistica_austral.la.dto.Usuario;
import com.logistica_austral.la.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite las peticiones desde cualquier origen
public class AuthController {
    
    private final UsuarioService usuarioService;


    /*
     * Endpoint: /api/auth/registro
     * Registra un nuevo usuario
     */
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) { 
        Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(usuarioRegistrado);
    }

    /*
     * Endpoint: /api/auth/login
     * Autentica el servicio
     */
    @PostMapping("/login")
    public ResponseEntity<Usuario> loginUsuario(@RequestBody LoginRequestDTO loginRequest) { 
        Usuario usuarioLogeado = usuarioService.loginUsuario(
            loginRequest.getCorreo(),
            loginRequest.getPassword()
        );
        return ResponseEntity.ok(usuarioLogeado);
    }
}
