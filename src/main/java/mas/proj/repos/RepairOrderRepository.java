package mas.proj.repos;

import mas.proj.dao.Car;
import mas.proj.dao.Mechanic;
import mas.proj.dao.RepairOrder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RepairOrderRepository extends CrudRepository<RepairOrder, Long> {

    RepairOrder findByCarRepair(Car car);
    List<RepairOrder> findRepairOrdersByMechanicRepair(Mechanic mechanicRepair);
    RepairOrder findRepairOrdersByIdRepairOrderAndMechanicRepair(Long id, Mechanic mechanic);

    @Modifying(clearAutomatically = true)
    @Query("update RepairOrder m set m.status = 'ZAKONCZONE' where m.idRepairOrder = :id")
    void changeStatusToClosed(@Param("id")Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update  RepairOrder  rep set rep.returnDate = current_date where rep.idRepairOrder = :id")
    void changeEndDateToNow(@Param("id")Long id);


   Optional<RepairOrder> findRepairOrdersByIdRepairOrder(Long id);

   RepairOrder findByIdRepairOrder(Long id);

}
