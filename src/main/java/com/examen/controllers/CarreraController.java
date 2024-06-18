package com.examen.controllers;

import com.examen.controllers.dto.AulaDTO;
import com.examen.controllers.dto.CarreraDTO;
import com.examen.controllers.dto.FacultadDTO;
import com.examen.entity.Aula;
import com.examen.entity.Carrera;
import com.examen.entity.Facultad;
import com.examen.service.IAulaService;
import com.examen.service.ICarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carrera")

public class CarreraController {
    @Autowired
    private ICarreraService carreraService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Carrera> carreraOptional = carreraService.findById(id);
        if(carreraOptional.isPresent()){
            Carrera carrera = carreraOptional.get();
            CarreraDTO carreraDTO = CarreraDTO.builder()
                    .id(carrera.getId())
                    .nombre(carrera.getNombre())
                    .facultad(FacultadDTO.builder()
                            .id(carrera.getFacultad().getId())
                            .nombre(carrera.getFacultad().getNombre())
                            .build()
                    )
                    .build();
            return ResponseEntity.ok(carreraDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<CarreraDTO> carreraDTOS = carreraService.findAll().
                stream().map(carrera -> CarreraDTO.builder()
                        .id(carrera.getId())
                        .nombre(carrera.getNombre())
                        .facultad(FacultadDTO.builder()
                                .id(carrera.getFacultad().getId())
                                .nombre(carrera.getFacultad().getNombre())
                                .build()
                        )
                        .build()
                ).toList();
        return ResponseEntity.ok(carreraDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CarreraDTO carreraDTO) throws URISyntaxException {
        carreraService.save(Carrera.builder()
                .id(carreraDTO.getId())
                .nombre(carreraDTO.getNombre())
                .facultad(Facultad.builder()
                        .id(carreraDTO.getFacultad().getId())
                        .nombre(carreraDTO.getFacultad().getNombre())
                        .build()
                )
                .build());
        return ResponseEntity.created(new URI("/api/carrera/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CarreraDTO carreraDTO) throws URISyntaxException {
        Optional<Carrera> carreraOptional = carreraService.findById(id);
        if(carreraOptional.isPresent()){
            Carrera carrera = carreraOptional.get();
            carrera.setId(carreraDTO.getId());
            carrera.setNombre(carreraDTO.getNombre());
            carrera.setFacultad(Facultad.builder()
                    .id(carreraDTO.getFacultad().getId())
                    .nombre(carreraDTO.getFacultad().getNombre())
                    .build());
            carreraService.save(carrera);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && carreraService.findById(id).isPresent()){
            carreraService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

}
