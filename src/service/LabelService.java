package service;

import model.Labor;
import repository.interfaces.LaborRepositoryInterface;

import java.util.List;

public class LabelService {
    private final LaborRepositoryInterface labelRepository;

    public LabelService(LaborRepositoryInterface labelRepository) {
        this.labelRepository = labelRepository;
    }

    public void addLabel(Labor labor) {
        labelRepository.addLabor(labor);
    }

    public Labor getLabelById(int id) {
        return labelRepository.getLaborById(id);
    }

    public List<Labor> getAllLabels() {
        return labelRepository.getAllLabors();
    }

    public void updateLabel(int id, Labor labor) {
        labelRepository.updateLabor(id, labor);
    }

    public void deleteLabel(int id) {
        labelRepository.deleteLabor(id);
    }
}
