package com.logistica_austral.la;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistica_austral.la.dto.Usuario;
import com.logistica_austral.la.service.UsuarioService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean; // Importante importar esto
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  

    @MockBean 
    private UsuarioService usuarioService;

    @Test
    public void testRegistroExitoso() throws Exception { 
        // 1. Datos de entrada.
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreoUsuario("test@example.com");
        nuevoUsuario.setPasswordUsuario("password123");
        nuevoUsuario.setNombreUsuario("Test User");
        nuevoUsuario.setRutUsuario("11.111.111-1");
        nuevoUsuario.setTelefonoUsuario("912345678");

        // 1a. Datos de salida (simulados).
        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setIdUsuario(1);
        usuarioGuardado.setCorreoUsuario("test@example.com");
        usuarioGuardado.setEsAdmin(false);
        usuarioGuardado.setNombreUsuario("Test User");
        usuarioGuardado.setRutUsuario("11.111.111-1");
        usuarioGuardado.setTelefonoUsuario("912345678");
        usuarioGuardado.setPasswordUsuario("password123");

        // 2. Configuración del mock
        // Ahora sí funcionará porque usuarioService es un MockBean y no null
        when(usuarioService.registrarUsuario(any(Usuario.class))).thenReturn(usuarioGuardado);

        // 3. Ejecución
        mockMvc.perform(post("/api/auth/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevoUsuario)))

            // Verificación de la respuesta
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idUsuario").value(1))
            .andExpect(jsonPath("$.correoUsuario").value("test@example.com"));
    }
}