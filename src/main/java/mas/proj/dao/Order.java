package mas.proj.dao;

import lombok.Getter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ordering")
public class Order  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_number")
    @Getter
    private Long idOrder;

    @Column(name = "spec")
    @Getter

    private String specialization;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    private Set<Mechanic> mechanicOrders;

    @OneToMany(mappedBy = "part", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    private Set<PartOrder> partOrders;

    @Transient
    private Set<Order> orderExtent = new HashSet<>();

    public Order(){
        orderExtent.add(this);

    }
}
