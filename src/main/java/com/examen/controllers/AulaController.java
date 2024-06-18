package com.examen.controllers;
import com.examen.controllers.dto.AulaDTO;
import com.examen.controllers.dto.FacultadDTO;
import com.examen.controllers.dto.ModuloDTO;
import com.examen.entity.Aula;
import com.examen.entity.Facultad;
import com.examen.entity.Modulo;
import com.examen.service.IAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/aula")

public class AulaController {
    @Autowired
    private IAulaService aulaService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Aula> aulaOptional = aulaService.findById(id);
        if(aulaOptional.isPresent()){
            Aula aula = aulaOptional.get();
            AulaDTO aulaDTO = AulaDTO.builder()
                    .id(aula.getId())
                    .numero(aula.getNumero())
                    .modulo(ModuloDTO.builder()
                            .id(aula.getModulo().getId())
                            .nombre(aula.getModulo().getNombre())
                            .build()
                    )
                    .build();
            return ResponseEntity.ok(aulaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<AulaDTO> aulaDTOS = aulaService.findAll().
                stream().map(aula -> AulaDTO.builder()
                        .id(aula.getId())
                        .numero(aula.getNumero())
                        .modulo(ModuloDTO.builder()
                                .id(aula.getModulo().getId())
                                .nombre(aula.getModulo().getNombre())
                                .build())
                        .build()
                ).toList();
        return ResponseEntity.ok(aulaDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AulaDTO aulaDTO) throws URISyntaxException {
        aulaService.save(Aula.builder()
                .id(aulaDTO.getId())
                .numero(aulaDTO.getNumero())
                .modulo(Modulo.builder()
                        .id(aulaDTO.getModulo().getId())
                        .nombre(aulaDTO.getModulo().getNombre())
                        .build()
                )
                .build());
        return ResponseEntity.created(new URI("/api/aula/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AulaDTO aulaDTO) throws URISyntaxException {
        Optional<Aula> aulaOptional = aulaService.findById(id);
        if(aulaOptional.isPresent()){
            Aula aula = aulaOptional.get();
            aula.setId(aulaDTO.getId());
            aula.setNumero(aulaDTO.getNumero());
            aula.setModulo(Modulo.builder()
                    .id(aulaDTO.getModulo().getId())
                    .nombre(aulaDTO.getModulo().getNombre())
                    .build()
            );
            aulaService.save(aula);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && aulaService.findById(id).isPresent()){
            aulaService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
