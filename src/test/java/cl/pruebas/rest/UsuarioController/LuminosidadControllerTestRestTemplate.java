package cl.pruebas.rest.UsuarioController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cl.pruebas.rest.model.Luminosidad;
import cl.pruebas.rest.model.Usuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LuminosidadControllerTestRestTemplate {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("test guardar usuario")
    @Test
    @Order(1)
    void testGuardarLuminosidad(){
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

        Luminosidad luminosidad = Luminosidad.builder()
            .id(1L)
            .nombre("Luz")
            .activo(true)
            .descripcion("condicion 1")
            .fechaActualiza(new Date())
            .build();

        ResponseEntity<Luminosidad> response1 = testRestTemplate.postForEntity("http://localhost:8080/luminosidad/guardar", luminosidad, Luminosidad.class);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response1.getHeaders().getContentType());

        Luminosidad luminosidadCreado = response1.getBody();

       
        assertNotNull(luminosidadCreado);    
        assertEquals(1L, luminosidadCreado.getId());
        assertEquals("Luz", luminosidadCreado.getNombre());
        assertEquals("condicion 1", luminosidadCreado.getDescripcion());
        
    }

    @Test
    @Order(2)
    void testListarLuminosidades(){
        ResponseEntity<Luminosidad[]> response = testRestTemplate.getForEntity("http://localhost:8080/luminosidad", Luminosidad[].class);

        List<Luminosidad> luminosidades = Arrays.asList(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(1,luminosidades.size());
        assertEquals(1L,luminosidades.get(0).getId());
        assertEquals("Luz",luminosidades.get(0).getNombre());
    }

    @Test
    @Order(3)
    void testBuscarPorId(){
        ResponseEntity<Luminosidad> response = testRestTemplate.getForEntity("http://localhost:8080/luminosidad/1", Luminosidad.class);
        Luminosidad luminosidadBd = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, luminosidadBd.getId());
        assertEquals("Luz", luminosidadBd.getNombre());
        }

    @Test
    @Order(4)
    void testGuardarLuminosidadInactiva(){
    
        Luminosidad luminosidad = Luminosidad.builder()
            .id(2L)
            .nombre("Nubado")
            .activo(false)
            .descripcion("condicion 2")
            .fechaActualiza(new Date())
            .build();

        ResponseEntity<Luminosidad> response = testRestTemplate.postForEntity("http://localhost:8080/luminosidad/guardar", luminosidad, Luminosidad.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        Luminosidad luminosidadCreado = response.getBody();

       
        assertNotNull(luminosidadCreado);    
        assertEquals(2L, luminosidadCreado.getId());
        assertEquals("Nubado", luminosidadCreado.getNombre());
        assertEquals("condicion 2", luminosidadCreado.getDescripcion());
        
    }
    @Test
    @Order(5)
    void testListarActivos(){
        
        ResponseEntity<Luminosidad[]> response = testRestTemplate.getForEntity("http://localhost:8080/luminosidad/activas", Luminosidad[].class);

        List<Luminosidad> luminosidades = Arrays.asList(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(1,luminosidades.size());
        assertEquals(1L,luminosidades.get(0).getId());
        assertEquals("Luz",luminosidades.get(0).getNombre());
    }
}
