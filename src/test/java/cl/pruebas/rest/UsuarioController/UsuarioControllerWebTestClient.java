package cl.pruebas.rest.UsuarioController;

import static org.springframework.boot.test.context.SpringBootTest.*;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import cl.pruebas.rest.model.Usuario;
import java.util.List;

import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerWebTestClient {
    
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testGuardarUsuario(){
        //given
       Usuario usuario = Usuario.builder()
            .id(1L)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();

            //when
            webTestClient.post().uri("http://localhost:8080/usuarios/guardar")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(usuario)
            .exchange()

            //then
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.nombre").isEqualTo(usuario.getNombre())
            .jsonPath("$.apellido").isEqualTo(usuario.getApellido())
            .jsonPath("$.email").isEqualTo(usuario.getEmail())
            .jsonPath("$.username").isEqualTo(usuario.getUsername());
            
    }
    @Test
    @Order(2)
    void testBuscarUsuario(){
        webTestClient.get().uri("http://localhost:8080/usuarios/1").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.nombre").isEqualTo("Javier")
        .jsonPath("$.apellido").isEqualTo("Garrido")
        .jsonPath("$.email").isEqualTo("A@A.cl")
        .jsonPath("$.username").isEqualTo("jg");
        
    }

    @Test
    @Order(3)
    void testListarUsuarios(){
        webTestClient.get().uri("http://localhost:8080/usuarios").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$[0].nombre").isEqualTo("Javier")
        .jsonPath("$[0].apellido").isEqualTo("Garrido")
        .jsonPath("$[0].email").isEqualTo("A@A.cl")
        .jsonPath("$[0].username").isEqualTo("jg")
        .jsonPath("$").isArray()
        .jsonPath("$").value(hasSize(1));
    }

    @Test
    @Order(4)
    void testObtenerListadoUsuarios(){
        webTestClient.get().uri("http://localhost:8080/usuarios").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Usuario.class)
        .consumeWith(response ->{
            List<Usuario> usuarios = response.getResponseBody();
            Assertions.assertEquals(1, usuarios.size());
            Assertions.assertNotNull(usuarios);
        });
    }

    @Test
    @Order(5)
    void testActualizarUsuario(){
        Usuario usuarioActualizado = Usuario.builder()
        .nombre("juan")
        .apellido("Gomez")
        .email("B@B.cl")
        .build();

        webTestClient.put().uri("http://localhost:8080/usuarios/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(usuarioActualizado)
        .exchange()
        //then
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON);

    }

    @Test
    @Order(6)
    void testEliminarUsuario(){
        webTestClient.get().uri("http://localhost:8080/usuarios").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Usuario.class)
        .hasSize(1);

        webTestClient.delete().uri("http://localhost:8080/usuarios/1").exchange()
        .expectStatus().isOk();

         webTestClient.get().uri("http://localhost:8080/usuarios").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Usuario.class)
        .hasSize(0);

        webTestClient.get().uri("http://localhost:8080/usuarios/1").exchange()
        .expectStatus().is4xxClientError();
    }
}
