package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import mas.proj.enums.Type;
import mas.proj.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "persons")
public class Person {

    @Autowired
    @Transient
    private PersonRepo personRepo;

    @Autowired
    @Transient
    private AdminEmplyeeRepo adminEmplyeeRepo;


    @Autowired
    @Transient
    private MechanicRepo mechanicRepo;

    @Autowired
    @Transient
    private RepairOrderRepository repairOrderRepository;
    @Autowired
    @Transient
    private CarRepo carRepo;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Getter
    private Long id;

    @Column(name = "adress")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Person.adress.NotEmpty}")
    private String adress;

    @Column(name = "name")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Person.name.NotEmpty}")
    private String name;

    @Column(name = "phoneNumber")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Person.phoneNumber.NotEmpty}")
    private String phoneNumber;

    @Column(name = "type")
    @Getter@Setter
    private String type;

    @Getter@Setter
    @Column(name = "allowProcessingPersonalData")
    private boolean allowProcessingPersonalData;

    @Getter@Setter
    @OneToMany(mappedBy = "person",
               cascade = CascadeType.REMOVE)
    private Set<Car> personCars;


    //=========================================================

    @Column(name = "email")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Person.email.NotEmpty}")
    private String email;

    @Column(name = "password")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Person.password.NotEmpty}")
    private String password;

    @Column(name = "role")
    @Getter@Setter
    private String role;

    @Column(name = "auth")
    @Getter@Setter
    private boolean isAuthenticated;


    //=========================================================



    public Person(Set<Type> types){
        this.setType(types.toString());
    }

    public Person(){

    }
    public boolean isClient(){
        return this.getType().contains(Type.CLIENT.toString());
    }
    public boolean isEmployee(){
        return this.getType().contains(Type.EMPLOYEE.toString());
    }

    public void addClient(Person person){

        person.setType(EnumSet.of(Type.CLIENT).toString());

    }

    public void addEmployee(Mechanic mechanic, String ... spec){
        mechanic.setType(Type.EMPLOYEE.toString());
        mechanic.setSpecialization(Arrays.toString(spec));
        mechanicRepo.save(mechanic);

    }

    public void addEmployee(AdminEmployee adminEmployee){
        adminEmployee.setType(Type.EMPLOYEE.toString());
        adminEmplyeeRepo.save(adminEmployee);

    }

    public Optional<RepairOrder> findRepairOrder(String VIN){
        Car car = carRepo.findByVIN(VIN);
        return Optional.ofNullable(repairOrderRepository.findByCarRepair(car));
    }

    public void addCar(Car car){
        this.getPersonCars().add(car);
    }
@Override
    public String toString(){
        return "emial" + email + ": password: " + password;
}
}
