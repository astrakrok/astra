package com.example.astraapi.mapper.qualifier;

import com.example.astraapi.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class RoleQualifierTest {
  @InjectMocks
  private RoleQualifier roleQualifier;

  @Test
  void shouldReturnNullWhenNull() {
    assertNull(roleQualifier.mapRoleEntityToString(null));
  }

  @Test
  void shouldReturnEntityRole() {
    assertEquals("user", roleQualifier.mapRoleEntityToString(new RoleEntity(1L, "user")));
  }
}
