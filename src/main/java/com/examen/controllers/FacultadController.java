package com.examen.controllers;
import com.examen.controllers.dto.AulaDTO;
import com.examen.controllers.dto.FacultadDTO;
import com.examen.entity.Aula;
import com.examen.entity.Facultad;
import com.examen.service.IAulaService;
import com.examen.service.IFacultadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facultad")

public class FacultadController {
    @Autowired
    private IFacultadService facultadService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Facultad> facultadOptional = facultadService.findById(id);
        if(facultadOptional.isPresent()){
            Facultad facultad = facultadOptional.get();
            FacultadDTO facultadDTO = FacultadDTO.builder()
                    .id(facultad.getId())
                    .nombre(facultad.getNombre())
                    .build();
            return ResponseEntity.ok(facultadDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<FacultadDTO> facultadDTOS = facultadService.findAll().
                stream().map(facultad -> FacultadDTO.builder()
                        .id(facultad.getId())
                        .nombre(facultad.getNombre())
                        .build()
                ).toList();
        return ResponseEntity.ok(facultadDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody FacultadDTO facultadDTO) throws URISyntaxException {
        facultadService.save(Facultad.builder()
                .id(facultadDTO.getId())
                .nombre(facultadDTO.getNombre())
                .build());
        return ResponseEntity.created(new URI("/api/facultad/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FacultadDTO facultadDTO) throws URISyntaxException {
        Optional<Facultad> facultadOptional = facultadService.findById(id);
        if(facultadOptional.isPresent()){
            Facultad facultad = facultadOptional.get();
            facultad.setNombre(facultadDTO.getNombre());
            facultad.setId(id);
            facultadService.save(facultad);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && facultadService.findById(id).isPresent()){
            facultadService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
