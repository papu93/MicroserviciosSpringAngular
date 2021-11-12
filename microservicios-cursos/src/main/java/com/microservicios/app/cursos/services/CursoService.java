package com.microservicios.app.cursos.services;

import com.microservicios.app.commons.services.CommonService;
import com.microservicios.app.cursos.models.entity.Curso;
import org.springframework.web.bind.annotation.PathVariable;

public interface CursoService extends CommonService<Curso> {

    public Curso findCursoByAlumnoId(Long id);

    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);
}
