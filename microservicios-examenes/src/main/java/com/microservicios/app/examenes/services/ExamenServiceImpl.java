package com.microservicios.app.examenes.services;

import com.microservicios.app.commons.models.entity.Asignatura;
import com.microservicios.app.commons.services.CommonServiceImpl;
import com.microservicios.app.commons.models.entity.Examen;
import com.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.microservicios.app.examenes.models.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return repository.findByNombre(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Asignatura> findAllAsignaturas() {
        return asignaturaRepository.findAll();
    }

}
