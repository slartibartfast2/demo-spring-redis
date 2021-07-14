package ea.slartibarfast.demo.redis.repository;

import ea.slartibarfast.demo.redis.model.Item;
import ea.slartibarfast.demo.redis.model.Transaction;
import ea.slartibarfast.demo.redis.model.TransactionType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RedisTransactionRepositoryTest {

    @Autowired
    TransactionRepository repository;

    @Test
    @Order(1)
    void should_add_transaction_with_one_second_timeout() {
        Transaction transaction = new Transaction(1L, 1000, TransactionType.JUST_LOOKING, new Date(), 1L);
        transaction = repository.save(transaction);
        assertNotNull(transaction);
    }

    @Test
    @Order(2)
    void should_not_find_transaction_by_type_when_transaction_is_expired() throws InterruptedException {
        Thread.sleep(1000L);

        List<Transaction> transactions = repository.findByType(TransactionType.JUST_LOOKING);
        assertEquals(0, transactions.size());
    }

    @Test
    @Order(3)
    void should_add_another_transaction_with_one_minute_timeout() {
        Transaction transaction = new Transaction(1L, 1000, TransactionType.SALE, new Date(), 60L);
        transaction.addItem(new Item(1L, "notebook", BigDecimal.TEN));
        transaction.addItem(new Item(2L, "mouse", BigDecimal.ONE));
        transaction.addItem(new Item(3L, "keyboard", BigDecimal.ONE));
        transaction = repository.save(transaction);
        assertNotNull(transaction);
    }

    @Test
    @Order(4)
    void should_find_transaction_by_type() {
        List<Transaction> transactions = repository.findByType(TransactionType.SALE);
        assertEquals(1, transactions.size());
    }

    @Test
    @Order(4)
    void should_find_transaction_by_item_name() {
        List<Transaction> transactions = repository.findByItemsName("notebook");
        assertEquals(1, transactions.size());
    }
}