package by.lexkhortic.recipeInPocket.models;

import by.lexkhortic.recipeInPocket.models.emum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "owner_login")
    private String login;
    @Column(name = "owner_password")
    private String password;
    @Column(name = "owner_role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "name_company")
    private String nameCompany;
    @Column(name = "phone_company")
    private String phoneCompany;
    @Column(name = "link_company")
    private String linkCompany;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private List<Pharmacy> pharmaciesList;

    public Owner(String login, String password, Role role, String nameCompany, String phoneCompany, String linkCompany, List<Pharmacy> pharmaciesList) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.nameCompany = nameCompany;
        this.phoneCompany = phoneCompany;
        this.linkCompany = linkCompany;
        this.pharmaciesList = pharmaciesList;
    }
}
