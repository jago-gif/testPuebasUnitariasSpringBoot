package cl.pruebas.rest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import cl.pruebas.rest.exception.ResourceNotFoundException;
import cl.pruebas.rest.impl.UsuarioServiceImpl;
import cl.pruebas.rest.model.Usuario;
import cl.pruebas.rest.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    Usuario usuariox;

    @BeforeEach
    void setup(){
         usuariox = Usuario.builder()
            .id(1L)
            .nombre("Javier")
            .apellido("Garrido")
            .email("A@A.cl")
            .username("jg")
            .build();
        
    }

    @DisplayName("test para eliminar usuario")
    @Test
    void testDeleteUsuario() {
        // Given
        long usuarioid = 1L;    
        // When
        usuarioService.deleteUsuario(usuarioid);
    
        // Then
        verify(usuarioRepository, times(1)).deleteById(usuarioid);
    }

    @DisplayName("test listar empleados")
    @Test
    void testGetAllUsuarios() {
        //given
        Usuario usuario1 = Usuario.builder()
        .nombre("Javier")
        .apellido("Garrido")
        .email("A@A.cl")
        .username("jg")
        .build();

        given(usuarioRepository.findAll()).willReturn(List.of(usuario1, usuariox));

        //then 
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        //then
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);
    }

    @DisplayName("test listar empleados vacia")
    @Test
    void testGetAllUsuariosVacia() {
        //given
        
        given(usuarioRepository.findAll()).willReturn(Collections.emptyList());

        //then 
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        //then
        assertThat(usuarios).isEmpty();
        assertThat(usuarios.size()).isEqualTo(0);
    }

    @DisplayName("Test para obtener un usuario por id")
    @Test
    void testGetUsuarioById() {
        //given
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuariox));

        // When
        Usuario usuarioGuardado = usuarioService.getUsuarioById(1L).orElse(null);
    
    
        //then
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getId()).isEqualTo(usuariox.getId());
        assertThat(usuarioGuardado.getUsername()).isEqualTo(usuariox.getUsername());
    }

    @DisplayName("Test para guardar un usuario")
    @Test
    void testSaveUsuario() {
        //given
            given(usuarioRepository.findByUsername(usuariox.getUsername()))
                .willReturn(Optional.empty());
                given(usuarioRepository.save(usuariox)).willReturn(usuariox);
        //when
        Usuario usuarioGuardado = usuarioService.saveUsuario(usuariox);
        //then

        assertThat(usuarioGuardado).isNotNull();
    }

    @DisplayName("Test para guardar un usuario con throw exception")
    @Test
    void testSaveUsuarioConThrow() {
        //given
            given(usuarioRepository.findByUsername(usuariox.getUsername()))
                .willReturn(Optional.of(usuariox));
        //when
        assertThrows(ResourceNotFoundException.class, () ->{
            usuarioService.saveUsuario(usuariox);
        });
        //then

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }   
    @DisplayName("test para actualizar un usuario")
    @Test
    void testUpdateUsuario() {
        //given
        given(usuarioRepository.save(usuariox)).willReturn(usuariox);
        //when
        usuariox.setApellido("perez");
        Usuario usuarioGuardado = usuarioService.updateUsuario(usuariox);
        //then

        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getApellido()).isEqualTo("perez");
    

    }
}
