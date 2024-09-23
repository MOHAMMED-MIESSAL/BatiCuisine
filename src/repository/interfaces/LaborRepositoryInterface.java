package repository.interfaces;

import model.Labor;

import java.util.List;

public interface LaborRepositoryInterface {
    void addLabor(Labor labor);
    Labor getLaborById(int id);
    List<Labor> getAllLabors();
    void updateLabor(int id, Labor labor);
    void deleteLabor(int id);
}
