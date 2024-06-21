package edu.exam.result;

import edu.exam.participant.Participant;
import edu.exam.discipline.Discipline;
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

class ResultServiceTest {

    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private ResultService resultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateResult() {
        Participant participant = new Participant();
        Discipline discipline = new Discipline();
        Result result = new Result(participant, discipline, 9.58, LocalDate.of(2023, 6, 20), ResultType.TIME);
        when(resultRepository.save(any(Result.class))).thenReturn(result);

        Result createdResult = resultService.createResult(result);
        assertNotNull(createdResult);
        assertEquals(9.58, createdResult.getResultValue());
        verify(resultRepository, times(1)).save(result);
    }

    @Test
    void testGetResultById() {
        Participant participant = new Participant();
        Discipline discipline = new Discipline();
        Result result = new Result(participant, discipline, 8.95, LocalDate.of(2023, 6, 20), ResultType.DISTANCE);
        when(resultRepository.findById(1L)).thenReturn(Optional.of(result));

        Optional<Result> foundResult = resultService.getResultById(1L);
        assertTrue(foundResult.isPresent());
        assertEquals(8.95, foundResult.get().getResultValue());
        verify(resultRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateResult() {
        Participant participant = new Participant();
        Discipline discipline = new Discipline();
        Result result = new Result(participant, discipline, 9.58, LocalDate.of(2023, 6, 20), ResultType.TIME);
        Result updatedResult = new Result(participant, discipline, 9.63, LocalDate.of(2023, 6, 20), ResultType.TIME);
        when(resultRepository.findById(1L)).thenReturn(Optional.of(result));
        when(resultRepository.save(any(Result.class))).thenReturn(updatedResult);

        Result resultAfterUpdate = resultService.updateResult(1L, updatedResult);
        assertNotNull(resultAfterUpdate);
        assertEquals(9.63, resultAfterUpdate.getResultValue());
        verify(resultRepository, times(1)).findById(1L);
        verify(resultRepository, times(1)).save(updatedResult);
    }

    @Test
    void testDeleteResult() {
        Participant participant = new Participant();
        Discipline discipline = new Discipline();
        Result result = new Result(participant, discipline, 9.58, LocalDate.of(2023, 6, 20), ResultType.TIME);
        when(resultRepository.findById(1L)).thenReturn(Optional.of(result));
        doNothing().when(resultRepository).delete(result);

        resultService.deleteResult(1L);
        verify(resultRepository, times(1)).findById(1L);
        verify(resultRepository, times(1)).delete(result);
    }
}