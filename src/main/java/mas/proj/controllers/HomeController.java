package mas.proj.controllers;


import lombok.Getter;
import lombok.Setter;
import mas.proj.dao.AdminEmployee;
import mas.proj.dao.Mechanic;
import mas.proj.dao.Person;
import mas.proj.dao.RepairOrder;
import mas.proj.repos.AdminEmplyeeRepo;
import mas.proj.repos.MechanicRepo;
import mas.proj.repos.PersonRepo;
import mas.proj.repos.RepairOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class HomeController {

    private PersonRepo personRepo;
    @Getter
    private MechanicRepo mechanicRepo;
    @Getter
    private AdminEmplyeeRepo adminEmplyeeRepo;

    @Getter
    @Setter
    private Person user;

    @Getter
    @Setter
    private Mechanic mechanic;

    @Getter
    @Setter
    private AdminEmployee admin;
    @Getter@Setter
    private RepairOrderRepository repairOrderRepository;
    @Autowired
    public HomeController(PersonRepo personRepo, MechanicRepo mechanicRepo, AdminEmplyeeRepo adminEmplyeeRepo, RepairOrderRepository repairOrderRepository) {
        this.personRepo = personRepo;
        this.mechanicRepo = mechanicRepo;
        this.adminEmplyeeRepo = adminEmplyeeRepo;
        this.repairOrderRepository=repairOrderRepository;
    }


    public static Optional<Person> isAuthenticated = Optional.empty();


    @GetMapping("/")
    public String home(Model model) {







        user = new AdminEmployee();
        user.setEmail("admin");
        user.setPassword("admin");
        user.setType("ADMIN");
        user.setAdress("asdasd");
        user.setName("name");
        user.setPhoneNumber("asd");

        if (personRepo.findByEmail(user.getEmail()).isEmpty()) {

            personRepo.save(user);
        }

        if (isAuthenticated.isPresent()) {
            isAuthenticated.ifPresent(person -> model.addAttribute("autenticated", person));

            return "homeAuth";
        } else
            return "home";
    }


    @GetMapping("/success")
    public String showMe() {
        return "home";
    }


    @GetMapping("/authenticate")
    public String auth(Model model) {

        model.addAttribute("authme", new Person());
        return "login";
    }

    @PostMapping("/authenticate")
    public String postAuthenticate(@ModelAttribute("authme") @Valid Person user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            if (bindingResult.getSuppressedFields().length > 0)
                return "login";

        }
        System.out.println("user: " + user.getPassword());

        try {

            isAuthenticated = personRepo.findFirstByEmailAndPassword(user.getEmail(), user.getPassword());

        } catch (Exception e) {
            System.out.println("nie ma takiego usera");
        }


        if (isAuthenticated.isPresent()) {
            return "redirect:/";
        } else return "redirect:/";
    }

    @GetMapping("/logOut")
    public String logMeOut() {
        if (isAuthenticated.isPresent()) {
            isAuthenticated = Optional.empty();
        }
        return "home";
    }


}
