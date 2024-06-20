package edu.exam.discipline;

import edu.exam.result.ResultType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DisciplineServiceTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @InjectMocks
    private DisciplineService disciplineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDiscipline() {
        Discipline discipline = new Discipline("100-meterløb", ResultType.TIME);
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(discipline);

        Discipline createdDiscipline = disciplineService.createDiscipline(discipline);
        assertNotNull(createdDiscipline);
        assertEquals("100m", createdDiscipline.getDisciplineName());
        verify(disciplineRepository, times(1)).save(discipline);
    }

    @Test
    void testGetDisciplineById() {
        Discipline discipline = new Discipline("Længdespring", ResultType.DISTANCE);
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));

        Optional<Discipline> foundDiscipline = disciplineService.getDisciplineById(1L);
        assertTrue(foundDiscipline.isPresent());
        assertEquals("Long Jump", foundDiscipline.get().getDisciplineName());
        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateDiscipline() {
        Discipline discipline = new Discipline("Højdespring", ResultType.DISTANCE);
        Discipline updatedDiscipline = new Discipline("High Jump", ResultType.DISTANCE);
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(updatedDiscipline);

        Discipline result = disciplineService.updateDiscipline(1L, updatedDiscipline);
        assertNotNull(result);
        assertEquals("High Jump", result.getDisciplineName());
        assertEquals(ResultType.DISTANCE, result.getResultType());
        verify(disciplineRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).save(updatedDiscipline);
    }

    @Test
    void testDeleteDiscipline() {
        Discipline discipline = new Discipline("Kuglestød", ResultType.DISTANCE);
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        doNothing().when(disciplineRepository).delete(discipline);

        disciplineService.deleteDiscipline(1L);
        verify(disciplineRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).delete(discipline);
    }
}