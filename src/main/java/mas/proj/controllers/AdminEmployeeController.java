package mas.proj.controllers;

import lombok.Getter;
import mas.proj.dao.*;
import mas.proj.enums.OrderStatus;
import mas.proj.enums.Type;
import mas.proj.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.*;

@Controller
public class AdminEmployeeController {

    @Getter
    private CarRepo carRepo;

    @Getter
    @Autowired
    private PersonRepo personRepo;

    @Autowired
    public void setCarRepo(CarRepo carRepo){
        this.carRepo=carRepo;
    }

    @Autowired
    private AdminEmplyeeRepo adminEmplyeeRepo;

    @Autowired
    private RepairOrderRepository repairOrderRepository;

    @Autowired
    private MechanicRepo mechanicRepo;


    @GetMapping("/insert-car-data")
    public String enterCarData(Model model){

        if(HomeController.isAuthenticated.isPresent()) {
            if(HomeController.isAuthenticated.get().getType().contains("ADMIN")) {
                model.addAttribute("car", new Car());
                model.addAttribute("person", new Person());
                model.addAttribute("repair", new RepairOrder());
                return "newCar";
            }
        }
        return "error";

    }


    @PostMapping("/insert-car-data")
    @Transactional
    public String processAndSaveCarData(@ModelAttribute("car") @Valid Car car, BindingResult bindingResultCar,
                                        @ModelAttribute("person") @Valid Person person, BindingResult bindingResultPerson,
                                        @ModelAttribute("repair")@Valid RepairOrder repairOrder) {

        if(bindingResultCar.hasErrors() || bindingResultPerson.hasErrors()){
            System.out.println(bindingResultCar.getFieldError());
            System.out.println(bindingResultPerson.getFieldError());
            return "newCar";
        }
                person.setType(Type.CLIENT.toString());
                car.setPerson(person);

                if(personRepo.findByEmail(person.getEmail()).isPresent() && person.getType().contains("CLIENT")){
                    return "newCar";
                }

                personRepo.save(person);
                carRepo.save(car);


                AdminEmployee ae = (AdminEmployee) HomeController.isAuthenticated.get();
                RepairOrder ro = new RepairOrder();

                Optional<Mechanic> findFirstFree = mechanicRepo.findFirstByFreeIs(true);
                if (findFirstFree.isPresent()) {


                    Mechanic mechanic = findFirstFree.get();
                    mechanicRepo.changeIsFreeToFalse(mechanic.getId());

                    ro.setMechanicRepair(findFirstFree.get());
                    Set<RepairOrder> mechanicRepairs = new HashSet<>(findFirstFree.get().getMechanicRepairOrder());
                    mechanicRepairs.add(ro);
                    findFirstFree.get().setMechanicRepairOrder(mechanicRepairs);
                } else {
                    return "redirect:/";
                }
                ro.setAdminEmployeeRepair(ae);
                ro.setStatus(OrderStatus.ZAREJESTROWANE.toString());
                ro.setCarRepair(car);
                ro.setGeneralDescription(repairOrder.getGeneralDescription());

                adminEmplyeeRepo.save(ae);
                findFirstFree.ifPresent(mechanic -> mechanicRepo.save(mechanic));

                repairOrderRepository.save(ro);


         return "redirect:/";
    }

    @GetMapping("/addMechanic")
    public String addNewMechanic(Model model){
        Mechanic p = new Mechanic();
        if(HomeController.isAuthenticated.isPresent()) {
         if(HomeController.isAuthenticated.get().getType().contains("ADMIN"))

            model.addAttribute("newMecha", p);
            return "newMechanic";
        }
        else
            return "error";
    }

    @PostMapping("/addMechanic")
    public String addNewMechanicAndSave(@ModelAttribute("newMecha") @Valid Mechanic mechanic, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            System.out.println(Arrays.toString(bindingResult.getSuppressedFields()));
            System.out.println(bindingResult.getFieldError());
            return "newMechanic";
        }
        String specs = mechanic.getSpecialization();
        System.out.println(specs);

        if(mechanicRepo.findMechanicByEmail(mechanic.getEmail()).isPresent()){
            return "newMechanic";
        }
        mechanicRepo.save( mechanic.addMechanic(specs));

        List<Mechanic> mechanics = mechanicRepo.findAll();
        mechanics.forEach(System.out::println);

        return "redirect:/";
    }

}
