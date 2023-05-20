package by.lexkhortic.recipeInPocket.controllers;

import by.lexkhortic.recipeInPocket.models.Medicine;
import by.lexkhortic.recipeInPocket.models.Pharmacy;
import by.lexkhortic.recipeInPocket.repositories.PharmacyRepository;
import by.lexkhortic.recipeInPocket.services.IndexServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private IndexServices indexServices;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @PostMapping("/find-medicines")
    public String findMedicines(
            @RequestParam(name = "medicine_name", required = false) Set<String> medicinesList)
    {
        medicinesList.forEach(System.out::println);
        Map<String, Set<Pharmacy>> medicinePharmaciesMap = new HashMap<>();

        List<Medicine> medicinesID = indexServices.findAllIDMedicines(medicinesList);

        for (Medicine medicine : medicinesID) {
            medicinePharmaciesMap.put(medicine.getName(), new HashSet<>());
        }

        for (Medicine medicine : medicinesID) {
            medicinePharmaciesMap.get(medicine.getName()).add(pharmacyRepository.getReferenceById(medicine.getPharmacyID()));
        }

        System.out.println("Кол-во лекарств в Map - " + medicinePharmaciesMap.size());
        for (Map.Entry<String, Set<Pharmacy>> entry : medicinePharmaciesMap.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            entry.getValue().forEach(pharmacy -> {
                System.out.print(pharmacy.getId() + " ");
            });
            System.out.println();
        }
        return "find-medicines";
    }

    @GetMapping("/")
    public String startApp(Model model) {
        model.addAttribute("allPharmacies", indexServices.getAllPharmaciesFromDB());
        return "index";
    }

}
