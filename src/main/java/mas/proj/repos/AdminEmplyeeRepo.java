package mas.proj.repos;

import mas.proj.dao.AdminEmployee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminEmplyeeRepo extends CrudRepository<AdminEmployee, Long> {
    @Override
    List<AdminEmployee> findAll();
}
