package mas.proj.controllers;

import lombok.Getter;
import mas.proj.dao.*;
import mas.proj.enums.OrderStatus;
import mas.proj.repos.*;
import mas.proj.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MechanicController {

    @Getter
    private MechanicRepo mechanicRepo;
    @Getter
    private RepairOrderRepository repairOrderRepository;
    @Getter
    private DamageRepository damageRepository;
    @Getter
    private FileService fileService;
    @Getter
    private PartRepository partRepository;
    @Getter
    private PartOrderRepository partOrderRepository;
    @Getter
    private OrderRepo orderRepo;

    @Autowired
    public MechanicController(MechanicRepo mechanicRepo, RepairOrderRepository repairOrderRepository,
                              DamageRepository damageRepository, FileService fileService,
                              PartRepository partRepository, PartOrderRepository partOrderRepository,
                              OrderRepo orderRepo) {
        this.mechanicRepo = mechanicRepo;
        this.repairOrderRepository = repairOrderRepository;
        this.damageRepository = damageRepository;
        this.fileService = fileService;
        this.partRepository = partRepository;
        this.partOrderRepository = partOrderRepository;
        this.orderRepo = orderRepo;
    }

    @GetMapping("/showRepairList")
    public String showRepairList(Model model) {

        Mechanic mechanic;
        List<RepairOrder> mechanicRepairsOrder;
        if (HomeController.isAuthenticated.isPresent()) {
            if (HomeController.isAuthenticated.get().getType().contains("MECHANIC")) {
                mechanic = (Mechanic) HomeController.isAuthenticated.get();

                mechanicRepairsOrder = repairOrderRepository.findRepairOrdersByMechanicRepair(mechanic)
                        .stream()
                        .filter(s -> s.getStatus()
                                .contains("ZAREJESTROWANE"))
                        .collect(Collectors.toList());

                mechanicRepairsOrder.forEach(System.out::println);

                model.addAttribute("mechanic", mechanicRepairsOrder);
                return "repairList";
            }
        }


        return "error";

    }

    @GetMapping("/repairOrder/{id}")
    public String choosenOne(@PathVariable("id") Long id, Model model) {
        Mechanic mechanic;
        RepairOrder choosenOneRepairOrder = new RepairOrder();

        if (HomeController.isAuthenticated.isPresent()) {
            if (HomeController.isAuthenticated.get().getType().contains("MECHANIC")) {
                mechanic = (Mechanic) HomeController.isAuthenticated.get();
                choosenOneRepairOrder = repairOrderRepository.findRepairOrdersByIdRepairOrderAndMechanicRepair(id, mechanic);

            } else {
                return "home";
            }
        }

        Car car = choosenOneRepairOrder.getCarRepair();
        System.out.println(car.getMark());
        model.addAttribute("choosen", choosenOneRepairOrder);
        model.addAttribute("choosenCar", car);
        model.addAttribute("addDamage", new Damage());
        model.addAttribute("part", new Part());
        return "orderDetails";
    }

    @GetMapping("/uploadHere")
    public String uploadTest(){
        return "upload";
    }
    @PostMapping("/uploadHere")
    public String testUpload( @RequestParam("file") MultipartFile file) throws Exception{


        String pathToFile = "src/main/resources/uploaded_files/" + file.getOriginalFilename();
        System.out.println(pathToFile + "path here");
        fileService.uploadFile(file);

        return "redirect:/";
    }

    //==============================
    @GetMapping("/img/{id}")
    public void getImage(@PathVariable("id") Long id, HttpServletResponse httpServletResponse){
        fileService.writeImageToResponse(id, httpServletResponse);
    }

    //==============================
    @PostMapping("/repairOrder/{id}")
    public String addText(@ModelAttribute("addDamage") @Valid Damage damage, BindingResult bindingResultDamage, @ModelAttribute("part")
    @Valid Part part, BindingResult bindingResultPart, @PathVariable("id") Long id
                         )  {

        if (bindingResultDamage.hasErrors() || bindingResultPart.hasErrors()) {
            System.out.println(bindingResultDamage.getFieldError());
            System.out.println(bindingResultPart.getFieldError());
            return "orderDetails";
        }
        System.out.println(damage.getDescription());


        Optional<RepairOrder> repairOrder = repairOrderRepository.findById(id);

        Set<Part> parts = new HashSet<>();
        String[] str = part.getName().split(",");
        for (String s : str) {
            Optional<Part> p = partRepository.findByNameAndQuantityGreaterThan(s, 0);
            p.ifPresent(parts::add);
            p.ifPresent(Part::changeQuantityAfterCarRepair);
        }

        Set<Damage> damages = new HashSet<>();
        damages.add(damage);

        if (repairOrder.isPresent()) {
            damage.setRepairOrder(repairOrder.get());
            damage.setPartDamages(parts);
            Part.setDamagedPart(damages);
            repairOrder.get().setStatus(OrderStatus.ZAKONCZONE.toString());
            repairOrder.get().setReturnDate(LocalDate.now());

        }
        partRepository.save(part);
        damageRepository.save(damage);
        partRepository.findAll().forEach(System.out::println);
        damageRepository.findAll().forEach(System.out::println);

        return "redirect:/";
    }

    @Transactional
    @PostMapping("/closeRepairOrder/{id}")
    public String closeRepairOrder(@PathVariable("id") Long id,  @RequestParam("file") MultipartFile file) throws Exception {

        Optional<RepairOrder> rr = repairOrderRepository.findById(id);
        String pathToFile = "src/main/resources/uploaded_files/" + file.getOriginalFilename();
        System.out.println(pathToFile + "path here");

        fileService.uploadFile(file);
        if (rr.isPresent()) {
            Long mechanicId = rr.get().getMechanicRepair().getId();
            mechanicRepo.changeIsFreeToTrue(mechanicId);
            repairOrderRepository.changeStatusToClosed(id);
            repairOrderRepository.changeEndDateToNow(id);
            return "redirect:/";
        }

        return "redirect:/error";
    }

    @GetMapping("/orderPart")
    public String orderPart(Model model) {


        model.addAttribute("partOrder", new Order());
        model.addAttribute("onlyPart", new Part());

        return "partsOrder";
    }

    @PostMapping("/orderPart")
    public String postOrderPart(@ModelAttribute("partOrder") @Valid Order order,
                                @ModelAttribute("onlyPart") @Valid Part part,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "partsOrder";
        }

        PartOrder partOrder = new PartOrder();

        partOrder.setPartOrderDate(LocalDate.now());
        partOrder.setPart(order);
        partOrder.setAddPart(part);

        partRepository.save(part);
        orderRepo.save(order);
        partOrderRepository.save(partOrder);

        return "redirect:/";

    }

}
