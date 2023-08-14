package cl.pruebas.rest.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.pruebas.rest.exception.ResourceNotFoundException;
import cl.pruebas.rest.model.Usuario;
import cl.pruebas.rest.repository.UsuarioRepository;
import cl.pruebas.rest.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        Optional<Usuario> usuarioGuardado = usuarioRepository.findByUsername(usuario.getUsername());
        if(usuarioGuardado.isPresent()){
            throw new ResourceNotFoundException("El username ya se encuentra en uso " + usuario.getUsername());
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario updateUsuario(Usuario usuarioActualizado) {
        return usuarioRepository.save(usuarioActualizado);
    }

    @Override
    public void deleteUsuario(long id) {
         usuarioRepository.deleteById(id);
    }
    
}
