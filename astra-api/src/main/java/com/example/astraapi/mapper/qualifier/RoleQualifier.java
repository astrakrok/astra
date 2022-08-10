package com.example.astraapi.mapper.qualifier;

import com.example.astraapi.entity.RoleEntity;
import com.example.astraapi.model.RoleAuthority;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class RoleQualifier {
  public static final String ROLE_ENTITY_TO_STRING = "roleEntityToString";
  public static final String ROLE_STRING_TO_AUTHORITY = "roleEntityToAuthority";

  @Named(ROLE_ENTITY_TO_STRING)
  public String mapRoleEntityToString(RoleEntity entity) {
    return entity.getTitle();
  }

  @Named(ROLE_STRING_TO_AUTHORITY)
  public RoleAuthority mapRoleEntityToAuthority(String roleName) {
    return new RoleAuthority(roleName);
  }
}
