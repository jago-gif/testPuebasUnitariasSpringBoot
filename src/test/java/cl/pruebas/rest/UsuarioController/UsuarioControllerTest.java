package cl.pruebas.rest.UsuarioController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.pruebas.rest.impl.UsuarioServiceImpl;
import cl.pruebas.rest.model.Usuario;

@WebMvcTest
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("test obtener usuario por id")
    @Test
    void testObtenerUsuarioById() throws Exception {
       //given
       long id = 1L;
       Usuario usuario = Usuario.builder()
            .id(id)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();
            given(usuarioService.getUsuarioById(id)).willReturn(Optional.of(usuario));
       //then
       ResultActions response = mockMvc.perform(get("/usuarios/{id}", id)
       .contentType(MediaType.APPLICATION_JSON));
       //when 
       response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is(usuario.getNombre())));

    }

     @DisplayName("test obtener usuario por id no encontrado")
    @Test
    void testObtenerUsuarioByIdNoEncontrado() throws Exception {
       //given
       long id = 1L;
       Usuario usuario = Usuario.builder()
            .id(id)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();
            given(usuarioService.getUsuarioById(id)).willReturn(Optional.empty());
       //then
       ResultActions response = mockMvc.perform(get("/usuarios/{id}", id)
       .contentType(MediaType.APPLICATION_JSON));
       //when 
       response.andExpect(status().isNotFound())
       .andDo(print());
                

    }
    @DisplayName("actualizar un usuario")
    @Test
    void testActualizarUsuario() throws Exception{
        //givin
         long id = 1L;
       Usuario usuarioGuardado = Usuario.builder()
            .id(id)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();

             Usuario usuarioActualizado = Usuario.builder()
            .id(id)
            .nombre("Juan")
            .apellido("Perez")
            .email("B@A.cl")
            .username("jg")
            .build();

            given(usuarioService.getUsuarioById(id)).willReturn(Optional.of(usuarioGuardado));
            given(usuarioService.updateUsuario(any(Usuario.class)))
            .willAnswer(invocation -> invocation.getArgument(0));

        //then
            ResultActions response = mockMvc.perform(put("/usuarios/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioActualizado)));
        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is(usuarioActualizado.getNombre())))
                .andExpect(jsonPath("$.apellido", is(usuarioActualizado.getApellido())))
                .andExpect(jsonPath("$.email", is(usuarioActualizado.getEmail())));
        
    }

    @DisplayName("actualizar un usuario no encontrado")
    @Test
    void testActualizarUsuarioNulo() throws Exception{
        //givin
         long id = 1L;
    

             Usuario usuarioActualizado = Usuario.builder()
            .id(id)
            .nombre("Juan")
            .apellido("Perez")
            .email("B@A.cl")
            .username("jg")
            .build();

            given(usuarioService.getUsuarioById(id)).willReturn((Optional.empty()));
            given(usuarioService.updateUsuario(any(Usuario.class)))
            .willAnswer(invocation -> invocation.getArgument(0));

        //then
            ResultActions response = mockMvc.perform(put("/usuarios/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioActualizado)));
        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
                
        
    }
    @DisplayName("Eliminar un usuario")
    @Test
    void testEmiliarUsuario() throws Exception{
        //given
        long id = 1L;
        doNothing().when(usuarioService).deleteUsuario(id);
            //then
            ResultActions response = mockMvc.perform(delete("/usuarios/{id}", id)
            .contentType(MediaType.APPLICATION_JSON));
            //then
            response.andExpect(status().isOk())
                    .andDo(print());
    }

    @DisplayName("test guardar usuario")
    @Test
    void testGuardarUsuario() throws Exception {
       Usuario usuario = Usuario.builder()
            .id(1L)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();
    
    given(usuarioService.saveUsuario(any(Usuario.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
    
    // When

    ResultActions response = mockMvc.perform(post("/usuarios/guardar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)));
    // Then
    response.andDo(print())
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.nombre", is(usuario.getNombre())))
    .andExpect(jsonPath("$.apellido", is(usuario.getApellido())))
    .andExpect(jsonPath("$.email", is(usuario.getEmail())))
    .andExpect(jsonPath("$.username", is(usuario.getUsername())));

    }
    @DisplayName("listar usuarios")
    @Test
    void testListarUsuarios() throws Exception{
        //given
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(Usuario.builder().nombre("juan").apellido("perez").email("a@a.cl").username("j.p").build());
        usuarios.add(Usuario.builder().nombre("Jorge").apellido("Garrido").email("b@b.cl").username("j.g").build());
        usuarios.add(Usuario.builder().nombre("Juan").apellido("Torres").email("c@c.cl").username("j.t").build());
        usuarios.add(Usuario.builder().nombre("Hector").apellido("Soto").email("d@d.cl").username("H.S").build());
        given(usuarioService.getAllUsuarios()).willReturn(usuarios);

        //when
        ResultActions response = mockMvc.perform(get("/usuarios"));
        //then

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(usuarios.size())));

    }
    
}
