package edu.exam.participant;

import edu.exam.discipline.Discipline;
import edu.exam.discipline.DisciplineRepository;
import edu.exam.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;

    public ParticipantService(ParticipantRepository participantRepository, DisciplineRepository disciplineRepository) {
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public Participant createParticipant(Participant participant) {
        List<Discipline> disciplines = participant.getDisciplines().stream()
                .map(discipline -> disciplineRepository.findById(discipline.getId())
                        .orElseThrow(() -> new NotFoundException("Discipline not found for id :: " + discipline.getId())))
                .collect(Collectors.toList());
        participant.setDisciplines(disciplines);
        return participantRepository.save(participant);
    }

    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Participant updateParticipant(Long id, Participant updatedParticipant) {
        return participantRepository.findById(id)
                .map(participant -> {
                    participant.setName(updatedParticipant.getName());
                    participant.setGender(updatedParticipant.getGender());
                    participant.setBirthdate(updatedParticipant.getBirthdate());
                    participant.setTeam(updatedParticipant.getTeam());

                    List<Discipline> disciplines = updatedParticipant.getDisciplines().stream()
                            .map(discipline -> disciplineRepository.findById(discipline.getId())
                                    .orElseThrow(() -> new NotFoundException("Discipline not found for id :: " + discipline.getId())))
                            .collect(Collectors.toList());
                    participant.setDisciplines(disciplines);

                    return participantRepository.save(participant);
                })
                .orElseThrow(() -> new NotFoundException("Participant not found for id :: " + id));
    }

    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    public List<String> getAllTeams() {
        return participantRepository.findDistinctTeams();
    }
}