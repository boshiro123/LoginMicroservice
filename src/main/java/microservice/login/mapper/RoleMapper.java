package microservice.login.mapper;

import microservice.login.dto.request.RoleRequest;
import microservice.login.dto.response.RoleResponse;
import microservice.login.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    RoleResponse toRoleResponse(Role role);

    Role toRole(RoleRequest roleRequest);

}
