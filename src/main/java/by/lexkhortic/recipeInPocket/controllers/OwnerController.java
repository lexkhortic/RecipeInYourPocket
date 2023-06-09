package by.lexkhortic.recipeInPocket.controllers;

import by.lexkhortic.recipeInPocket.models.Owner;
import by.lexkhortic.recipeInPocket.models.Pharmacy;
import by.lexkhortic.recipeInPocket.services.OwnerDaoServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OwnerController {

    @Autowired
    private OwnerDaoServicesImpl ownerServices;

    @GetMapping("/company/{owner_id}/{pharm_id}")
    public String enteredInAccount(Model model, @PathVariable long owner_id, @PathVariable long pharm_id) {
        Owner owner = ownerServices.findOwnerByID(owner_id);
        List<Pharmacy> pharmacies = owner.getPharmaciesList();
        model.addAttribute("ownerID", owner.getId());
        model.addAttribute("company_name", owner.getNameCompany());
        model.addAttribute("pharmacies", owner.getPharmaciesList().stream().sorted().collect(Collectors.toList()));

        if (pharmacies.isEmpty()) {
            model.addAttribute("pharmacy", null);
            model.addAttribute("medicines", null);
        } else {
            Pharmacy pharmacy = owner.getPharmaciesList()
                    .stream()
                    .filter(el -> el.getId() == pharm_id)
                    .findFirst()
                    .orElse(null);
            model.addAttribute("pharmacy",  pharmacy);
            assert pharmacy != null;
            model.addAttribute("medicines", pharmacy.getMedicinesList().stream().sorted().collect(Collectors.toList()));
        }
        return "owner";
    }

    @GetMapping("/company/{ownerID}/create-pharm")
    public String createNewPharmacy(@PathVariable long ownerID, Model model) {
        model.addAttribute("operation", "create_pharmacy");
        Owner owner = ownerServices.findOwnerByID(ownerID);
        model.addAttribute("owner", owner);
        if (owner.getPharmaciesList().isEmpty() || owner.getPharmaciesList() == null) {
            model.addAttribute("pharmacyID", 0);
        } else {
            model.addAttribute("pharmacyID", owner.getPharmaciesList().get(0).getId());
        }
        return "operations";
    }

    @PostMapping("/company/{ownerID}/create-pharm")
    public String createNewPharmacy(
            @PathVariable long ownerID,
            @RequestParam(
                    name = "city",
                    required = false) String city,
            @RequestParam(
                    name = "address",
                    required = false) String address,
            @RequestParam(
                    name = "title",
                    required = false) String title,
            @RequestParam(
                    name = "phone",
                    required = false) String phone,
            @RequestParam(
                    name = "latitude",
                    required = false) double latitude,
            @RequestParam(
                    name = "longitude",
                    required = false) double longitude) {
        long pharmID = ownerServices.addNewPharmacyToOwner(ownerID, city, address, title, phone, latitude, longitude);
        System.out.println("Аптека добавлена...");
        return "redirect:/company/" + ownerID + "/" + pharmID;
    }

    @GetMapping("/company/{ownerID}/{pharmID}/add-med")
    public String addNewMedicine(@PathVariable long ownerID, @PathVariable long pharmID, Model model) {
        model.addAttribute("operation", "add_medicine");
        model.addAttribute("ownerID", ownerID);
        model.addAttribute("pharmacy", ownerServices.findPharmacyByID(pharmID));
        return "operations";
    }

    @PostMapping("/company/{ownerID}/{pharmID}/add-med")
    public String addNewMedicine(
            @PathVariable long ownerID,
            @PathVariable long pharmID,
            @RequestParam(
                    name = "name",
                    required = false) String name,
            @RequestParam(
                    name = "country",
                    required = false) String country,
            @RequestParam(
                    name = "count",
                    required = false) int count,
            @RequestParam(
                    name = "price",
                    required = false) double price) {
        ownerServices.addMedicineToPharmacy(pharmID, name, country, count, price);
        System.out.println("Медикоменты добавлены...");
        return "redirect:/company/" + ownerID + "/" + pharmID;
    }

    @GetMapping("/company/{ownerID}/{pharmID}/change-info")
    public String changeInfoAboutPharmacy(@PathVariable long ownerID, @PathVariable long pharmID, Model model) {
        model.addAttribute("operation", "change_pharmacy");
        model.addAttribute("pharmacy", ownerServices.findPharmacyByID(pharmID));
        model.addAttribute("ownerID", ownerID);
        return "operations";
    }

    @PostMapping("/company/{ownerID}/{pharmID}/change-info")
    public String changeInfoAboutPharmacy(
            @PathVariable long ownerID,
            @PathVariable long pharmID,
            @RequestParam(
                    name = "city",
                    required = false) String city,
            @RequestParam(
                    name = "address",
                    required = false) String address,
            @RequestParam(
                    name = "title",
                    required = false) String title,
            @RequestParam(
                    name = "phone",
                    required = false) String phone,
            @RequestParam(
                    name = "latitude",
                    required = false) double latitude,
            @RequestParam(
                    name = "longitude",
                    required = false) double longitude
    ) {
        ownerServices.changeInfoPharmacyFromOwnerByID(pharmID, city, address, title, phone, latitude, longitude);
        return "redirect:/company/" + ownerID + "/" + pharmID;
    }

    @GetMapping("/company/{ownerID}/{pharmID}/change-med/{medID}")
    public String changeMedicineFromPharmacy(@PathVariable long ownerID, @PathVariable long pharmID, Model model, @PathVariable long medID) {
        model.addAttribute("operation", "change_medicine");
        model.addAttribute("pharmacy", ownerServices.findPharmacyByID(pharmID));
        model.addAttribute("medicine", ownerServices.findMedicineByID(medID));
        model.addAttribute("ownerID", ownerID);
        return "operations";
    }

    @PostMapping("/company/{ownerID}/{pharmID}/change-med/{medID}")
    public String changeMedicineFromPharmacy(
            @PathVariable long ownerID,
            @PathVariable long pharmID,
            @PathVariable long medID,
            @RequestParam(
                    name = "name",
                    required = false) String name,
            @RequestParam(
                    name = "country",
                    required = false) String country,
            @RequestParam(
                    name = "count",
                    required = false) int count,
            @RequestParam(
                    name = "price",
                    required = false) double price
    ) {
        ownerServices.changeMedicineFromPharmacyByID(medID, name, country, count, price);
        return "redirect:/company/" + ownerID + "/" + pharmID;
    }

    @GetMapping("/company/{ownerID}/{pharmID}/del-med/{medID}")
    public String deleteMedicineFromPharmacy(@PathVariable long ownerID, @PathVariable long pharmID, @PathVariable long medID) {
        ownerServices.deleteMedicineFromPharmacyByID(medID);
        return "redirect:/company/" + ownerID + "/" + pharmID;
    }

    @GetMapping("/company/{owner_id}/{pharm_id}/delete-pharm")
    public String deletePharmacy(@PathVariable long owner_id, @PathVariable long pharm_id) {
        ownerServices.deletePharmacyByID(pharm_id);
        Owner owner = ownerServices.findOwnerByID(owner_id);
        if (owner.getPharmaciesList().isEmpty() || owner.getPharmaciesList() == null) {
            return "redirect:/company/" + owner_id + "/0";
        } else {
            return "redirect:/company/" + owner_id + "/" + owner.getPharmaciesList().get(0).getId();
        }
    }

}
