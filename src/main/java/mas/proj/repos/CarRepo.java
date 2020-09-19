package mas.proj.repos;

import mas.proj.dao.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends CrudRepository<Car, Long> {
    Car findByVIN(String VIN);
}
