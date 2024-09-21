package service;

import model.Material;
import repository.interfaces.MaterialRepositoryInterface;

import java.util.List;

public class MaterialService {
    private final MaterialRepositoryInterface materialRepository;

    public MaterialService(MaterialRepositoryInterface materialRepository) {
        this.materialRepository = materialRepository;
    }

    public void addMaterial(Material material) {
        materialRepository.addMaterial(material);
    }

    public Material getMaterialById(int id) {
        return materialRepository.getMaterialById(id);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.getAllMaterials();
    }

    public void updateMaterial(int id,Material material) {
        materialRepository.updateMaterial(id,material);
    }

    public void deleteMaterial(int id) {
        materialRepository.deleteMaterial(id);
    }

}
