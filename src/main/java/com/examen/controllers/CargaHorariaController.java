package com.examen.controllers;

import com.examen.controllers.dto.*;
import com.examen.entity.*;
import com.examen.service.ICargaHorariaService;
import com.examen.service.IHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cargahoraria")

public class CargaHorariaController {
    @Autowired
    private ICargaHorariaService cargaHorariaService;

    @Autowired
    private IHorarioService horarioService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<CargaHoraria> cargaHorariaOptional = cargaHorariaService.findById(id);
        if(cargaHorariaOptional.isPresent()){
            CargaHoraria cargaHoraria = cargaHorariaOptional.get();
            CargaHorariaDTO cargaHorariaDTO = CargaHorariaDTO.builder()
                    .id(cargaHoraria.getId())
                    .fecha(cargaHoraria.getFecha())
                    .grupo(GrupoDTO.builder()
                            .id(cargaHoraria.getGrupo().getId())
                            .sigla(cargaHoraria.getGrupo().getSigla())
                            .build()
                    )
                    .materia(MateriaDTO.builder()
                            .id(cargaHoraria.getMateria().getId())
                            .nombre(cargaHoraria.getMateria().getNombre())
                            .build())
                    .gestion(GestionDTO.builder()
                            .id(cargaHoraria.getGestion().getId())
                            .nombre(cargaHoraria.getGestion().getNombre())
                            .build())
                    .carrera(CarreraDTO.builder()
                            .id(cargaHoraria.getCarrera().getId())
                            .nombre(cargaHoraria.getCarrera().getNombre())
                            .build())
                    .docente(DocenteDTO.builder()
                            .id(cargaHoraria.getDocente().getId())
                            .apellidoP(cargaHoraria.getDocente().getApellidoP())
                            .apellidoM(cargaHoraria.getDocente().getApellidoM())
                            .nombre(cargaHoraria.getDocente().getNombre())
                            .build())
                    .administrador(AdministradorDTO.builder()
                            .id(cargaHoraria.getAdministrador().getId())
                            .build())
                    .build();
            return ResponseEntity.ok(cargaHorariaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<CargaHorariaDTO> cargaHorariaDTOS = cargaHorariaService.findAll().
                stream().map(cargaHoraria -> CargaHorariaDTO.builder()
                        .id(cargaHoraria.getId())
                        .fecha(cargaHoraria.getFecha())
                        .grupo(GrupoDTO.builder()
                                .id(cargaHoraria.getGrupo().getId())
                                .sigla(cargaHoraria.getGrupo().getSigla())
                                .build()
                        )
                        .materia(MateriaDTO.builder()
                                .id(cargaHoraria.getMateria().getId())
                                .nombre(cargaHoraria.getMateria().getNombre())
                                .build())
                        .gestion(GestionDTO.builder()
                                .id(cargaHoraria.getGestion().getId())
                                .nombre(cargaHoraria.getGestion().getNombre())
                                .build())
                        .carrera(CarreraDTO.builder()
                                .id(cargaHoraria.getCarrera().getId())
                                .nombre(cargaHoraria.getCarrera().getNombre())
                                .build())
                        .docente(DocenteDTO.builder()
                                .id(cargaHoraria.getDocente().getId())
                                .apellidoP(cargaHoraria.getDocente().getApellidoP())
                                .apellidoM(cargaHoraria.getDocente().getApellidoM())
                                .nombre(cargaHoraria.getDocente().getNombre())
                                .build())
                        .administrador(AdministradorDTO.builder()
                                .id(cargaHoraria.getAdministrador().getId())
                                .nombre(cargaHoraria.getAdministrador().getNombre())
                                .build())
                        .horarios(horarioService.findByMateriaAndGrupo(cargaHoraria.getMateria(), cargaHoraria.getGrupo())
                                .stream().map(horario-> HorarioMateriaGrupoDTO.builder()
                                        .id(horario.getId())
                                        .aula(horario.getAula().getNumero())
                                        .dia(horario.getDia())
                                        .horaInicio(horario.getHoraInicio())
                                        .horaFin(horario.getHoraFin())
                                        .build()).toList())
                        .build()


                ).toList();
        return ResponseEntity.ok(cargaHorariaDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CargaHorariaDTO cargaHorariaDTO) throws URISyntaxException {
        cargaHorariaService.save(CargaHoraria.builder()
                .id(cargaHorariaDTO.getId())
                .fecha(cargaHorariaDTO.getFecha())
                .grupo(Grupo.builder()
                        .id(cargaHorariaDTO.getGrupo().getId())
                        .sigla(cargaHorariaDTO.getGrupo().getSigla())
                        .build()
                )
                .materia(Materia.builder()
                        .id(cargaHorariaDTO.getMateria().getId())
                        .nombre(cargaHorariaDTO.getMateria().getNombre())
                        .build())
                .gestion(Gestion.builder()
                        .id(cargaHorariaDTO.getGestion().getId())
                        .nombre(cargaHorariaDTO.getGestion().getNombre())
                        .build())
                .carrera(Carrera.builder()
                        .id(cargaHorariaDTO.getCarrera().getId())
                        .nombre(cargaHorariaDTO.getCarrera().getNombre())
                        .build())
                .docente(Docente.builder()
                        .id(cargaHorariaDTO.getDocente().getId())
                        .apellidoP(cargaHorariaDTO.getDocente().getApellidoP())
                        .apellidoM(cargaHorariaDTO.getDocente().getApellidoM())
                        .nombre(cargaHorariaDTO.getDocente().getNombre())
                        .build())
                .administrador(Administrador.builder()
                        .id(cargaHorariaDTO.getAdministrador().getId())
                        .build())
                .build());
        return ResponseEntity.created(new URI("/api/cargahoraria/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CargaHorariaDTO cargaHorariaDTO) throws URISyntaxException {
        Optional<CargaHoraria> cargaHorariaOptional = cargaHorariaService.findById(id);
        if(cargaHorariaOptional.isPresent()){
            CargaHoraria cargaHoraria = cargaHorariaOptional.get();
            cargaHoraria.setId(id);
            cargaHoraria.setGrupo(Grupo.builder()
                    .id(cargaHorariaDTO.getGrupo().getId())
                    .sigla(cargaHorariaDTO.getGrupo().getSigla())
                    .build());
            cargaHoraria.setAdministrador(Administrador.builder()
                    .id(cargaHorariaDTO.getAdministrador().getId())
                    .build());
            cargaHoraria.setFecha(cargaHorariaDTO.getFecha());
            cargaHoraria.setMateria(Materia.builder()
                    .id(cargaHorariaDTO.getMateria().getId())
                    .nombre(cargaHorariaDTO.getMateria().getNombre())
                    .build());
            cargaHoraria.setGestion(Gestion.builder()
                    .id(cargaHorariaDTO.getGestion().getId())
                    .nombre(cargaHorariaDTO.getGestion().getNombre())
                    .build());
            cargaHoraria.setCarrera(Carrera.builder()
                    .id(cargaHorariaDTO.getCarrera().getId())
                    .nombre(cargaHorariaDTO.getCarrera().getNombre())
                    .build());
            cargaHoraria.setDocente(Docente.builder()
                    .id(cargaHorariaDTO.getDocente().getId())
                    .apellidoP(cargaHorariaDTO.getDocente().getApellidoP())
                    .apellidoM(cargaHorariaDTO.getDocente().getApellidoM())
                    .nombre(cargaHorariaDTO.getDocente().getNombre())
                    .build());
            cargaHorariaService.save(cargaHoraria);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && cargaHorariaService.findById(id).isPresent()){
            cargaHorariaService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
