package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import mas.proj.enums.PartEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PartStock extends Part {

    @Column(name = "price")
    private int purchasePrice;
    @Column(name = "status_part_stock")
    @Getter @Setter
    @NotEmpty
    private String status;

    @Transient
    private static Set<PartStock> partStocks = new HashSet<>();

    public Set<PartStock> getPartStocks() {
        return new HashSet<>(partStocks);
    }
    public void addPartToEvidence(){
        partStocks.add(this);
    }

    public void assignToDamage(Damage damage){  //PRZYPISUJE CZĘŚĆ DO KONKRETNEGO USZKODZENIA
            Part.getDamagedPart().add(damage);
    }
    public static void addToEvidence(Part part){
        partStocks.add((PartStock) part);
    }

    public PartStock(Set<PartEnum> partEnums){
        this.setStatus(partEnums.toString());
        partStocks.add(this);
    }
    public PartStock(){
        partStocks.add(this);
    }
}
