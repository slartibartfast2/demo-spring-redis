package ea.slartibarfast.demo.redis.web;

import ea.slartibarfast.demo.redis.model.vo.UserVo;
import ea.slartibarfast.demo.redis.web.request.AddUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserControllerIT {

    @Autowired
    private TestRestTemplate template;

    @Container
    static final GenericContainer redis = new GenericContainer("redis:latest")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        int port = redis.getFirstMappedPort();
        registry.add("spring.redis.port", () -> port);
    }

    @Test
    void testAddAndFind() {
        assertTrue(redis.isRunning());
        AddUserRequest addUserRequest = new AddUserRequest("123456", "John Smith");
        UserVo userVo = template.postForObject("/users", addUserRequest, UserVo.class);
        assertNotNull(userVo);
        userVo = template.getForObject("/users/{id}", UserVo.class, "123456");
        assertNotNull(userVo);
        assertEquals("John Smith", userVo.getName());
        assertEquals(0, userVo.getTransactions().size());
    }
}