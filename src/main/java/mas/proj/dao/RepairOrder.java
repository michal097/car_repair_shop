package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import scala.util.control.Exception;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "repair_order")
public class RepairOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long idRepairOrder;

    @Column(name = "data_oddania")
    @Getter@Setter
    private LocalDate returnDate;

    @Column(name = "data_rozpoczęcia")
    @Getter@Setter
    private LocalDate beginDate;

    @Column(name = "opis_ogolny")
    @Getter@Setter

    private String generalDescription;

    @Column(name = "powod_anulowania")
    @Getter@Setter
    private String cancelReason;

    @Column(name = "status")
    @Getter@Setter

    private String status;

    @ManyToOne()
    @JoinColumn(
            name = "car_id"

    )
    @Getter@Setter
    private Car carRepair;

    @ManyToOne
    @JoinColumn
            (
                    name = "adminRepair"
            )
    @Getter@Setter
    private AdminEmployee adminEmployeeRepair;

    @ManyToOne
    @JoinColumn
            (
                    name = "mechanicRepair"
            )
    @Getter@Setter
    private Mechanic mechanicRepair;

    @Lob
    @Getter@Setter
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    public RepairOrder(){
        setBeginDate(LocalDate.now());
    }

    @ManyToMany(targetEntity = ExtraOrder.class)
    @Getter@Setter
    private Set<ExtraOrder> extraOrders;

    @OneToMany(mappedBy = "repairOrder" )
    @Getter@Setter
    private Set<Damage> damagesRepair;


    @Override
    public String toString() {
        return "RepairOrder{" +
                "idRepairOrder=" + idRepairOrder +
                ", returnDate=" + returnDate +
                ", beginDate=" + beginDate +
                ", generalDescription='" + generalDescription + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                ", status='" + status + '\'' +
                ", carRepair=" + carRepair +
                ", adminEmployeeRepair=" + adminEmployeeRepair +
                ", mechanicRepair=" + mechanicRepair +
                ", extraOrders=" + extraOrders +
                ", damagesRepair=" + damagesRepair +
                '}';
    }

}
