package mas.proj.repos;
import mas.proj.dao.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepo extends CrudRepository<Person, Long> {
    List<Person> findAll();
    Optional<Person> findFirstByEmailAndPassword(String email, String password);
    Optional<Person> findByEmail(String email);


}
