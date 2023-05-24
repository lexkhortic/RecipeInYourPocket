package by.lexkhortic.recipeInPocket.controllers;

import by.lexkhortic.recipeInPocket.models.Owner;
import by.lexkhortic.recipeInPocket.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("isLogin", false);
        return "login";
    }

    @PostMapping("/login")
    public String login (
            @RequestParam(
                    name = "login",
                    required = false) String login,
            @RequestParam(
                    name = "password",
                    required = false) String password,
            Model model)
    {
        if (login != null && password != null) {
            Owner owner = loginService.checkOwnerByLoginAndPassword(login, password);
            if (owner == null) {
                System.out.println("Owner does not exist");
                model.addAttribute("isLogin", false);
                model.addAttribute("error", "Невеный логин или пароль");
            } else {
                model.addAttribute("isLogin", true);
                model.addAttribute("companyName", owner.getNameCompany());
                model.addAttribute("ownerID", owner.getId());
                if (owner.getPharmaciesList() != null && owner.getPharmaciesList().size() != 0) {
                    model.addAttribute("pharmID", owner.getPharmaciesList().get(0).getId());
                } else  {
                    model.addAttribute("pharmID", 0);
                }
            }
            return "login";
        } else {
            model.addAttribute("isLogin", false);
            return "login";
        }
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam(
                    name = "login",
                    required = false) String login,
            @RequestParam(
                    name = "password",
                    required = false) String password,
            @RequestParam(
                    name = "name_company",
                    required = false) String company,
            @RequestParam(
                    name = "phone_company",
                    required = false) String phone,
            @RequestParam(
                    name = "link_company",
                    required = false) String link,
            Model model
    )
    {
        if (loginService.checkOwnerByLogin(login)) {
            model.addAttribute("login", login);
            model.addAttribute("name_company", company);
            model.addAttribute("phone_company", phone);
            model.addAttribute("link_company", link);
            model.addAttribute("error", "Логин уже существует");
            return "registration";
        } else {
            Long ownerID = loginService.registerNewOwner(login, password, company, phone, link);
            System.out.println("Владелец добавлен... ID - " + ownerID);
            return "redirect:/company/" + ownerID + "/0";
        }
    }

}
