package repository.interfaces;

import model.Estimate;

import java.util.List;

public interface EstimateRepositoryInterface {
    void addEstimate(Estimate estimate);
    Estimate getEstimateById(int id);
    List<Estimate> getAllEstimates();
    void updateEstimate(int id,Estimate estimate);
    void deleteEstimate(int id);
}
