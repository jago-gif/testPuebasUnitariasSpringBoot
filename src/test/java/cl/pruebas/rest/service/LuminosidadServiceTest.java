package cl.pruebas.rest.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;

import cl.pruebas.rest.impl.LuminocidadServiceImpl;
import cl.pruebas.rest.impl.UsuarioServiceImpl;
import cl.pruebas.rest.model.Luminosidad;
import cl.pruebas.rest.model.Usuario;
import cl.pruebas.rest.repository.LuminocidadRepository;
import cl.pruebas.rest.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class LuminosidadServiceTest {
    
     @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private LuminocidadRepository luminosidadRepository;

    @InjectMocks
    private LuminocidadServiceImpl luminosidadService;

    Usuario usuariox;

    Luminosidad luminosidadx;


    @BeforeEach
    void setup(){
         usuariox = Usuario.builder()
            .id(1L)
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

    @DisplayName("Test para guardar luminosidad")
    @Test
    void testSaveUsuario() {
        //given
        given(luminosidadRepository.save(luminosidadx)).willReturn(luminosidadx);
        //when
        Luminosidad luminosidadGuardado = luminosidadService.saveLuminosidad(luminosidadx);
        //then
        assertThat(luminosidadGuardado).isNotNull();
    }

    @DisplayName("test listar todas las luminosidades")
    @Test
    void testGetAllLuminosidades() {
        //given
        Luminosidad luminosidad1 = Luminosidad.builder()
            .id(2L)
            .nombre("luminosidad 2")
            .descripcion("luminosidad 2")
            .activo(false)
            .usuarioActualiza(usuariox)
            .build();

        given(luminosidadRepository.findAll()).willReturn(List.of(luminosidad1, luminosidadx));

        //then 
        List<Luminosidad> luminosidades = luminosidadService.findAll();

        //then
        assertThat(luminosidades).isNotNull();
        assertThat(luminosidades.size()).isEqualTo(2);
    }

     @DisplayName("test listar luminosidades activas")
    @Test
    void testGetLuminosidadesActivas() {
        //given
        Luminosidad luminosidad1 = Luminosidad.builder()
            .id(2L)
            .nombre("luminosidad 2")
            .descripcion("luminosidad 2")
            .activo(false)
            .usuarioActualiza(usuariox)
            .build();

        given(luminosidadRepository.findByActivoTrue()).willReturn(Collections.singletonList(luminosidadx));

        //then 
        List<Luminosidad> luminosidades = luminosidadService.findByActivoTrue();

        //then
        System.out.println(luminosidades);
        assertThat(luminosidades).isNotNull();
        assertThat(luminosidades.size()).isEqualTo(1);
    }

    @DisplayName("test listar luminosidad vacia")
    @Test
    void testGetLuminosidadesVacio() {
        //given
        
        given(luminosidadRepository.findAll()).willReturn(Collections.emptyList());

        //then 
        List<Luminosidad> luminosidades = luminosidadService.findAll();

        //then
        assertThat(luminosidades).isEmpty();
        assertThat(luminosidades.size()).isEqualTo(0);
    }

    @DisplayName("Test para obtener luminosidad por id")
    @Test
    void testGetLuminosidadById() {
        //given
        given(luminosidadRepository.findById(1L)).willReturn(Optional.of(luminosidadx));

        // When
        Luminosidad luminosidadId = luminosidadService.getLuminosidadById(1L).orElse(null);
    
    
        //then
        assertThat(luminosidadId).isNotNull();
        assertThat(luminosidadId.getId()).isEqualTo(luminosidadx.getId());
        assertThat(luminosidadId.getNombre()).isEqualTo(luminosidadx.getNombre());
    }

    @DisplayName("test para actualizar luminosidad")
    @Test
    void testUpdateLuminosidad() {
        //given
        given(luminosidadRepository.save(luminosidadx)).willReturn(luminosidadx); 
        //when
        luminosidadx.setNombre("luminosidad nueva");
        Luminosidad luminosidadGuardado = luminosidadService.saveLuminosidad(luminosidadx);
        //then

        assertThat(luminosidadGuardado).isNotNull();
        assertThat(luminosidadGuardado.getNombre()).isEqualTo("luminosidad nueva");
    

    }

}
