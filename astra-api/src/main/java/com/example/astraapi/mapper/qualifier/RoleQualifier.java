package com.example.astraapi.mapper.qualifier;

import com.example.astraapi.entity.RoleEntity;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class RoleQualifier {
  public static final String ROLE_ENTITY_TO_STRING = "roleEntityToString";

  @Named(ROLE_ENTITY_TO_STRING)
  public String mapRoleEntityToString(RoleEntity entity) {
    return entity == null ? null : entity.getTitle();
  }
}
