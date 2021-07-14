package ea.slartibarfast.demo.redis.repository;

import ea.slartibarfast.demo.redis.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByExternalId(String externalId);
    List<User> findByTransactionsId(Long id);
}
