package com.examen.controllers;
import com.examen.controllers.dto.*;
import com.examen.entity.Aula;
import com.examen.entity.Grupo;
import com.examen.entity.Horario;
import com.examen.entity.Materia;
import com.examen.service.IAulaService;
import com.examen.service.IHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/horario")

public class HorarioController {
    @Autowired
    private IHorarioService horarioService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Horario> horarioOptional = horarioService.findById(id);
        if(horarioOptional.isPresent()){
            Horario horario = horarioOptional.get();
            HorarioDTO horarioDTO = HorarioDTO.builder()
                    .id(horario.getId())
                    .dia(horario.getDia())
                    .horaInicio(horario.getHoraInicio())
                    .horaFin(horario.getHoraFin())
                    .materia(MateriaDTO.builder()
                            .id(horario.getMateria().getId())
                            .nombre(horario.getMateria().getNombre())
                            .build()
                    )
                    .grupo(GrupoDTO.builder()
                            .id(horario.getGrupo().getId())
                            .sigla(horario.getGrupo().getSigla())
                            .build()
                    )
                    .aula(AulaDTO.builder()
                            .id(horario.getAula().getId())
                            .numero(horario.getAula().getNumero())
                            .modulo(ModuloDTO.builder()
                                    .id(horario.getAula().getModulo().getId())
                                    .nombre(horario.getAula().getModulo().getNombre())
                                    .build()
                            )
                            .build()
                    )
                    .build();
            return ResponseEntity.ok(horarioDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<HorarioDTO> horarioDTOS = horarioService.findAll().
                stream().map(horario -> HorarioDTO.builder()
                        .id(horario.getId())
                        .dia(horario.getDia())
                        .horaInicio(horario.getHoraInicio())
                        .horaFin(horario.getHoraFin())
                        .materia(MateriaDTO.builder()
                                .id(horario.getMateria().getId())
                                .nombre(horario.getMateria().getNombre())
                                .build())
                        .grupo(GrupoDTO.builder()
                                .id(horario.getGrupo().getId())
                                .sigla(horario.getGrupo().getSigla())
                                .build())
                        .aula(AulaDTO.builder()
                                .id(horario.getAula().getId())
                                .numero(horario.getAula().getNumero())
                                .modulo(ModuloDTO.builder()
                                        .id(horario.getAula().getModulo().getId())
                                        .nombre(horario.getAula().getModulo().getNombre())
                                        .build()
                                )
                                .build())
                        .build()
                ).toList();
        return ResponseEntity.ok(horarioDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody HorarioDTO horarioDTO) throws URISyntaxException {
        horarioService.save(Horario.builder()
                .id(horarioDTO.getId())
                .dia(horarioDTO.getDia())
                .horaInicio(horarioDTO.getHoraInicio())
                .horaFin(horarioDTO.getHoraFin())
                .materia(Materia.builder()
                        .id(horarioDTO.getMateria().getId())
                        .nombre(horarioDTO.getMateria().getNombre())
                        .build()
                )
                .grupo(Grupo.builder()
                        .id(horarioDTO.getGrupo().getId())
                        .sigla(horarioDTO.getGrupo().getSigla())
                        .build()
                )
                .aula(Aula.builder()
                        .id(horarioDTO.getAula().getId())
                        .numero(horarioDTO.getAula().getNumero())
                        .build()
                )
                .build());
        return ResponseEntity.created(new URI("/api/horario/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HorarioDTO horarioDTO) throws URISyntaxException {
        Optional<Horario> horarioOptional = horarioService.findById(id);
        if(horarioOptional.isPresent()){
            Horario horario = horarioOptional.get();
            horario.setId(horarioDTO.getId());
            horario.setDia(horarioDTO.getDia());
            horario.setHoraInicio(horarioDTO.getHoraInicio());
            horario.setHoraFin(horarioDTO.getHoraFin());
            horario.setMateria(Materia.builder()
                    .id(horarioDTO.getMateria().getId())
                    .nombre(horarioDTO.getMateria().getNombre())
                    .build());
            horario.setGrupo(Grupo.builder()
                    .id(horarioDTO.getGrupo().getId())
                    .sigla(horarioDTO.getGrupo().getSigla())
                    .build());
            horario.setAula(Aula.builder()
                    .id(horarioDTO.getAula().getId())
                    .numero(horarioDTO.getAula().getNumero())
                    .build());
            horarioService.save(horario);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && horarioService.findById(id).isPresent()){
            horarioService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
