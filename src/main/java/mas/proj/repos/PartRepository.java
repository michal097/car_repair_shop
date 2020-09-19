package mas.proj.repos;

import mas.proj.dao.Part;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends CrudRepository<Part, Long> {
   Optional<Part> findByNameAndQuantityGreaterThan(String name, Integer integer);
   @NotNull List<Part> findAll();
}
