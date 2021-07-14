package ea.slartibarfast.demo.redis.service;

import ea.slartibarfast.demo.redis.model.User;
import ea.slartibarfast.demo.redis.model.converter.UserVoMapper;
import ea.slartibarfast.demo.redis.model.vo.UserVo;
import ea.slartibarfast.demo.redis.repository.UserRepository;
import ea.slartibarfast.demo.redis.web.converter.UserRequestMapper;
import ea.slartibarfast.demo.redis.web.request.AddUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserVoMapper userVoMapper;

    public UserVo findByExternalId(String externalId) {
        Optional<User> optUser = userRepository.findByExternalId(externalId);
        return optUser.map(userVoMapper::entityToVo).orElse(null);
    }

    public UserVo save(AddUserRequest addUserRequest) {
        var user = userRepository.save(userRequestMapper.requestToEntity(addUserRequest));
        log.info("New user created, {} - {}", user.getId(), user.getName());
        return userVoMapper.entityToVo(user);
    }
}
