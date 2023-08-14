package cl.pruebas.rest.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.pruebas.rest.exception.ResourceNotFoundException;
import cl.pruebas.rest.model.Luminosidad;
import cl.pruebas.rest.repository.LuminocidadRepository;
import cl.pruebas.rest.service.LuminocidadService;

@Service
public class LuminocidadServiceImpl implements LuminocidadService{

    @Autowired
    LuminocidadRepository luminocidadRepository;

    @Override
    public List<Luminosidad> findAll() {
        return luminocidadRepository.findAll();
    }

    @Override
    public List<Luminosidad> findByActivoTrue() {
        return luminocidadRepository.findByActivoTrue();
    }

    @Override
    public Optional<Luminosidad> getLuminosidadById(long id) {
        return luminocidadRepository.findById(id);
    }

    @Override
    public Luminosidad saveLuminosidad(Luminosidad luminosiodad) {
        Optional<Luminosidad> luminosidadGuardada = luminocidadRepository.findById(luminosiodad.getId());
        if(luminosidadGuardada.isPresent()){
            throw new ResourceNotFoundException("La luminosidad ya existe");
        }
        return luminocidadRepository.save(luminosiodad);
    }

    @Override
    public Luminosidad updateLuminosiodad(Luminosidad luminosidadActualizada) {
        Optional<Luminosidad> luminosidadGuardada = luminocidadRepository.findById(luminosidadActualizada.getId());
        if(!luminosidadGuardada.isPresent()){
            throw new ResourceNotFoundException("La luminosidad no existe");
        }
        return luminocidadRepository.save(luminosidadActualizada);
    }
    
    

}
