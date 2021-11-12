package com.microservicios.app.examenes.controllers;

import com.microservicios.app.commons.controllers.CommonController;
import com.microservicios.app.commons.models.entity.Examen;
import com.microservicios.app.examenes.services.ExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Examen> o = service.findById(id);
        if(!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Examen examenDb = o.get();
        examenDb.setNombre(examen.getNombre());

        /*List<Pregunta> eliminadas = new ArrayList<>();
        examenDb.getPreguntas().forEach(pdb -> {
            if(!examen.getPreguntas().contains(pdb)) {
                eliminadas.add(pdb);
            }
        });*/
        //Eliminamos las preguntas que estan en la BD pero no en la lista pasada como param.
        examenDb.getPreguntas()
                .stream()
                .filter(pdb -> !examen.getPreguntas().contains(pdb))
                .forEach(examenDb::removePreguntas);

        //Agregamos las demas
        examenDb.setPreguntas(examen.getPreguntas());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(service.findByNombre(term));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas() {
        return ResponseEntity.ok(service.findAllAsignaturas());
    }
}
