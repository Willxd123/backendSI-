package com.examen.controllers;
import com.examen.controllers.dto.GrupoDTO;
import com.examen.entity.Grupo;
import com.examen.service.IGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grupo")

public class GrupoController {
    @Autowired
    private IGrupoService grupoService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Grupo> grupoOptional = grupoService.findById(id);
        if(grupoOptional.isPresent()){
            Grupo grupo = grupoOptional.get();
            GrupoDTO grupoDTO = GrupoDTO.builder()
                    .id(grupo.getId())
                    .sigla(grupo.getSigla())
                    .build();
            return ResponseEntity.ok(grupoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<GrupoDTO> grupoDTOS = grupoService.findAll().
                stream().map(grupo -> GrupoDTO.builder()
                        .id(grupo.getId())
                        .sigla(grupo.getSigla())
                        .build()
                ).toList();
        return ResponseEntity.ok(grupoDTOS);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody GrupoDTO grupoDTO) throws URISyntaxException {
        grupoService.save(Grupo.builder()
                .id(grupoDTO.getId())
                .sigla(grupoDTO.getSigla())
                .build());
        return ResponseEntity.created(new URI("/api/grupo/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GrupoDTO grupoDTO) throws URISyntaxException {
        Optional<Grupo> grupoOptional = grupoService.findById(id);
        if(grupoOptional.isPresent()){
            Grupo grupo = grupoOptional.get();
            grupo.setSigla(grupoDTO.getSigla());
            grupo.setId(grupoDTO.getId());
            grupoService.save(grupo);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && grupoService.findById(id).isPresent()){
            grupoService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
