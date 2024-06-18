package com.examen.controllers;
import com.examen.controllers.dto.AulaDTO;
import com.examen.controllers.dto.DocenteDTO;
import com.examen.controllers.dto.UsuarioDTO;
import com.examen.entity.Administrador;
import com.examen.entity.Aula;
import com.examen.entity.Docente;
import com.examen.entity.Usuario;
import com.examen.service.IAulaService;
import com.examen.service.IDocenteService;
import com.examen.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/docente")

public class DocenteController {
    @Autowired
    private IDocenteService docenteService;
    private IUsuarioService usuarioService;
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Docente> docenteOptional = docenteService.findById(id);
        if(docenteOptional.isPresent()){
            Docente docente = docenteOptional.get();
            DocenteDTO docenteDTO = DocenteDTO.builder()
                    .id(docente.getId())
                    .nombre(docente.getNombre())
                    .apellidoP(docente.getApellidoP())
                    .apellidoM(docente.getApellidoM())
                    .usuario(UsuarioDTO.builder()
                            .id(docente.getUsuario().getId())
                            .username(docente.getUsuario().getUsername())
                            .email(docente.getUsuario().getEmail())
                            .build()
                    )
                    .build();
            return ResponseEntity.ok(docenteDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<DocenteDTO> docenteDTOS = docenteService.findAll().
                stream().map(docente -> DocenteDTO.builder()
                        .id(docente.getId())
                        .nombre(docente.getNombre())
                        .apellidoP(docente.getApellidoP())
                        .apellidoM(docente.getApellidoM())
                        .usuario(UsuarioDTO.builder()
                                .id(docente.getUsuario().getId())
                                .username(docente.getUsuario().getUsername())
                                .email(docente.getUsuario().getEmail())
                                .build()
                        )
                        .build()
                ).toList();
        return ResponseEntity.ok(docenteDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody DocenteDTO docenteDTO) throws URISyntaxException {
        docenteService.save(Docente.builder()
                .id(docenteDTO.getId())
                .nombre(docenteDTO.getNombre())
                .apellidoP(docenteDTO.getApellidoP())
                .apellidoM(docenteDTO.getApellidoM())
                .usuario(Usuario.builder()
                        .id(docenteDTO.getUsuario().getId())
                        .username(docenteDTO.getUsuario().getUsername())
                        .build()
                )
                .build());
        return ResponseEntity.created(new URI("/api/docente/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DocenteDTO docenteDTO) throws URISyntaxException {
        Optional<Docente> docenteOptional = docenteService.findById(id);
        if(docenteOptional.isPresent()){
            Docente docente = docenteOptional.get();
            docente.setNombre(docenteDTO.getNombre());
            docente.setId(id);
            docente.setApellidoP(docenteDTO.getApellidoP());
            docente.setApellidoM(docenteDTO.getApellidoM());
            docente.setUsuario(Usuario.builder()
                    .id(docenteDTO.getUsuario().getId())
                    .username(docenteDTO.getUsuario().getUsername())
                    .build());
            docenteService.save(docente);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && docenteService.findById(id).isPresent()){
            docenteService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
