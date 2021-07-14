package ea.slartibarfast.demo.redis.model.vo;

import ea.slartibarfast.demo.redis.model.Transaction;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserVo {

    private String externalId;
    private String name;
    private List<Transaction> transactions;
}
