package ea.slartibarfast.demo.redis.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RedisHash("transaction")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Transaction {

    @NonNull
    @Indexed
    private Long id;
    @NonNull
    private int amount;
    @Indexed
    @NonNull
    private TransactionType type;
    @NonNull
    private Date createdDate;
    private List<Item> items = new ArrayList<>();
    @NonNull
    @TimeToLive
    private Long timeout;

    public void addItem(Item item) {
        this.items.add(item);
    }
}
