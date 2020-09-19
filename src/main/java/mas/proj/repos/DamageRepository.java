package mas.proj.repos;

import mas.proj.dao.Damage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageRepository extends CrudRepository<Damage, Long> {

}
