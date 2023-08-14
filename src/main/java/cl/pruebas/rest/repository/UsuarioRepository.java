package cl.pruebas.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.pruebas.rest.model.Usuario;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Optional<Usuario> findByUsername(String username);
}
