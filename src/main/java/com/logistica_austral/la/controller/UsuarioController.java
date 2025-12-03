package com.logistica_austral.la.controller;

import com.logistica_austral.la.dto.Usuario;
import com.logistica_austral.la.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPerfil(@PathVariable Integer id) { 
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarPerfil(@PathVariable Integer id, @RequestBody Usuario usuario) { 
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Integer id) { 
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
