package cl.pruebas.rest.UsuarioController;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
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

import cl.pruebas.rest.impl.LuminocidadServiceImpl;
import cl.pruebas.rest.impl.UsuarioServiceImpl;
import cl.pruebas.rest.model.Luminosidad;
import cl.pruebas.rest.model.Usuario;

@WebMvcTest
public class LuminosidadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServiceImpl usuarioService;
    @MockBean
    private LuminocidadServiceImpl luminocidadService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("test guardar Luminosidad")
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

        Luminosidad luminosidad = Luminosidad.builder()
            .id(1L)
            .nombre("Luz")
            .activo(true)
            .descripcion("condicion 1")
            .fechaActualiza(new Date())
            .usuarioActualiza(usuario)
            .build();
    given(luminocidadService.saveLuminosidad(any(Luminosidad.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
    
    // When

    ResultActions response = mockMvc.perform(post("/luminosidad/guardar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(luminosidad)));
    // Then
    response.andDo(print())
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.nombre", is(luminosidad.getNombre())))
    .andExpect(jsonPath("$.descripcion", is(luminosidad.getDescripcion())));
    }

    @DisplayName("test obtener luminosidad  por id")
    @Test
    void testObtenerUsuarioById() throws Exception {
       //given
       long id=1L;
       Usuario usuario = Usuario.builder()
            .id(1L)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();
    
    given(usuarioService.saveUsuario(any(Usuario.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));

        Luminosidad luminosidad = Luminosidad.builder()
            .id(1L)
            .nombre("Luz")
            .activo(true)
            .descripcion("condicion 1")
            .fechaActualiza(new Date())
            .usuarioActualiza(usuario)
            .build();
    given(luminocidadService.saveLuminosidad(any(Luminosidad.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
       //then
       ResultActions response = mockMvc.perform(get("/luminosidad/{id}", id)
       .contentType(MediaType.APPLICATION_JSON));
       //when 
       response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is(luminosidad.getNombre())));

    }
}
