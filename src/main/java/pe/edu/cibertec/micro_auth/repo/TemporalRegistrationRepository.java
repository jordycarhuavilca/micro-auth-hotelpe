package pe.edu.cibertec.micro_auth.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.micro_auth.entity.TemporalRegistration;

@Repository
public interface TemporalRegistrationRepository extends CrudRepository<TemporalRegistration,Integer> {
}
