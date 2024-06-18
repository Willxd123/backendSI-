package com.examen.controllers.dto;

import com.examen.entity.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
public class CargaHorariaDTO {
    private Long id;
    private Date fecha;
    private DocenteDTO docente;
    private GestionDTO gestion;
    private AdministradorDTO administrador;
    private GrupoDTO grupo;
    private MateriaDTO materia;
    private CarreraDTO carrera;
    private List<HorarioMateriaGrupoDTO> horarios;
}
