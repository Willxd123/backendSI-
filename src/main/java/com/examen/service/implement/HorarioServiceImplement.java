package com.examen.service.implement;

import com.examen.entity.Grupo;
import com.examen.entity.Horario;
import com.examen.entity.Materia;
import com.examen.persistence.IHorarioDAO;
import com.examen.repository.HorarioRepository;
import com.examen.service.IHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioServiceImplement implements IHorarioService {

    @Autowired
    private IHorarioDAO horarioDAO;


    @Override
    public List<Horario> findAll() {
        return horarioDAO.findAll();
    }

    @Override
    public List<Horario> findByMateriaAndGrupo(Materia materia, Grupo grupo) {
        return horarioDAO.findByMateriaAndGrupo(materia,grupo);
    }

    @Override
    public Optional<Horario> findById(Long id) {
        return horarioDAO.findById(id);
    }

    @Override
    public void save(Horario horario) {
        horarioDAO.save(horario);
    }

    @Override
    public void deleteById(Long id) {
        horarioDAO.deleteById(id);
    }
}
