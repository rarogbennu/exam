package edu.exam.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("SELECT DISTINCT p.team FROM Participant p")
    List<String> findDistinctTeams();
}