package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "extra_order")
public class ExtraOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long extraOrderId;

    @Transient
    @Getter
    private Set<ExtraOrder> extraOrdersExtent = new HashSet<>();


    @Column(name = "extra_order_name")
    @Getter@Setter
    private String extraOrderName;
    @Column(name = "std_selpr")
    @Getter@Setter
    private int standardPrice;

    public ExtraOrder(){
        extraOrdersExtent.add(this);
    }

    @ManyToMany(targetEntity = RepairOrder.class)
    @Getter@Setter
    private Set<RepairOrder> repairOrder;

}
