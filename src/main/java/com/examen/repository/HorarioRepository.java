package com.examen.repository;

import com.examen.entity.Grupo;
import com.examen.entity.Horario;
import com.examen.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByMateriaAndGrupo(Materia materia, Grupo grupo);
}
