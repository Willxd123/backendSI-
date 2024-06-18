package com.examen.persistence.implement;

import com.examen.entity.Aula;
import com.examen.entity.Grupo;
import com.examen.entity.Horario;
import com.examen.entity.Materia;
import com.examen.persistence.IAulaDAO;
import com.examen.persistence.IHorarioDAO;
import com.examen.repository.AulaRepository;
import com.examen.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HorarioDAOImplement implements IHorarioDAO {

    @Autowired
    private HorarioRepository horarioRepository;


    @Override
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    @Override
    public List<Horario> findByMateriaAndGrupo(Materia materia, Grupo grupo) {
        return horarioRepository.findByMateriaAndGrupo(materia, grupo);
    }

    @Override
    public Optional<Horario> findById(Long id) {
        return horarioRepository.findById(id);
    }

    @Override
    public void save(Horario horario) {
        horarioRepository.save(horario);
    }

    @Override
    public void deleteById(Long id) {
        horarioRepository.deleteById(id);
    }
}
