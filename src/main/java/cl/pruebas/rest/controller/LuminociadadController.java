package cl.pruebas.rest.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.pruebas.rest.model.Luminosidad;
import cl.pruebas.rest.model.Usuario;
import cl.pruebas.rest.service.LuminocidadService;
import cl.pruebas.rest.service.UsuarioService;

@RestController
@RequestMapping("/luminosidad")
public class LuminociadadController {
    
    @Autowired
    private LuminocidadService luminocidadService;

    @Autowired
    private UsuarioService usuarioService ;


    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Luminosidad guardarLuminosidad (@RequestBody Luminosidad luminosidad){
        return luminocidadService.saveLuminosidad(luminosidad);
    }

    @GetMapping
    public List<Luminosidad> listarLuminosidad(){
        return luminocidadService.findAll();
    }

     @GetMapping("/activas")
    public List<Luminosidad> listarLuminosidadActiva(){
        return luminocidadService.findByActivoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Luminosidad> ObtenerLuminosidadById(@PathVariable("id") long idLuminosidad){
        return luminocidadService.getLuminosidadById(idLuminosidad)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    
    }

    @PutMapping("/{id}/{user}")
    public ResponseEntity<Luminosidad> actualizarLuminosidad(@PathVariable("id") long idLuminosidad, @PathVariable("user") long idUser, @RequestBody Luminosidad luminosidad){
        Usuario user = usuarioService.getUsuarioById(idUser).orElse(null);
        return luminocidadService.getLuminosidadById(idLuminosidad)
            .map(luminosidadGuardada -> {
                luminosidadGuardada.setNombre(luminosidad.getNombre());
                luminosidadGuardada.setActivo(luminosidad.getActivo());
                luminosidadGuardada.setDescripcion(luminosidad.getDescripcion());
                luminosidadGuardada.setFechaActualiza(new Date());
                luminosidadGuardada.setUsuarioActualiza(user);

                

                Luminosidad luminosidadActualizada = luminocidadService.updateLuminosiodad(luminosidadGuardada);
                return new ResponseEntity<>(luminosidadActualizada,HttpStatus.OK);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
