package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id")
    @Getter
    private Long id;

    @Column(name = "mark")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Car.mark.NotEmpty}")
    private String mark;

    @Column(name = "model")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Car.model.NotEmpty}")
    private String model;

    @Column(name = "rejestration")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Car.rejestration.NotEmpty}")
    private String rejestration;

    @Column(name = "vin")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Car.VIN.NotEmpty}")
    private String VIN;

    @Column(name = "year")
    @Getter@Setter
    @Min(message = "{mas.proj.dao.Car.mark.Min}", value = 1900)
    @Max(message = "{mas.proj.dao.Car.mark.Max}", value = 2020)
    private int yearOfProd;


    @ManyToOne()
    @JoinColumn(
            name = "person_id"

    )
    @Getter@Setter
    private Person person;

    @OneToMany(mappedBy = "carRepair")
    @Getter@Setter
    private Set<RepairOrder> repairOrders;


    public Car(){
        setPerson(getPerson());
    }


}
