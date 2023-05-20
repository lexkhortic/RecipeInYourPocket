package by.lexkhortic.recipeInPocket.services;

import by.lexkhortic.recipeInPocket.models.Medicine;
import by.lexkhortic.recipeInPocket.models.Pharmacy;
import by.lexkhortic.recipeInPocket.repositories.MedicineRepository;
import by.lexkhortic.recipeInPocket.repositories.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class IndexServices {

    @Autowired
    private PharmacyRepository pharmacyRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    public List<Pharmacy> getAllPharmaciesFromDB() {
        return pharmacyRepository.findAll();
    }

    public List<Medicine> findAllIDMedicines(Set<String> medicinesName) {
        return medicineRepository.findAllIDMedicinesByName(medicinesName);
    }
}
