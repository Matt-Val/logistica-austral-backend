package com.logistica_austral.la;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistica_austral.la.controller.AuthController; // Importar el controlador explícitamente
import com.logistica_austral.la.dto.Usuario;
import com.logistica_austral.la.dto.LoginRequestDTO;
import com.logistica_austral.la.service.UsuarioService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioService usuarioService;

    private AuthController authController;

    @BeforeEach
    void setup() {
        authController = new AuthController(usuarioService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

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

    @Test
    public void testRegistroConflictCorreo() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreoUsuario("dup@example.com");
        nuevoUsuario.setPasswordUsuario("password123");
        nuevoUsuario.setNombreUsuario("Dup User");
        nuevoUsuario.setRutUsuario("22.222.222-2");
        nuevoUsuario.setTelefonoUsuario("900000000");

        when(usuarioService.registrarUsuario(any(Usuario.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado"));

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
            .andExpect(status().isConflict());
    }

    @Test
    public void testRegistroConflictRut() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreoUsuario("rut@example.com");
        nuevoUsuario.setPasswordUsuario("password123");
        nuevoUsuario.setNombreUsuario("Rut User");
        nuevoUsuario.setRutUsuario("33.333.333-3");
        nuevoUsuario.setTelefonoUsuario("911111111");

        when(usuarioService.registrarUsuario(any(Usuario.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado"));

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
            .andExpect(status().isConflict());
    }

    @Test
    public void testLoginExitoso() throws Exception {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setCorreo("login@example.com");
        login.setPassword("secret");

        Usuario usuarioLogeado = new Usuario();
        usuarioLogeado.setIdUsuario(5);
        usuarioLogeado.setCorreoUsuario("login@example.com");
        usuarioLogeado.setPasswordUsuario("secret");
        usuarioLogeado.setNombreUsuario("Login User");
        usuarioLogeado.setRutUsuario("44.444.444-4");
        usuarioLogeado.setTelefonoUsuario("922222222");
        usuarioLogeado.setEsAdmin(false);

        when(usuarioService.loginUsuario("login@example.com", "secret")).thenReturn(usuarioLogeado);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idUsuario").value(5))
            .andExpect(jsonPath("$.correoUsuario").value("login@example.com"));
    }

    @Test
    public void testLoginCorreoNoExiste() throws Exception {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setCorreo("noexiste@example.com");
        login.setPassword("secret");

        when(usuarioService.loginUsuario("noexiste@example.com", "secret"))
            .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginPasswordIncorrecta() throws Exception {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setCorreo("login@example.com");
        login.setPassword("mala");

        when(usuarioService.loginUsuario("login@example.com", "mala"))
            .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o Contraseña incorrectos"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegistroAdminExitoso() throws Exception {
        Usuario adminNuevo = new Usuario();
        adminNuevo.setCorreoUsuario("admin@example.com");
        adminNuevo.setPasswordUsuario("adminpass");
        adminNuevo.setNombreUsuario("Admin User");
        adminNuevo.setRutUsuario("55.555.555-5");
        adminNuevo.setTelefonoUsuario("933333333");

        Usuario adminGuardado = new Usuario();
        adminGuardado.setIdUsuario(10);
        adminGuardado.setCorreoUsuario("admin@example.com");
        adminGuardado.setPasswordUsuario("adminpass");
        adminGuardado.setNombreUsuario("Admin User");
        adminGuardado.setRutUsuario("55.555.555-5");
        adminGuardado.setTelefonoUsuario("933333333");
        adminGuardado.setEsAdmin(true);

        when(usuarioService.registrarUsuarioAdmin(any(Usuario.class))).thenReturn(adminGuardado);

        mockMvc.perform(post("/api/auth/registro-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminNuevo)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idUsuario").value(10))
            .andExpect(jsonPath("$.esAdmin").value(true));
    }

    @Test
    public void testRegistroAdminConflictCorreo() throws Exception {
        Usuario adminNuevo = new Usuario();
        adminNuevo.setCorreoUsuario("dupadmin@example.com");
        adminNuevo.setPasswordUsuario("adminpass");
        adminNuevo.setNombreUsuario("Admin Dup");
        adminNuevo.setRutUsuario("66.666.666-6");
        adminNuevo.setTelefonoUsuario("944444444");

        when(usuarioService.registrarUsuarioAdmin(any(Usuario.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado"));

        mockMvc.perform(post("/api/auth/registro-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminNuevo)))
            .andExpect(status().isConflict());
    }

    @Test
    public void testRegistroAdminConflictRut() throws Exception {
        Usuario adminNuevo = new Usuario();
        adminNuevo.setCorreoUsuario("rutadmin@example.com");
        adminNuevo.setPasswordUsuario("adminpass");
        adminNuevo.setNombreUsuario("Admin Rut");
        adminNuevo.setRutUsuario("77.777.777-7");
        adminNuevo.setTelefonoUsuario("955555555");

        when(usuarioService.registrarUsuarioAdmin(any(Usuario.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "El RUT ya está registrado"));

        mockMvc.perform(post("/api/auth/registro-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminNuevo)))
            .andExpect(status().isConflict());
    }
}