package ea.slartibarfast.demo.redis.repository;

import ea.slartibarfast.demo.redis.model.Transaction;
import ea.slartibarfast.demo.redis.model.TransactionType;
import ea.slartibarfast.demo.redis.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RedisUserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    @Order(1)
    void should_add_user() {
        User user = new User(1L, "1234567890", "test");
        user.addTransaction(new Transaction(1L, 1, TransactionType.SALE, now(), 60L));
        user.addTransaction(new Transaction(2L, 1, TransactionType.SALE, now(), 60L));
        user.addTransaction(new Transaction(3L, 2, TransactionType.REFUND, now(), 60L));
        user = repository.save(user);
        assertNotNull(user);
    }

    @Test
    @Order(2)
    void should_find_user_by_transaction_id() {
        List<User> users = repository.findByTransactionsId(3L);
        assertEquals(1, users.size());
        User user = users.get(0);
        assertNotNull(user);
        assertEquals(1, user.getId().longValue());
    }

    @Test
    @Order(3)
    void should_find_user_by_external_id() {
        Optional<User> optUser = repository.findByExternalId("1234567890");
        assertTrue(optUser.isPresent());
        User user = optUser.get();
        assertEquals(1, user.getId().longValue());
    }

    public Date now() {
        return new Date();
    }
}