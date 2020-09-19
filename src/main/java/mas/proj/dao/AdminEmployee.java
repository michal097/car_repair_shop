package mas.proj.dao;


import lombok.Getter;
import lombok.Setter;
import mas.proj.enums.Type;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AdminEmployee extends Person {

    @Transient
    private Set<AdminEmployee> adminEmployeesExtent = new HashSet<>();

    public AdminEmployee(){
        super();
        setType(EnumSet.of(Type.EMPLOYEE, Type.ADMIN).toString());
        adminEmployeesExtent.add(this);
    }

    @OneToMany(mappedBy = "adminEmployeeRepair")
    @Getter@Setter
    private Set<RepairOrder> repairOrdersAdminEmployee;
}
