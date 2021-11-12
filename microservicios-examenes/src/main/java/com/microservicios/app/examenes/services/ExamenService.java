package com.microservicios.app.examenes.services;

import com.microservicios.app.commons.models.entity.Asignatura;
import com.microservicios.app.commons.services.CommonService;
import com.microservicios.app.commons.models.entity.Examen;

import java.util.List;

public interface ExamenService extends CommonService<Examen> {

    public List<Examen> findByNombre(String term);

    public Iterable<Asignatura> findAllAsignaturas();
}
