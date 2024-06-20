package edu.exam.result;

import edu.exam.participant.Participant;
import edu.exam.discipline.Discipline;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Participant participant;

    @ManyToOne
    private Discipline discipline;

    private double resultValue;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    public Result(Participant participant, Discipline discipline, double resultValue, LocalDate date, ResultType resultType) {
        this.participant = participant;
        this.discipline = discipline;
        this.resultValue = resultValue;
        this.date = date;
        this.resultType = resultType;
    }
}