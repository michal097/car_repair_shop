package mas.proj.dao;

        import lombok.Getter;
        import lombok.NonNull;
        import lombok.Setter;

        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.Transient;
        import java.util.HashSet;
        import java.util.Set;

@Entity
public class PartSpecyfic extends PartStock {

    @Transient
    @Column(name = "partSpec")
    private Set<PartSpecyfic> partSpecyfics = new HashSet<>();

    public Set<PartSpecyfic> getPartSpecyfics() {
        return new HashSet<>(partSpecyfics);
    }

    @Column(name = "purchase_reason")
    @Getter
    @Setter
    private String purchaseReason;

    public PartSpecyfic(){
        partSpecyfics.add(this);
    }
}
