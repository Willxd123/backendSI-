package com.examen.controllers;
import com.examen.controllers.dto.AulaDTO;
import com.examen.controllers.dto.MateriaDTO;
import com.examen.entity.Aula;
import com.examen.entity.Materia;
import com.examen.service.IAulaService;
import com.examen.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materia")

public class MateriaController {
    @Autowired
    private IMateriaService materiaService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Materia> materiaOptional = materiaService.findById(id);
        if(materiaOptional.isPresent()){
            Materia materia = materiaOptional.get();
            MateriaDTO materiaDTO = MateriaDTO.builder()
                    .id(materia.getId())
                    .nombre(materia.getNombre())
                    .build();
            return ResponseEntity.ok(materiaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<MateriaDTO> materiaDTOS = materiaService.findAll().
                stream().map(materia -> MateriaDTO.builder()
                        .id(materia.getId())
                        .nombre(materia.getNombre())
                        .build()
                ).toList();
        return ResponseEntity.ok(materiaDTOS);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MateriaDTO materiaDTO) throws URISyntaxException {
        materiaService.save(Materia.builder()
                .id(materiaDTO.getId())
                .nombre(materiaDTO.getNombre())
                .build());
        return ResponseEntity.created(new URI("/api/materia/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MateriaDTO materiaDTO) throws URISyntaxException {
        Optional<Materia> materiaOptional = materiaService.findById(id);
        if(materiaOptional.isPresent()){
            Materia materia = materiaOptional.get();
            materia.setId(materiaDTO.getId());
            materia.setNombre(materiaDTO.getNombre());
            materiaService.save(materia);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && materiaService.findById(id).isPresent()){
            materiaService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
