package by.lexkhortic.recipeInPocket.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicines")
public class Medicine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String countryManufacturer;
    @Column(name = "count")
    private int count;
    @Column(name = "price")
    private double price;
    @Column(name = "pharmacy_id")
    private long pharmacyID;

    public Medicine(String name, String countryManufacturer, int count, double price, long pharmacyID) {
        this.name = name;
        this.countryManufacturer = countryManufacturer;
        this.count = count;
        this.price = price;
        this.pharmacyID = pharmacyID;
    }
}
