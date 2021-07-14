package ea.slartibarfast.demo.redis.repository;

import ea.slartibarfast.demo.redis.model.Transaction;
import ea.slartibarfast.demo.redis.model.TransactionType;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByItemsName(String itemName);
}
