package cl.pruebas.rest.UsuarioController;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import cl.pruebas.rest.model.Usuario;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTestRestTemplate {
    
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("test guardar usuario")
    @Test
    @Order(1)
    void testGuardarUsuario(){
        long id = 1L;
       Usuario usuario = Usuario.builder()
            .id(id)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();

        ResponseEntity<Usuario> response = testRestTemplate.postForEntity("http://localhost:8080/usuarios/guardar", usuario, Usuario.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        Usuario usuarioCreado = response.getBody();

        assertNotNull(usuarioCreado);    
        assertEquals(1L, usuarioCreado.getId());
        assertEquals("Javier", usuarioCreado.getNombre());
        assertEquals("Garrido", usuarioCreado.getApellido());
        assertEquals("A@A.cl", usuarioCreado.getEmail());
    }

    @Test
    @Order(2)
    void testListarUsuarios(){
        ResponseEntity<Usuario[]> response = testRestTemplate.getForEntity("http://localhost:8080/usuarios", Usuario[].class);

        List<Usuario> usuarios = Arrays.asList(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(1,usuarios.size());
        assertEquals(1L,usuarios.get(0).getId());
        assertEquals("Javier",usuarios.get(0).getNombre());
    

    }

    @Test
    @Order(3)
    void testBuscarUsuario(){
        ResponseEntity<Usuario> response = testRestTemplate.getForEntity("http://localhost:8080/usuarios/1", Usuario.class);
        Usuario usuario = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario.getId(),1L);
        assertEquals(usuario.getNombre(),"Javier");
    }

    @Test
    @Order(4)
    void testEliminarUsuario(){
        ResponseEntity<Usuario[]> response = testRestTemplate.getForEntity("http://localhost:8080/usuarios", Usuario[].class);
        List<Usuario> usuarios = Arrays.asList(response.getBody());
        assertEquals(1,usuarios.size());

        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("id", 1L);
        ResponseEntity<Void> exchange = testRestTemplate.exchange("http://localhost:8080/usuarios/{id}", HttpMethod.DELETE, null, Void.class, pathVariables);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertFalse(exchange.hasBody());
        response = testRestTemplate.getForEntity("http://localhost:8080/usuarios", Usuario[].class);
        usuarios = Arrays.asList(response.getBody());
        assertEquals(0,usuarios.size());

        ResponseEntity<Usuario> response2 = testRestTemplate.getForEntity("http://localhost:8080/usuarios/1", Usuario.class);
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertFalse(response2.hasBody());
    }
}
