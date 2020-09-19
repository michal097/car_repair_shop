package mas.proj.repos;

import mas.proj.dao.ExtraOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraOrderRepository extends CrudRepository<ExtraOrder, Long> {
}
