package com.examen.controllers;

import com.examen.controllers.dto.AsistenciaDTO;
import com.examen.controllers.dto.HorarioDTO;
import com.examen.entity.Asistencia;
import com.examen.entity.Horario;
import com.examen.service.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import java.util.Optional;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api/asistencia")


public class AsistenciaController {
    @Autowired
    private IAsistenciaService asistenciaService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Asistencia> asistenciaOptional = asistenciaService.findById(id);
        if (asistenciaOptional.isPresent()) {
            Asistencia asistencia = asistenciaOptional.get();
            AsistenciaDTO asistenciaDTO = AsistenciaDTO.builder()
                    .id(asistencia.getId())
                    .fecha(asistencia.getFecha())
                    .hora(asistencia.getHora())
                    .estado(asistencia.getEstado())
                    .observacion(asistencia.getObservacion())
                    .horario(HorarioDTO.builder()
                            .id(asistencia.getHorario().getId())
                            .dia(asistencia.getHorario().getDia())
                            .horaInicio(asistencia.getHorario().getHoraInicio())
                            .horaFin(asistencia.getHorario().getHoraFin())
                            .build()
                    )
                    .build();
            return ResponseEntity.ok(asistenciaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<AsistenciaDTO> asistenciaDTOS = asistenciaService.findAll().
                stream().map(asistencia -> AsistenciaDTO.builder()
                        .id(asistencia.getId())
                        .fecha(asistencia.getFecha())
                        .hora(asistencia.getHora())
                        .estado(asistencia.getEstado())
                        .observacion(asistencia.getObservacion())
                        .horario(HorarioDTO.builder()
                                .id(asistencia.getHorario().getId())
                                .dia(asistencia.getHorario().getDia())
                                .horaInicio(asistencia.getHorario().getHoraInicio())
                                .horaFin(asistencia.getHorario().getHoraFin())
                                .build())
                        .build()
                ).toList();
        return ResponseEntity.ok(asistenciaDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AsistenciaDTO asistenciaDTO) throws URISyntaxException {
        Asistencia asistencia = Asistencia.builder()
                .estado(asistenciaDTO.getEstado())
                .fecha(asistenciaDTO.getFecha())
                .hora(asistenciaDTO.getHora())
                .observacion(asistenciaDTO.getObservacion())
                .horario(Horario.builder()
                        .id(asistenciaDTO.getHorario().getId())
                        .dia(asistenciaDTO.getHorario().getDia())
                        .horaInicio(asistenciaDTO.getHorario().getHoraInicio())
                        .horaFin(asistenciaDTO.getHorario().getHoraFin())
                        .build()
                )
                .build();
        asistenciaService.save(asistencia);
        return ResponseEntity.created(new URI("/api/asistencia/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AsistenciaDTO asistenciaDTO) throws URISyntaxException {
        Optional<Asistencia> asistenciaOptional = asistenciaService.findById(id);
        if(asistenciaOptional.isPresent()){
            Asistencia asistencia = asistenciaOptional.get();
            asistencia.setFecha(asistenciaDTO.getFecha());
            asistencia.setHora(asistenciaDTO.getHora());
            asistencia.setEstado(asistenciaDTO.getEstado());
            asistencia.setObservacion(asistenciaDTO.getObservacion());
            asistencia.setHorario(Horario.builder()
                    .id(asistenciaDTO.getHorario().getId())
                    .dia(asistenciaDTO.getHorario().getDia())
                    .horaInicio(asistenciaDTO.getHorario().getHoraInicio())
                    .horaFin(asistenciaDTO.getHorario().getHoraFin())
                    .build());
            asistencia.setId(id);
            asistenciaService.save(asistencia);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && asistenciaService.findById(id).isPresent()){
            asistenciaService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

}
