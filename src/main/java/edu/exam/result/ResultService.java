package edu.exam.result;

import edu.exam.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Result createResult(Result result) {
        return resultRepository.save(result);
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }

    public Result updateResult(Long id, Result resultDetails) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Result not found for this id :: " + id));

        result.setParticipant(resultDetails.getParticipant());
        result.setDiscipline(resultDetails.getDiscipline());
        result.setResultValue(resultDetails.getResultValue());
        result.setDate(resultDetails.getDate());
        result.setResultType(resultDetails.getResultType());

        return resultRepository.save(result);
    }

    public void deleteResult(Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Result not found for this id :: " + id));

        resultRepository.delete(result);
    }
}
