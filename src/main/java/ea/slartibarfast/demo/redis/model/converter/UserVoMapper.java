package ea.slartibarfast.demo.redis.model.converter;

import ea.slartibarfast.demo.redis.model.User;
import ea.slartibarfast.demo.redis.model.vo.UserVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserVoMapper {

    UserVo entityToVo(User user);
}
