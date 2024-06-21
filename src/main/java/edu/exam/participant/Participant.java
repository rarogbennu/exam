package edu.exam.participant;

import edu.exam.discipline.Discipline;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthdate;
    private String team;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Discipline> disciplines = new ArrayList<>();

    public Participant(String name, Gender gender, LocalDate birthdate, String team) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.team = team;
    }

    public Participant(String name, Gender gender, LocalDate birthdate, String team, List<Discipline> disciplines) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.team = team;
        this.disciplines = disciplines;
    }
}