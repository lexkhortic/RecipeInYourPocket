package by.lexkhortic.recipeInPocket.services;

import by.lexkhortic.recipeInPocket.models.Medicine;
import by.lexkhortic.recipeInPocket.models.Owner;
import by.lexkhortic.recipeInPocket.models.Pharmacy;
import by.lexkhortic.recipeInPocket.repositories.MedicineRepository;
import by.lexkhortic.recipeInPocket.repositories.OwnerRepository;
import by.lexkhortic.recipeInPocket.repositories.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerDaoServicesImpl implements OwnerDaoServices{

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;
    @Autowired
    private MedicineRepository medicineRepository;


    @Override
    public Owner findOwnerByID(Long ownerID) {
        Optional<Owner> owners = ownerRepository.findById(ownerID);
        List<Owner> ownerList = new ArrayList<>();
        owners.ifPresent(ownerList::add);
        return ownerList.get(0);
    }

    @Override
    public Pharmacy findPharmacyByID(long id) {
        return pharmacyRepository.getReferenceById(id);
    }

    @Override
    public Medicine findMedicineByID(long id) {
        return medicineRepository.getReferenceById(id);
    }

    @Override
    public long addNewPharmacyToOwner(long ownerID, String city, String address, String title, String phone, double latitude, double longitude) {
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerID);
        List<Owner> ownerList = new ArrayList<>();
        ownerOptional.ifPresent(ownerList::add);
        Owner owner =  ownerList.get(0);
        Pharmacy newPharmacy = new Pharmacy(city, address, title, phone, latitude, longitude, new ArrayList<>());
        owner.getPharmaciesList().add(newPharmacy);
        Pharmacy pharmacy = pharmacyRepository.save(newPharmacy);
        ownerRepository.save(owner);
        return pharmacy.getId();
    }

    @Override
    public void changeInfoPharmacyFromOwnerByID(long pharmID, String city, String address, String title, String phone, double latitude, double longitude) {
        Pharmacy pharmacy = pharmacyRepository.getReferenceById(pharmID);
        pharmacy.setCity(city);
        pharmacy.setAddress(address);
        pharmacy.setTitle(title);
        pharmacy.setPhone(phone);
        pharmacy.setLatitude(latitude);
        pharmacy.setLongitude(longitude);
        pharmacyRepository.save(pharmacy);
    }

    @Override
    public void addMedicineToPharmacy(long pharmID, String name, String country, int count, double price) {
        Optional<Pharmacy> pharmacyOptional = pharmacyRepository.findById(pharmID);
        List<Pharmacy> pharmacyList = new ArrayList<>();
        pharmacyOptional.ifPresent(pharmacyList::add);
        Pharmacy pharmacy = pharmacyList.get(0);
        Medicine newMedicine = new Medicine(name, country, count, price, pharmID);
        pharmacy.getMedicinesList().add(newMedicine);
        medicineRepository.save(newMedicine);
        pharmacyRepository.save(pharmacy);
    }

    @Override
    public void changeMedicineFromPharmacyByID(long medID, String name, String country, int count, double price) {
        Medicine medicine = medicineRepository.getReferenceById(medID);
        medicine.setName(name);
        medicine.setCountryManufacturer(country);
        medicine.setCount(count);
        medicine.setPrice(price);
        medicineRepository.save(medicine);
    }

    @Override
    public void deleteMedicineFromPharmacyByID(long medID) {
        medicineRepository.delete(medicineRepository.getReferenceById(medID));
    }
}
