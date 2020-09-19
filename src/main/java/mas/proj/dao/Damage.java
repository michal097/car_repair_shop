package mas.proj.dao;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "damage")
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long damageId;

    @Column(name = "description")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Damage.description.NotEmpty}")
    private String description;

    @Column(name = "image")
    @Getter@Setter
    @NotEmpty(message = "{mas.proj.dao.Damage.image.NotEmpty}")
    private String image;


    @ManyToOne
    @JoinColumn
            (
                    name = "damageRepairId"
            )
    @Getter@Setter
    private RepairOrder repairOrder;

    @ManyToMany(targetEntity = Part.class)
    @Getter@Setter
    private Set<Part> partDamages;
}
