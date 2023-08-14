package cl.pruebas.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pruebas.rest.model.Luminosidad;

@Repository
public interface LuminocidadRepository extends JpaRepository<Luminosidad, Long>{

    List<Luminosidad> findAll();
    List<Luminosidad> findByActivoTrue();

    
}
