package com.microservicios.app.cursos.controllers;

import com.microservicios.app.commons.controllers.CommonController;
import com.microservicios.app.commons.models.entity.Alumno;
import com.microservicios.app.commons.models.entity.Examen;
import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.app.cursos.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Curso> o = service.findById(id);
        if(!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = o.get();
        cursoDb.setNombre(curso.getNombre());

        return ResponseEntity.ok().body(service.save(cursoDb));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        if(!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = o.get();

        alumnos.forEach(a -> cursoDb.addAlumno(a));

        return ResponseEntity.ok().body(service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        if(!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = o.get();
        cursoDb.removeAlumno(alumno);

        return ResponseEntity.ok().body(service.save(cursoDb));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
        Curso curso = service.findCursoByAlumnoId(id);

        if(curso != null) {
            List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);

            List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                if(examenesIds.contains(examen.getId())) {
                    examen.setRespondido(true);
                }
                return examen;
            }).collect(Collectors.toList());

            curso.setExamenes(examenes);
        }
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        if(!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = o.get();

        examenes.forEach(e -> cursoDb.addExamen(e));

        return ResponseEntity.ok().body(service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        if(!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = o.get();
        cursoDb.removeExamen(examen);

        return ResponseEntity.ok().body(service.save(cursoDb));
    }
}
