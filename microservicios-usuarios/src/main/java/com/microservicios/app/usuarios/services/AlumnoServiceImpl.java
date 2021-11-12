package com.microservicios.app.usuarios.services;

import com.microservicios.app.commons.services.CommonServiceImpl;
import com.microservicios.app.commons.models.entity.Alumno;
import com.microservicios.app.usuarios.models.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return repository.findByNombreOrApellido(term);
    }
}
