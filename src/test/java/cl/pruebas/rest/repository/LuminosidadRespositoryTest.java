package cl.pruebas.rest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import cl.pruebas.rest.model.Luminosidad;
import cl.pruebas.rest.model.Usuario;
@DataJpaTest
public class LuminosidadRespositoryTest {
     @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LuminocidadRepository luminocidadRepository;

    Usuario usuariox;
    Luminosidad luminosidadx;

    @BeforeEach
    void setup(){
         usuariox = Usuario.builder()
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();

             luminosidadx = Luminosidad.builder()
            .id(1L)
            .nombre("luminosidad inicial")
            .descripcion("luminosidad inicial")
            .activo(true)
            .usuarioActualiza(usuariox)
            .build();
        
    }

    @DisplayName("test guardar Luminosidad")
    @Test
    void guardarLuminosidad(){
        //given
        Luminosidad luminosidad1 = Luminosidad.builder()
            .id(1L)
            .nombre("luminosidad luminosidad1")
            .descripcion("luminosidad luminosidad1")
            .activo(true)
            .usuarioActualiza(usuariox)
            .build();
        //when
        Luminosidad luminosidadGuardado = luminocidadRepository.save(luminosidad1);
        //then
        assertThat(luminosidadGuardado).isNotNull();
        assertThat(luminosidadGuardado.getId()).isGreaterThan(0);
    }
    
    @DisplayName("test listar All")
    @Test
    void testListarLuminosidades(){
         //given
        Usuario usuarioGuardado = usuarioRepository.save(usuariox);

        Luminosidad luminosidad1 = Luminosidad.builder()
            .id(1L)
            .nombre("luminosidad luminosidad1")
            .descripcion("luminosidad luminosidad1")
            .activo(true)
            .usuarioActualiza(usuarioGuardado)
            .build();

        //when

         luminocidadRepository.save(luminosidad1);

        List<Luminosidad> luminosidades = luminocidadRepository.findAll();
        //then
        assertThat(luminosidades).isNotEmpty();
        assertThat(luminosidades).isNotNull();
        assertThat(luminosidades.size()).isEqualTo(1);
      }

    @DisplayName("test obtener luminosidad por id")
    @Test
    void testObtenerUsuarioById(){
        //given
        usuarioRepository.save(usuariox);
        luminocidadRepository.save(luminosidadx);

        //when

        Optional<Luminosidad> luminosidadBd = luminocidadRepository.findById(luminosidadx.getId());
        //then
        assertThat(luminosidadBd).isNotNull();
    }

    @DisplayName("test actualizar Luminosidad")
    @Test
    void testActualizarUsuario(){
    //given
    usuarioRepository.save(usuariox);
    luminocidadRepository.save(luminosidadx);


    //when
    Luminosidad luminosidadBd = luminocidadRepository.findById(luminosidadx.getId()).orElse(null);

    luminosidadBd.setNombre("luminosidad actualizada");
    luminosidadBd.setDescripcion("luminosidad actualizada");
    luminosidadBd.setActivo(false);
    
    Luminosidad luminosidadActualizado = luminocidadRepository.save(luminosidadBd);
    //then
    assertThat(luminosidadActualizado).isNotNull();
    assertEquals(luminosidadActualizado.getNombre(), "luminosidad actualizada");
    assertEquals(luminosidadActualizado.getDescripcion(), "luminosidad actualizada");
    assertEquals(luminosidadActualizado.getActivo(), false);

    }

    @DisplayName("test listar activos")
    @Test
    void testListarLuminosidadesActivas(){
         //given
         usuarioRepository.save(usuariox);
        luminocidadRepository.save(luminosidadx);


        Luminosidad luminosidad1 = Luminosidad.builder()
            .id(1L)
            .nombre("luminosidad luminosidad1")
            .descripcion("luminosidad luminosidad1")
            .activo(true)
            .usuarioActualiza(usuariox)
            .build();

        //when

         luminocidadRepository.save(luminosidad1);

        List<Luminosidad> luminosidades = luminocidadRepository.findByActivoTrue();
        //then
        assertThat(luminosidades).isNotEmpty();
        assertThat(luminosidades).isNotNull();
        assertThat(luminosidades.size()).isEqualTo(1);
      }
  
}
