package repository.interfaces;

import model.Material;

import java.util.List;

public interface MaterialRepositoryInterface {
    void addMaterial(Material material);
    Material getMaterialById(int id);
    List<Material> getAllMaterials();
    void updateMaterial(int id,Material material);
    void deleteMaterial(int id);
}
