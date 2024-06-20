package edu.exam.configuration;

import edu.exam.discipline.Discipline;
import edu.exam.discipline.DisciplineRepository;
import edu.exam.result.ResultType;
import edu.exam.participant.Gender;
import edu.exam.participant.Participant;
import edu.exam.participant.ParticipantRepository;
import edu.exam.result.Result;
import edu.exam.result.ResultRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class InitData implements CommandLineRunner {

    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;
    private final Random random = new Random();

    public InitData(ParticipantRepository participantRepository, DisciplineRepository disciplineRepository, ResultRepository resultRepository) {
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("InitData");
        createDisciplines();
        createParticipants();
        createResults();
    }

    private void createDisciplines() {
        System.out.println("Create disciplines");
        List<Discipline> disciplines = List.of(
                new Discipline("100-meterløb", ResultType.TIME),
                new Discipline("Længdespring", ResultType.DISTANCE),
                new Discipline("Højdespring", ResultType.DISTANCE),
                new Discipline("Kuglestød", ResultType.DISTANCE)
        );

        disciplineRepository.saveAll(disciplines);
    }

    private void createParticipants() {
        System.out.println("Create participants");

        List<Discipline> allDisciplines = disciplineRepository.findAll();

        List<Participant> participants = List.of(
                new Participant("Albert Andersen", Gender.MALE, LocalDate.of(2000, 1, 1), "Hold A"),
                new Participant("Amalie Andersen", Gender.FEMALE, LocalDate.of(1973, 4, 1), "Hold A"),
                new Participant("Berta Bodilsen", Gender.FEMALE, LocalDate.of(1995, 5, 15), "Hold B"),
                new Participant("Bent Boesen", Gender.MALE, LocalDate.of(1995, 5, 15), "Hold B"),
                new Participant("Casper Christensen", Gender.MALE, LocalDate.of(1959, 1, 10), "Hold A"),
                new Participant("Clara Christiansen", Gender.FEMALE, LocalDate.of(1959, 3, 20), "Hold B"),
                new Participant("David Dahl", Gender.MALE, LocalDate.of(1998, 12, 5), "Hold A"),
                new Participant("Diana Damgaard", Gender.FEMALE, LocalDate.of(1998, 6, 12), "Hold B"),
                new Participant("Erik Eriksen", Gender.MALE, LocalDate.of(2008, 9, 18), "Hold A"),
                new Participant("Ella Engstrøm", Gender.FEMALE, LocalDate.of(2008, 7, 7), "Hold B"),
                new Participant("Frederik Fisker", Gender.MALE, LocalDate.of(2012, 11, 25), "Hold A"),
                new Participant("Frida Frederiksen", Gender.FEMALE, LocalDate.of(2012, 4, 30), "Hold B"),
                new Participant("Gustav Gundersen", Gender.MALE, LocalDate.of(2017, 2, 14), "Hold A"),
                new Participant("Greta Gustavsdottir", Gender.FEMALE, LocalDate.of(2017, 8, 22), "Hold B")
        );


        // Assigning disciplines to each participant
        participants.forEach(participant -> {
            List<Discipline> assignedDisciplines = new ArrayList<>();
            assignedDisciplines.add(allDisciplines.get(0)); // Assigning the first discipline
            assignedDisciplines.add(allDisciplines.get(1)); // Assigning the second discipline

            participant.setDisciplines(assignedDisciplines);
        });

        participantRepository.saveAll(participants);
    }


    private void createResults() {
        System.out.println("Create results");

        List<Participant> participants = participantRepository.findAll();
        List<Discipline> disciplines = disciplineRepository.findAll();

        List<Result> results = new ArrayList<>();

        for (Participant participant : participants) {
            for (Discipline discipline : participant.getDisciplines()) {
                double resultValue = generateRandomResult(discipline.getResultType());
                Result result = new Result(participant, discipline, resultValue, LocalDate.now(), discipline.getResultType());
                results.add(result);
            }
        }

        resultRepository.saveAll(results);
    }

    private double generateRandomResult(ResultType resultType) {
        return switch (resultType) {
            case TIME -> 10 + random.nextDouble() * 5; // Random time between 10 and 15 seconds
            case DISTANCE -> 1 + random.nextDouble() * 9; // Random distance between 1 and 10 meters
            case POINTS -> random.nextInt(100) + 1; // Random points between 1 and 100
            default -> 0;
        };
    }
}
