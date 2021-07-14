package ea.slartibarfast.demo.redis.web.converter;

import ea.slartibarfast.demo.redis.model.User;
import ea.slartibarfast.demo.redis.web.request.AddUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    User requestToEntity(AddUserRequest addUserRequest);
}
