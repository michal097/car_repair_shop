package mas.proj.controllers;

import lombok.Getter;
import mas.proj.dao.Car;
import mas.proj.dao.Mechanic;
import mas.proj.dao.RepairOrder;
import mas.proj.repos.CarRepo;
import mas.proj.repos.MechanicRepo;
import mas.proj.repos.PersonRepo;
import mas.proj.repos.RepairOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class ClientController {

    @Getter
    private MechanicRepo mechanicRepo;

    @Getter
    private PersonRepo personRepo;

    @Getter
    private RepairOrderRepository repairOrderRepository;
    @Getter
    private CarRepo carRepo;

    @Autowired
    public ClientController(MechanicRepo mechanicRepo, PersonRepo personRepo, RepairOrderRepository repairOrderRepository, CarRepo carRepo){
        this.mechanicRepo=mechanicRepo;
        this.personRepo=personRepo;
        this.repairOrderRepository=repairOrderRepository;
        this.carRepo=carRepo;
    }

    @GetMapping(value = "/search")
    public String search(@RequestParam(value = "search", required = false) String searchPhrase, Model model) {

        RepairOrder getRepairStatus = null;
        List<Mechanic> mechanics= null;
        try {
            Car car = carRepo.findByVIN(searchPhrase);

             getRepairStatus = repairOrderRepository.findByCarRepair(car);
        }catch (Exception e){
            System.out.println("nie ma takiego vinu");
        }
        try {
         mechanics = mechanicRepo.findMechanicBySpecializationContains(searchPhrase);
        }catch (Exception e){
            System.out.println("Nie ma mechanikow ");
        }
        model.addAttribute("repairOrderDetails", getRepairStatus);
        model.addAttribute("mechanicsWithSpec", mechanics);

        return "search";
    }


}
