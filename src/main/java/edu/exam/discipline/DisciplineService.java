package edu.exam.discipline;

import edu.exam.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public Discipline createDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    public Optional<Discipline> getDisciplineById(Long id) {
        return disciplineRepository.findById(id);
    }

    public Discipline updateDiscipline(Long id, Discipline disciplineDetails) {
        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discipline not found for this id :: " + id));

        discipline.setDisciplineName(disciplineDetails.getDisciplineName());
        discipline.setResultType(disciplineDetails.getResultType());

        return disciplineRepository.save(discipline);
    }

    public void deleteDiscipline(Long id) {
        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discipline not found for this id :: " + id));

        disciplineRepository.delete(discipline);
    }
}