package by.lexkhortic.recipeInPocket.models;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pharmacies")
public class Pharmacy implements Serializable, Comparable<Pharmacy> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "title")
    private String title;
    @Column(name = "phone")
    private String phone;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "id")
    private List<Medicine> medicinesList;

    public Pharmacy(String city, String address, String title, String phone, double latitude, double longitude, List<Medicine> medicinesList) {
        this.city = city;
        this.address = address;
        this.title = title;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.medicinesList = medicinesList;
    }

    public Pharmacy(String city, String address, String title, double latitude, double longitude) {
        this.city = city;
        this.address = address;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int compareTo(Pharmacy o) {
        return title.compareTo(o.getTitle());
    }
}
