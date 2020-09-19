package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "part_order")
public class PartOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parts_id")
    @Getter
        private Long partOrderId;

    @Column(name = "order_date")
    @Getter@Setter
    private LocalDate partOrderDate;

    public PartOrder(){
        this.setPartOrderDate(LocalDate.now());
    }

    @Transient
    private Set<PartOrder> partOrders = new HashSet<>();

    @ManyToOne
    @JoinColumn(
            name =  "orderPartId"
    )
    @Getter@Setter
    private Order part;

    @ManyToOne()
    @JoinColumn(
            name =  "partOrderId"

    )
    @Getter@Setter
    private Part addPart;

    public Set<PartOrder> getPartOrders() {
        return new HashSet<>(partOrders);
    }
}
