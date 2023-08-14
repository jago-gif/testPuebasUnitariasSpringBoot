package cl.pruebas.rest.service;


import java.util.List;
import java.util.Optional;


import cl.pruebas.rest.model.Usuario;

public interface UsuarioService {
    
    Usuario saveUsuario(Usuario usuario);

    List<Usuario> getAllUsuarios();

    Optional<Usuario> getUsuarioById(long id);

    Usuario updateUsuario(Usuario usuarioActualizado);

    void deleteUsuario(long id);
}
