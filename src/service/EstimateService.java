package service;

import model.Estimate;
import repository.interfaces.EstimateRepositoryInterface;

import java.util.List;

public class EstimateService {
    private final EstimateRepositoryInterface estimateRepository;

    public EstimateService(EstimateRepositoryInterface estimateRepository) {
        this.estimateRepository = estimateRepository;
    }

    public void addEstimate(Estimate estimate) {
        estimateRepository.addEstimate(estimate);
    }

    public Estimate getEstimateById(int id) {
        return estimateRepository.getEstimateById(id);
    }

    public List<Estimate> getAllEstimates() {
        return estimateRepository.getAllEstimates();
    }

    public void updateEstimate(int id,Estimate estimate) {
        estimateRepository.updateEstimate(id,estimate);
    }

    public void deleteEstimate(int id) {
        estimateRepository.deleteEstimate(id);
    }

}
