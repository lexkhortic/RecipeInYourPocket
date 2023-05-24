package by.lexkhortic.recipeInPocket.services;

import by.lexkhortic.recipeInPocket.models.Medicine;
import by.lexkhortic.recipeInPocket.models.Owner;
import by.lexkhortic.recipeInPocket.models.Pharmacy;

public interface OwnerDaoServices {
    Owner findOwnerByID(Long ownerID);
    Pharmacy findPharmacyByID(long id);
    Medicine findMedicineByID(long id);
    long addNewPharmacyToOwner(long ownerID, String city, String address, String title, String phone, double latitude, double longitude);
    void changeInfoPharmacyFromOwnerByID(long pharmID, String city, String address, String title, String phone, double latitude, double longitude);
    void addMedicineToPharmacy(long pharmID, String name, String country, int count, double price);
    void changeMedicineFromPharmacyByID(long medID, String name, String country, int count, double price);
    void deleteMedicineFromPharmacyByID(long medID);
    void deletePharmacyByID(long pharmID);
}
