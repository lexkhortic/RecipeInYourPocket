package by.lexkhortic.recipeInPocket.controllers;

import by.lexkhortic.recipeInPocket.models.Medicine;

import by.lexkhortic.recipeInPocket.models.Pharmacy;
import by.lexkhortic.recipeInPocket.repositories.PharmacyRepository;
import by.lexkhortic.recipeInPocket.services.IndexServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;


@Controller
public class IndexController {

    @Autowired
    private IndexServices indexServices;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @GetMapping("/")
    public String startApp(Model model) {
        model.addAttribute("allPharmacies", indexServices.getAllPharmaciesFromDB());
        return "index";
    }

    @PostMapping("/find-medicines")
    public String findMedicines(
            @RequestParam(name = "medicine_name", required = false) Set<String> medicinesList,
            Model model)
    {
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


        Map<Pharmacy, List<Medicine>> resultQuery = new HashMap<>();
        for (Long id : pharmaciesID) {
            Pharmacy pharmacy = pharmacyRepository.getReferenceById(id);
            resultQuery
                    .computeIfAbsent(pharmacy, v -> new ArrayList<>())
                    .addAll(findAllMedicinesFromPharmacy(pharmacy, medicinesList));
        }

        for (Map.Entry<Pharmacy, List<Medicine>> entry : resultQuery.entrySet()) {
            long id = entry.getKey().getId();
            entry.getValue().add(new Medicine("", "", 0, findTotalSumPrice(entry.getValue()), id));
        }

//        sortByTotalPrice(resultQuery);

        List<Pharmacy> pharmaciesList = new ArrayList<>();
        for (Map.Entry<Pharmacy, List<Medicine>> entry : resultQuery.entrySet()) {
            pharmaciesList.add(new Pharmacy(
                    entry.getKey().getCity(),
                    entry.getKey().getAddress(),
                    entry.getKey().getTitle(),
                    entry.getKey().getLatitude(),
                    entry.getKey().getLongitude()
            ));
        }

        model.addAttribute("pharmaciesMap", resultQuery);
        model.addAttribute("allPharmaciesWhereHaveMedicines", pharmaciesList);

        return "find-medicines";
    }

    private List<Medicine> findAllMedicinesFromPharmacy(Pharmacy pharmacy, Set<String> medicineList) {
        return pharmacy.getMedicinesList().stream()
                .filter(el -> checkMedicineInList(el.getName(), medicineList))
                .collect(Collectors.toList());
    }

    private double findTotalSumPrice(List<Medicine> medicines) {
        AtomicReference<Double> totalPrice = new AtomicReference<>((double) 0);
        medicines.forEach(el -> totalPrice.updateAndGet(tPrice -> tPrice + el.getPrice()));
        return (double) Math.round(totalPrice.get() * 100) / 100;
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

//    private static Map<Pharmacy, List<Medicine>> sortByTotalPrice(Map<Pharmacy, List<Medicine>> map) {
//
//        class TotalPriceComparator implements Comparator<List<Medicine>> {
//            @Override
//            public int compare(List<Medicine> o1, List<Medicine> o2) {
//                return (int) (o1.get(o1.size()-1).getPrice() - o2.get(o2.size()-1).getPrice());
//            }
//        }
//
//        Comparator<List<Medicine>> totalPriceComparator = new TotalPriceComparator();
//
//        Map<Pharmacy, List<Medicine>> sortedMap = map
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue())
//                .collect(
//                        Collectors.toMap(e -> e.getKey(), e -> e.getValue().get(e.getValue().size()-1), (e1, e2) -> e2,
//                                LinkedHashMap::new));
//
//        return sortedMap;
//    }
}


