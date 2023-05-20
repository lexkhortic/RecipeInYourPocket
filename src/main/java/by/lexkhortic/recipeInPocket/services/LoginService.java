package by.lexkhortic.recipeInPocket.services;

import by.lexkhortic.recipeInPocket.models.Owner;
import by.lexkhortic.recipeInPocket.models.emum.Role;
import by.lexkhortic.recipeInPocket.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LoginService {

    @Autowired
    private OwnerRepository ownerRepository;

    public Owner checkOwnerByLoginAndPassword(String login, String password) {
        List<Owner> owners = ownerRepository.findByLoginAndPassword(login, password);
        return owners.isEmpty() ? null : owners.get(0);
    }

    public void registerNewOwner(String login, String password, String company, String phone, String link) {
        Owner newOwner = new Owner(login, password, Role.OWNER, company, phone, link, new ArrayList<>());
        ownerRepository.save(newOwner);
    }
}
