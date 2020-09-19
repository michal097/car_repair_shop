package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "parts")

public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long partId;

    @Column(name = "catalogPrice")
    @Getter@Setter
    private int catalogPrice;

    @Column(name = "quantity")
    @Getter@Setter
    private int quantity;

    @Column(name = "carMark")
    @Getter@Setter
    private String carMark;

    @Column(name = "name")
    @Getter@Setter
    private String name;

    @Transient
    private Set<Part> parts = new HashSet<>();

    public Part(){
        parts.add(this);
    }

    @OneToMany(mappedBy = "addPart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter@Setter
    private Set<PartOrder> partOrders;

    public List<Part> getPartExtent(){
        return new ArrayList<>(parts);
    }

    @ManyToMany(targetEntity = Damage.class)
    @Getter@Setter
    private static Set<Damage> damagedPart;

    public void changeQuantityAfterCarRepair(){
        this.quantity -=1;
    }


}
