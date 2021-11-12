package com.microservicios.app.usuarios.services;

import com.microservicios.app.commons.services.CommonService;
import com.microservicios.app.commons.models.entity.Alumno;

import java.util.List;

public interface AlumnoService extends CommonService<Alumno> {

    public List<Alumno> findByNombreOrApellido(String term);
}
