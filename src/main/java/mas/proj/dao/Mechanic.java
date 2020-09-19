package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import mas.proj.enums.Type;
import javax.persistence.*;
import java.util.*;

@Entity
public class Mechanic extends Person {

    enum spec{
        OPEL,
        AUDI
    };

    @Getter@Setter
    @Column(name = "isFree")
    private boolean free;


    @Column(name = "spec")
    @Getter
    private String specialization;

    @Transient
    private static Set<Mechanic> mechanicsExtent = new HashSet<>();

    @org.jetbrains.annotations.Contract(" -> new")
    public static HashSet<Mechanic> getMechanics(){
        return new HashSet<>(mechanicsExtent);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_number"

    )
    @Getter@Setter
    private Order order;

    public Mechanic(){
        super();
        setFree(true);
        mechanicsExtent.add(this);
    }

    public Mechanic(Set<spec>specs) {
        this.setSpecialization(specs.toString());
        mechanicsExtent.add(this);
    }

    public void setSpecialization(String specialization){

        this.specialization = specialization;
    }

    public Mechanic addMechanic(String specialization ){
        if(specialization.isEmpty()){
            throw new IllegalArgumentException("Musi byc przynajmniej jedna sepecjalizacja");
        }
        List<String> spec = Arrays.asList(specialization.split(","));


        this.setSpecialization(spec.toString());
        this.setType(EnumSet.of(Type.EMPLOYEE, Type.MECHANIC).toString());
        mechanicsExtent.add(this);
        return this;
    }

    public boolean checkFree(){
        return this.free;
    }
    public boolean changeFreeStatus(){
        return !this.free;
    }

    @OneToMany(mappedBy = "mechanicRepair")
    @Setter@Getter
    private Set<RepairOrder> mechanicRepairOrder;


    @Override
public String toString(){
    return "is freee" + this.isFree();
}
}
