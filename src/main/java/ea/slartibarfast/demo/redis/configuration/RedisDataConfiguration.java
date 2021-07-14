package ea.slartibarfast.demo.redis.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisDataConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);

        SocketOptions socketOptions = SocketOptions.builder()
                                                   .connectTimeout(Duration.ofMillis(2000))
                                                   .build();
        ClientOptions clientOptions = ClientOptions.builder()
                                                   .socketOptions(socketOptions)
                                                   .cancelCommandsOnReconnectFailure(true)
                                                   .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                                                   .build();
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                                                                                          .commandTimeout(Duration.ofMillis(2000))
                                                                                          .clientOptions(clientOptions)
                                                                                          .build();


        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        return lettuceConnectionFactory;
    }

    @Bean(name = "redisUserTemplate")
    public RedisTemplate<String, String> redisTemplateUser(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
