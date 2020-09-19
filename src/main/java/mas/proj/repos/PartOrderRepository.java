package mas.proj.repos;

import mas.proj.dao.PartOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartOrderRepository extends CrudRepository<PartOrder, Long> {

}
