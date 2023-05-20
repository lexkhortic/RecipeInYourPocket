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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        Map<String, Set<Long>> medicinePharmaciesMap = new HashMap<>();

        List<Medicine> medicinesID = indexServices.findAllIDMedicines(medicinesList);

        for (Medicine medicine : medicinesID) {
            medicinePharmaciesMap
                    .computeIfAbsent(medicine.getName(), v -> new HashSet<>())
                    .add(medicine.getPharmacyID());
        }

        List<Long> pharmaciesID = new ArrayList<>();
        for (Map.Entry<String, Set<Long>> entry : medicinePharmaciesMap.entrySet()) {
            pharmaciesID.addAll(entry.getValue().stream().toList());
        }

        pharmaciesID = pharmaciesID.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .filter(el -> el.getValue().size() == medicinesList.size())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        pharmaciesID.forEach(System.out::println);

        Map<Pharmacy, List<Medicine>> resultQuery = new HashMap<>();
        for (Long id : pharmaciesID) {
            Pharmacy pharmacy = pharmacyRepository.getReferenceById(id);
            resultQuery
                    .computeIfAbsent(pharmacy, v -> new ArrayList<>())
                    .addAll(findAllMedicinesFromPharmacy(pharmacy, medicinesList));
        }

        for (Map.Entry<Pharmacy, List<Medicine>> entry : resultQuery.entrySet()) {
            System.out.println(entry.getKey().getTitle() + ":");
            entry.getValue().forEach(el -> {
                System.out.println(el.getName() + ", " + el.getPrice() + " руб.");
            });
        }

        return "find-medicines";
    }

    private List<Medicine> findAllMedicinesFromPharmacy(Pharmacy pharmacy, Set<String> medicineList) {
        return pharmacy.getMedicinesList().stream()
                .filter(el -> checkMedicineInList(el.getName(), medicineList))
                .collect(Collectors.toList());
    }

    private boolean checkMedicineInList(String medicine, Set<String> medicineList) {
        AtomicBoolean isHas = new AtomicBoolean(false);
        medicineList.forEach(el -> {
            if (el.equals(medicine)) {
                 isHas.set(true);
            }
        });
        return isHas.get();
    }

    @GetMapping("/")
    public String startApp(Model model) {
        model.addAttribute("allPharmacies", indexServices.getAllPharmaciesFromDB());
        return "index";
    }

}
