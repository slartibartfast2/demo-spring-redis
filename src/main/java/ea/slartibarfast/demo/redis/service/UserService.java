package ea.slartibarfast.demo.redis.service;

import ea.slartibarfast.demo.redis.model.User;
import ea.slartibarfast.demo.redis.model.converter.UserVoMapper;
import ea.slartibarfast.demo.redis.model.vo.UserVo;
import ea.slartibarfast.demo.redis.repository.UserRepository;
import ea.slartibarfast.demo.redis.web.converter.UserRequestMapper;
import ea.slartibarfast.demo.redis.web.request.AddUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserVoMapper userVoMapper;
    private final ExpirableLockRegistry lockRegistry;

    public UserVo findByExternalId(String externalId) {
        Optional<User> optUser = userRepository.findByExternalId(externalId);
        return optUser.map(userVoMapper::entityToVo).orElse(null);
    }

    public UserVo save(AddUserRequest addUserRequest) {
        Lock lock = acquireLock(addUserRequest.getExternalId());
        try {
            var user = userRepository.save(userRequestMapper.requestToEntity(addUserRequest));
            log.info("New user created, {} - {}", user.getId(), user.getName());
            return userVoMapper.entityToVo(user);
        } finally {
            lock.unlock();
        }
    }

    private Lock acquireLock(String requestId){
        final Lock lock = lockRegistry.obtain(requestId);
        boolean success = lock.tryLock();
        if (!success) {
            throw new CannotAcquireLockException("user locked by another client!");
        }
        return lock;
    }
}
