package by.lexkhortic.recipeInPocket.repositories;

import by.lexkhortic.recipeInPocket.models.Medicine;

import java.util.List;
import java.util.Set;

public interface MedicineRepositoryCustom {
    List<Medicine> findAllIDMedicinesByName(Set<String> medicinesName);
}
