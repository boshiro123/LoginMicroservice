package microservice.login.mapper;

import microservice.login.dto.request.CreateUserRequest;
import microservice.login.dto.request.UpdateUserRequest;
import microservice.login.dto.response.UserResponse;
import microservice.login.entity.Role;
import microservice.login.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(target = "role", source = "role")
    UserResponse toUserResponse(User user);

    User toUser(UserResponse userResponse);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "role", source = "role")
    })
    User toUser(CreateUserRequest createUserRequest, Role role);

    @Mapping(target = "id", ignore = true)
    User toUser(UpdateUserRequest updateUserRequest);

    @Mapping(target = "id", ignore = true)
    void updateUserFromRequest(UpdateUserRequest updateUserRequest, @MappingTarget User user);

}
