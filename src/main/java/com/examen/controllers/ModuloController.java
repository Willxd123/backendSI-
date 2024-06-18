package com.examen.controllers;
import com.examen.controllers.dto.AulaDTO;
import com.examen.controllers.dto.FacultadDTO;
import com.examen.controllers.dto.ModuloDTO;
import com.examen.entity.Aula;
import com.examen.entity.Facultad;
import com.examen.entity.Modulo;
import com.examen.service.IModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modulo")

public class ModuloController {
    @Autowired
    private IModuloService moduloService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Modulo> moduloOptional = moduloService.findById(id);
        if(moduloOptional.isPresent()){
            Modulo modulo = moduloOptional.get();
            ModuloDTO moduloDTO = ModuloDTO.builder()
                    .id(modulo.getId())
                    .nombre(modulo.getNombre())
                    .facultad(FacultadDTO.builder()
                            .id(modulo.getFacultad().getId())
                            .nombre(modulo.getFacultad().getNombre())
                            .build()
                    )
                    .build();
            return ResponseEntity.ok(moduloDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<ModuloDTO> moduloDTOS = moduloService.findAll().
                stream().map(modulo -> ModuloDTO.builder()
                        .id(modulo.getId())
                        .nombre(modulo.getNombre())
                        .facultad(FacultadDTO.builder()
                                .id(modulo.getFacultad().getId())
                                .nombre(modulo.getFacultad().getNombre())
                                .build())
                        .build()
                ).toList();
        return ResponseEntity.ok(moduloDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ModuloDTO moduloDTO) throws URISyntaxException {
        moduloService.save(Modulo.builder()
                .id(moduloDTO.getId())
                .nombre(moduloDTO.getNombre())
                .facultad(Facultad.builder()
                        .id(moduloDTO.getFacultad().getId())
                        .nombre(moduloDTO.getFacultad().getNombre())
                        .build()
                )
                .build());
        return ResponseEntity.created(new URI("/api/modulo/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ModuloDTO moduloDTO) throws URISyntaxException {
        Optional<Modulo> moduloOptional = moduloService.findById(id);
        if(moduloOptional.isPresent()){
            Modulo modulo = moduloOptional.get();
            modulo.setId(moduloDTO.getId());
            modulo.setNombre(moduloDTO.getNombre());
            modulo.setFacultad(Facultad.builder()
                    .id(moduloDTO.getFacultad().getId())
                    .nombre(moduloDTO.getFacultad().getNombre())
                    .build());
            moduloService.save(modulo);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && moduloService.findById(id).isPresent()){
            moduloService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
