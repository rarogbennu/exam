package edu.exam.participant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantService participantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateParticipant() {
        Participant participant = new Participant("John Doe", Gender.MALE, LocalDate.of(2000, 1, 1), "Team A");
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        Participant createdParticipant = participantService.createParticipant(participant);
        assertNotNull(createdParticipant);
        assertEquals("John Doe", createdParticipant.getName());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    void testGetParticipantById() {
        Participant participant = new Participant("Jane Doe",  Gender.FEMALE, LocalDate.of(1995, 5, 15), "Team B");
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));

        Optional<Participant> foundParticipant = participantService.getParticipantById(1L);
        assertTrue(foundParticipant.isPresent());
        assertEquals("Jane Doe", foundParticipant.get().getName());
        verify(participantRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateParticipant() {
        Participant participant = new Participant("John Doe",  Gender.MALE, LocalDate.of(2000, 1, 1), "Team A");
        Participant updatedParticipant = new Participant("John Smith",  Gender.MALE, LocalDate.of(2000, 1, 1), "Team A");
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        when(participantRepository.save(any(Participant.class))).thenReturn(updatedParticipant);

        Participant result = participantService.updateParticipant(1L, updatedParticipant);
        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        verify(participantRepository, times(1)).findById(1L);
        verify(participantRepository, times(1)).save(updatedParticipant);
    }

    @Test
    void testDeleteParticipant() {
        doNothing().when(participantRepository).deleteById(1L);

        participantService.deleteParticipant(1L);
        verify(participantRepository, times(1)).deleteById(1L);
    }
}