package cl.pruebas.rest.service;

import java.util.List;
import java.util.Optional;

import cl.pruebas.rest.model.Luminosidad;

public interface LuminocidadService {

    Luminosidad saveLuminosidad (Luminosidad luminosiodad);

    List<Luminosidad> findByActivoTrue();

    List<Luminosidad> findAll();

    Optional<Luminosidad> getLuminosidadById(long id);

    Luminosidad updateLuminosiodad (Luminosidad luminosidadActualizada);

}
