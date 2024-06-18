package com.examen.service;

import com.examen.entity.Grupo;
import com.examen.entity.Horario;
import com.examen.entity.Materia;

import java.util.List;
import java.util.Optional;

public interface IHorarioService {

    List<Horario> findAll();
    List<Horario> findByMateriaAndGrupo(Materia materia, Grupo grupo);

    Optional<Horario> findById(Long id);

    void save(Horario horario);

    void deleteById(Long id);
}

