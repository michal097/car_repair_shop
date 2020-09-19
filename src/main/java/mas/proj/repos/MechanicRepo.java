package mas.proj.repos;

        import mas.proj.dao.Mechanic;
        import org.springframework.data.jpa.repository.Modifying;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

        import java.util.List;
        import java.util.Optional;

@Repository
public interface MechanicRepo extends CrudRepository<Mechanic, Long> {

    @Override
    List<Mechanic> findAll();
    Optional<Mechanic> findFirstByFreeIs(boolean free);

    Optional<Mechanic> findById(Long id);

    @Modifying
    @Query("update Mechanic m set m.free = false where m.id = :id")
    void changeIsFreeToFalse(@Param("id")Long id);

    @Modifying
    @Query("update Mechanic m set m.free = true where m.id = :id")
    void changeIsFreeToTrue(@Param("id")Long id);

    List<Mechanic> findMechanicBySpecializationContains(String phrase);

    Optional<Mechanic> findMechanicByEmail(String email);
}
