package edu.exam.discipline;

import edu.exam.result.ResultType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String disciplineName;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    public Discipline(String disciplineName, ResultType resultType) {
        this.disciplineName = disciplineName;
        this.resultType = resultType;
    }
}