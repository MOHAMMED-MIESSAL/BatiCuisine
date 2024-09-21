package service;

import model.Label;
import repository.interfaces.LabelRepositoryInterface;

import java.util.List;

public class LabelService {
    private final LabelRepositoryInterface labelRepository;

    public LabelService(LabelRepositoryInterface labelRepository) {
        this.labelRepository = labelRepository;
    }

    public void addLabel(Label label) {
        labelRepository.addLabel(label);
    }

    public Label getLabelById(int id) {
        return labelRepository.getLabelById(id);
    }

    public List<Label> getAllLabels() {
        return labelRepository.getAllLabels();
    }

    public void updateLabel(int id,Label label) {
        labelRepository.updateLabel(id,label);
    }

    public void deleteLabel(int id) {
        labelRepository.deleteLabel(id);
    }
}
