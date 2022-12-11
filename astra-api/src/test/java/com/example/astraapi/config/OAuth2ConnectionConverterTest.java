package com.example.astraapi.config;

import com.example.astraapi.meta.OAuth2Connection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OAuth2ConnectionConverterTest {
  @InjectMocks
  private OAuth2ConnectionConverter converter;

  @Test
  void shouldConvertValueToConnection() {
    OAuth2Connection google = converter.convert("google");

    assertEquals(OAuth2Connection.GOOGLE, google);
  }

  @Test
  void shouldConvertStrippedValueToConnection() {
    OAuth2Connection google = converter.convert("  google ");

    assertEquals(OAuth2Connection.GOOGLE, google);
  }

  @Test
  void shouldConvertValueToConnectionCaseInsensitive() {
    OAuth2Connection google = converter.convert("  GooGLe ");

    assertEquals(OAuth2Connection.GOOGLE, google);
  }

  @Test
  void shouldThrowExceptionOnInvalidValue() {
    assertThrows(IllegalArgumentException.class, () -> converter.convert("   goog le "));
  }
}
