package com.examen.controllers;

import com.examen.controllers.dto.CarreraDTO;
import com.examen.controllers.dto.CarreraMateriaDTO;
import com.examen.controllers.dto.MateriaDTO;
import com.examen.entity.Carrera;
import com.examen.entity.CarreraMateria;
import com.examen.entity.Materia;
import com.examen.service.ICarreraMateriaService;
import com.examen.service.ICarreraService;
import com.examen.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carreramateria")

public class CarreraMateriaController {
    @Autowired
    private ICarreraMateriaService carreraMateriaService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<CarreraMateria> carreraMateriaOptional = carreraMateriaService.findById(id);
        if(carreraMateriaOptional.isPresent()){
            CarreraMateria carreraMateria = carreraMateriaOptional.get();
            CarreraMateriaDTO carreraMateriaDTO = CarreraMateriaDTO.builder()
                    .id(carreraMateria.getId())
                    .materia(MateriaDTO.builder()
                            .id(carreraMateria.getMateria().getId())
                            .nombre(carreraMateria.getMateria().getNombre())
                            .build()
                    )
                    .carrera(CarreraDTO.builder()
                            .id(carreraMateria.getCarrera().getId())
                            .nombre(carreraMateria.getCarrera().getNombre())
                            .build()
                    )
                    .build();
            return ResponseEntity.ok(carreraMateriaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<CarreraMateriaDTO> carreraMateriaDTOS = carreraMateriaService.findAll().
                stream().map(carreraMateria -> CarreraMateriaDTO.builder()
                        .id(carreraMateria.getId())
                        .materia(MateriaDTO.builder()
                                .id(carreraMateria.getMateria().getId())
                                .nombre(carreraMateria.getMateria().getNombre())
                                .build()
                        )
                        .carrera(CarreraDTO.builder()
                                .id(carreraMateria.getCarrera().getId())
                                .nombre(carreraMateria.getCarrera().getNombre())
                                .build())
                        .build()
                ).toList();
        return ResponseEntity.ok(carreraMateriaDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CarreraMateriaDTO carreraMateriaDTO) throws URISyntaxException {
        carreraMateriaService.save(CarreraMateria.builder()
                .id(carreraMateriaDTO.getId())
                .materia(Materia.builder()
                        .id(carreraMateriaDTO.getMateria().getId())
                        .nombre(carreraMateriaDTO.getMateria().getNombre())
                        .build()
                )
                .carrera(Carrera.builder()
                        .id(carreraMateriaDTO.getCarrera().getId())
                        .nombre(carreraMateriaDTO.getCarrera().getNombre())
                        .build()
                )
                .build());
        return ResponseEntity.created(new URI("/api/carreramateria/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CarreraMateriaDTO carreraMateriaDTO) throws URISyntaxException {
        Optional<CarreraMateria> carreraMateriaOptional = carreraMateriaService.findById(id);
        if(carreraMateriaOptional.isPresent()){
            CarreraMateria carreraMateria = carreraMateriaOptional.get();
            carreraMateria.setId(carreraMateriaDTO.getId());
            carreraMateria.setMateria(Materia.builder()
                    .id(carreraMateriaDTO.getMateria().getId())
                    .nombre(carreraMateriaDTO.getMateria().getNombre())
                    .build());
            carreraMateria.setCarrera(Carrera.builder()
                    .id(carreraMateriaDTO.getCarrera().getId())
                    .nombre(carreraMateriaDTO.getCarrera().getNombre())
                    .build());
            carreraMateriaService.save(carreraMateria);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && carreraMateriaService.findById(id).isPresent()){
            carreraMateriaService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

}
