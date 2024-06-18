package com.examen.controllers;
import com.examen.controllers.dto.GestionDTO;
import com.examen.entity.Gestion;
import com.examen.service.IGestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gestion")

public class GestionController {
    @Autowired
    private IGestionService gestionService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Gestion> gestionOptional = gestionService.findById(id);
        if(gestionOptional.isPresent()){
            Gestion gestion = gestionOptional.get();
            GestionDTO gestionDTO = GestionDTO.builder()
                    .id(gestion.getId())
                    .nombre(gestion.getNombre())
                    .fechaFin(gestion.getFechaFin())
                    .fechaInicio(gestion.getFechaInicio())
                    .build();
            return ResponseEntity.ok(gestionDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<GestionDTO> gestionDTOS = gestionService.findAll().
                stream().map(gestion -> GestionDTO.builder()
                        .id(gestion.getId())
                        .nombre(gestion.getNombre())
                        .fechaFin(gestion.getFechaFin())
                        .fechaInicio(gestion.getFechaInicio())
                        .build()
                ).toList();
        return ResponseEntity.ok(gestionDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody GestionDTO gestionDTO) throws URISyntaxException {
        gestionService.save(Gestion.builder()
                .id(gestionDTO.getId())
                .nombre(gestionDTO.getNombre())
                .fechaFin(gestionDTO.getFechaFin())
                .fechaInicio(gestionDTO.getFechaInicio())
                .build());
        return ResponseEntity.created(new URI("/api/gestion/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GestionDTO gestionDTO) throws URISyntaxException {
        Optional<Gestion> gestionOptional = gestionService.findById(id);
        if(gestionOptional.isPresent()){
            Gestion gestion = gestionOptional.get();
            gestion.setNombre(gestionDTO.getNombre());
            gestion.setFechaFin(gestionDTO.getFechaFin());
            gestion.setFechaInicio(gestionDTO.getFechaInicio());
            gestion.setId(id);
            gestionService.save(gestion);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && gestionService.findById(id).isPresent()){
            gestionService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
