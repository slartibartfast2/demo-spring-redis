package ea.slartibarfast.demo.redis.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@RedisHash("user")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User {

    @NonNull
    @Id
    private Long id;
    @NonNull
    @Indexed
    private String externalId;
    @NonNull
    private String name;
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction trx) {
        this.transactions.add(trx);
    }
}
