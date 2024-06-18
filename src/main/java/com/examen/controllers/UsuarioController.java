package com.examen.controllers;
import com.examen.controllers.dto.AulaDTO;
import com.examen.controllers.dto.UsuarioDTO;
import com.examen.entity.Aula;
import com.examen.entity.ERole;
import com.examen.entity.RoleEntity;
import com.examen.entity.Usuario;
import com.examen.service.IAulaService;
import com.examen.service.IUsuarioService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
@RestController
@RequestMapping("/api/usuario")

public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            Set<String> roles = usuario.getRoles().stream()
                    .map(roleEntity -> roleEntity.getName().name())
                    .collect(Collectors.toSet());
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .id(usuario.getId())
                    .email(usuario.getEmail())
                    .username(usuario.getUsername())
                    .roles(roles)
                    .build();
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){

        List<UsuarioDTO> usuarioDTOS = usuarioService.findAll().
                stream().map(usuario -> UsuarioDTO.builder()
                        .id(usuario.getId())
                        .email(usuario.getEmail())
                        .username(usuario.getUsername())
                        .roles(usuario.getRoles().stream()
                                .map(roleEntity -> roleEntity.getName().name())
                                .collect(Collectors.toSet()))
                        .build()
                ).toList();
        return ResponseEntity.ok(usuarioDTOS);
    }
/*
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        usuarioService.save(Usuario.builder()
                .id(usuarioDTO.getId())
                .email(usuarioDTO.getEmail())
                .password(usuarioDTO.getPassword())
                .build());
        return ResponseEntity.created(new URI("/api/usuario/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setId(usuarioDTO.getId());
            usuario.setEmail(usuario.getEmail());
            usuario.setPassword(usuario.getPassword());
            usuarioService.save(usuario);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }
*/
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setId(usuarioDTO.getId());
            usuario.setEmail(usuario.getEmail());
            usuario.setPassword(usuario.getPassword());
            usuario.setRoles(usuario.getRoles());
            usuarioService.save(usuario);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (id != null && usuarioService.findById(id).isPresent()){
            usuarioService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UsuarioDTO usuarioDTO){
        Set<RoleEntity> roles = usuarioDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build()
                ).collect(Collectors.toSet());
        Usuario usuario = Usuario.builder()
                .username(usuarioDTO.getUsername())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                .email(usuarioDTO.getEmail())
                .roles(roles)
                .build();
        usuarioService.save(usuario);
        return ResponseEntity.ok(usuario);
    }
}
