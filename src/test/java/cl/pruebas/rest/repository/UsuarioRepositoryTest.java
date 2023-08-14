package cl.pruebas.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.pruebas.rest.model.Usuario;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    Usuario usuariox;

    @BeforeEach
    void setup(){
         usuariox = Usuario.builder()
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();
        
    }


    @DisplayName("test guardar usuario")
    @Test
    void guardarUsuario(){
        //given
        Usuario usuario1 = Usuario.builder()
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();
        //when
        Usuario usuarioGuardado = usuarioRepository.save(usuario1);
        //then
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getId()).isGreaterThan(0);
    }

    @Test
    void testFindByUsername() {
        usuarioRepository.save(usuariox);
        Usuario usuarioBd = usuarioRepository.findByUsername(usuariox.getUsername()).orElse(null);
        assertThat(usuarioBd).isNotNull();
        assertEquals("jg", usuarioBd.getUsername());

     }

    @DisplayName("test listar All")
    @Test
    void testListarUsuarios(){
         //given
        Usuario usuario1 = Usuario.builder()
            .nombre("Antorio")
            .apellido("Oviedo")
            .email("B@A.cl")
            .username("AO")
            .build();
        //when
         usuarioRepository.save(usuario1);
         usuarioRepository.save(usuariox);

        List<Usuario> usuarios = usuarioRepository.findAll();
        //then
        assertThat(usuarios).isNotEmpty();
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);
      }
    
    @DisplayName("test obtener usuario por id")
    @Test
    void testObtenerUsuarioById(){
         //given
        usuarioRepository.save(usuariox);

        //when

        Optional<Usuario> usuarioBd = usuarioRepository.findById(usuariox.getId());
        //then
        assertThat(usuarioBd).isNotNull();
      }

    @DisplayName("test actualizar usuario")
    @Test
    void testActualizarUsuario(){
         //given
        usuarioRepository.save(usuariox);

        //when
        Usuario usuarioBd = usuarioRepository.findById(usuariox.getId()).orElse(null);

        usuarioBd.setEmail("asd@asd.cl");
        usuarioBd.setApellido("perez");
        usuarioBd.setNombre("juan");
        Usuario usuarioActualizado = usuarioRepository.save(usuarioBd);
        //then
        assertThat(usuarioActualizado).isNotNull();
        assertEquals("asd@asd.cl", usuarioActualizado.getEmail());
        assertEquals("perez", usuarioActualizado.getApellido());
        assertEquals("juan", usuarioActualizado.getNombre());

      }

      @DisplayName("Eliminar usuario")
      @Test
      void eliminarUsuario(){
        usuarioRepository.save(usuariox);

        //when 
        usuarioRepository.deleteById(usuariox.getId());
        Optional<Usuario> usOptional = usuarioRepository.findById(usuariox.getId());

        //then
        assertThat(usOptional).isEmpty();


      }

    
}
//prueba de integración es la revision de todas las pruebas unitarias implementadas.