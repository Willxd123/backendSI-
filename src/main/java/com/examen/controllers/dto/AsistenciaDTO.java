package com.examen.controllers.dto;

import com.examen.entity.Horario;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data

public class AsistenciaDTO {
    private Long id;
    private char estado;
    private Date fecha;
    private TimeZone hora;
    private String observacion;

    private HorarioDTO horario;
}
