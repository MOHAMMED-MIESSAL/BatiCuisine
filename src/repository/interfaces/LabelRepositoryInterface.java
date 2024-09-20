package repository.interfaces;

import model.Label;

import java.util.List;

public interface LabelRepositoryInterface {
    void addLabel(Label label);
    Label getLabelById(int id);
    List<Label> getAllLabels();
    void updateLabel(int id,Label label);
    void deleteLabel(int id);
}
