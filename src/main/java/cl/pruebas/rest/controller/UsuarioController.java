package cl.pruebas.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.pruebas.rest.model.Usuario;
import cl.pruebas.rest.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario guardarUsuario (@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }
     
    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioService.getAllUsuarios();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> ObtenerUsuarioById(@PathVariable("id") long usuarioId){
        return usuarioService.getUsuarioById(usuarioId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") long usuarioId, @RequestBody Usuario usuario){
        return usuarioService.getUsuarioById(usuarioId)
            .map(usuarioGuardado -> {
                usuarioGuardado.setNombre(usuario.getNombre());
                usuarioGuardado.setApellido(usuario.getApellido());
                usuarioGuardado.setEmail(usuario.getEmail());

                Usuario usuarioActualizado = usuarioService.updateUsuario(usuarioGuardado);
                return new ResponseEntity<>(usuarioActualizado,HttpStatus.OK);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> emiliarUsuario(@PathVariable("id") long usuarioId){
        usuarioService.deleteUsuario(usuarioId);
        return new ResponseEntity<>("Usuario Eliminado con exito", HttpStatus.OK);
    }
}
