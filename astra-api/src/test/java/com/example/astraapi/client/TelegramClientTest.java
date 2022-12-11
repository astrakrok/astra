package com.example.astraapi.client;

import com.example.astraapi.client.impl.TelegramClientImpl;
import com.example.astraapi.config.TelegramProperties;
import com.example.astraapi.dto.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TelegramClientTest {
  @InjectMocks
  private TelegramClientImpl telegramClient;
  @Mock
  private WebClient webClient;
  @Mock
  private TelegramProperties telegramProperties;

  @BeforeEach
  void beforeEach() {
    Mockito.lenient().when(telegramProperties.getApiToken()).thenReturn("telegramApiToken");
    Mockito.lenient().when(telegramProperties.getChatId()).thenReturn("telegramChatId");
  }

  @Test
  void shouldSendRegularMessage() {
    String[] url = new String[1];

    Mono<ResponseEntity<Void>> mono = Mockito.mock(Mono.class);
    Mockito.doAnswer(invocation -> null).when(mono).block();

    WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
    Mockito.when(responseSpec.toBodilessEntity()).thenReturn(mono);

    WebClient.RequestBodyUriSpec requestBodySpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
    Mockito.when(requestBodySpec.uri(ArgumentMatchers.<Function<UriBuilder, URI>>any())).thenAnswer(invocation -> {
      Function<UriBuilder, URI> function = invocation.getArgument(0);
      URI value = function.apply(new DefaultUriBuilderFactory().uriString("https://example.com"));
      url[0] = value.toString();
      return requestBodySpec;
    });
    Mockito.when(requestBodySpec.retrieve()).thenReturn(responseSpec);

    Mockito.when(webClient.post()).thenReturn(requestBodySpec);

    telegramClient.sendRegularMessage(new MessageDto("title", "text", null));

    assertEquals("https://example.com/bottelegramApiToken/sendMessage?parse_mode=markdown&text=*title*%0Atext&chat_id=telegramChatId", url[0]);
  }

  @Test
  void shouldSendRegularMessageAndIgnoreNullText() {
    String[] url = new String[1];

    Mono<ResponseEntity<Void>> mono = Mockito.mock(Mono.class);
    Mockito.doAnswer(invocation -> null).when(mono).block();

    WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
    Mockito.when(responseSpec.toBodilessEntity()).thenReturn(mono);

    WebClient.RequestBodyUriSpec requestBodySpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
    Mockito.when(requestBodySpec.uri(ArgumentMatchers.<Function<UriBuilder, URI>>any())).thenAnswer(invocation -> {
      Function<UriBuilder, URI> function = invocation.getArgument(0);
      URI value = function.apply(new DefaultUriBuilderFactory().uriString("https://example.com"));
      url[0] = value.toString();
      return requestBodySpec;
    });
    Mockito.when(requestBodySpec.retrieve()).thenReturn(responseSpec);

    Mockito.when(webClient.post()).thenReturn(requestBodySpec);

    telegramClient.sendRegularMessage(new MessageDto("title", null, null));

    assertEquals("https://example.com/bottelegramApiToken/sendMessage?parse_mode=markdown&text=*title*&chat_id=telegramChatId", url[0]);
  }

  @Test
  void shouldSendRegularMessageAndIgnoreNullTitle() {
    String[] url = new String[1];

    Mono<ResponseEntity<Void>> mono = Mockito.mock(Mono.class);
    Mockito.doAnswer(invocation -> null).when(mono).block();

    WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
    Mockito.when(responseSpec.toBodilessEntity()).thenReturn(mono);

    WebClient.RequestBodyUriSpec requestBodySpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
    Mockito.when(requestBodySpec.uri(ArgumentMatchers.<Function<UriBuilder, URI>>any())).thenAnswer(invocation -> {
      Function<UriBuilder, URI> function = invocation.getArgument(0);
      URI value = function.apply(new DefaultUriBuilderFactory().uriString("https://example.com"));
      url[0] = value.toString();
      return requestBodySpec;
    });
    Mockito.when(requestBodySpec.retrieve()).thenReturn(responseSpec);

    Mockito.when(webClient.post()).thenReturn(requestBodySpec);

    telegramClient.sendRegularMessage(new MessageDto(null, "text", null));

    assertEquals("https://example.com/bottelegramApiToken/sendMessage?parse_mode=markdown&text=text&chat_id=telegramChatId", url[0]);
  }

  @Test
  void shouldThrowExceptionOnNullTextValues() {
    String[] url = new String[1];

    WebClient.RequestBodyUriSpec requestBodySpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
    Mockito.when(requestBodySpec.uri(ArgumentMatchers.<Function<UriBuilder, URI>>any())).thenAnswer(invocation -> {
      Function<UriBuilder, URI> function = invocation.getArgument(0);
      URI value = function.apply(new DefaultUriBuilderFactory().uriString("https://example.com"));
      url[0] = value.toString();
      return requestBodySpec;
    });

    Mockito.when(webClient.post()).thenReturn(requestBodySpec);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> telegramClient.sendRegularMessage(new MessageDto(null, null, null)));

    assertEquals("Invalid message", exception.getMessage());
  }

  @Test
  void shouldSendPhotoMessage() {
    String[] url = new String[1];

    Mono<ResponseEntity<Void>> mono = Mockito.mock(Mono.class);
    Mockito.doAnswer(invocation -> null).when(mono).block();

    WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
    Mockito.when(responseSpec.toBodilessEntity()).thenReturn(mono);

    WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
    Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

    WebClient.RequestBodyUriSpec requestBodySpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
    Mockito.when(requestBodySpec.uri(ArgumentMatchers.<Function<UriBuilder, URI>>any())).thenAnswer(invocation -> {
      Function<UriBuilder, URI> function = invocation.getArgument(0);
      URI value = function.apply(new DefaultUriBuilderFactory().uriString("https://example.com"));
      url[0] = value.toString();
      return requestBodySpec;
    });
    Mockito.when(requestBodySpec.contentType(ArgumentMatchers.any())).thenReturn(requestBodySpec);
    Mockito.when(requestBodySpec.body(ArgumentMatchers.any())).thenReturn(requestHeadersSpec);

    Mockito.when(webClient.post()).thenReturn(requestBodySpec);

    MessageDto messageDto = new MessageDto("title", "text", mockFile());
    telegramClient.sendPhotoMessage(messageDto);

    assertEquals("https://example.com/bottelegramApiToken/sendPhoto?parse_mode=markdown&caption=*title*%0Atext&chat_id=telegramChatId", url[0]);
  }

  @Test
  void shouldSendPhotoMessageAndIgnoreNullTextValues() {
    String[] url = new String[1];

    Mono<ResponseEntity<Void>> mono = Mockito.mock(Mono.class);
    Mockito.doAnswer(invocation -> null).when(mono).block();

    WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
    Mockito.when(responseSpec.toBodilessEntity()).thenReturn(mono);

    WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
    Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

    WebClient.RequestBodyUriSpec requestBodySpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
    Mockito.when(requestBodySpec.uri(ArgumentMatchers.<Function<UriBuilder, URI>>any())).thenAnswer(invocation -> {
      Function<UriBuilder, URI> function = invocation.getArgument(0);
      URI value = function.apply(new DefaultUriBuilderFactory().uriString("https://example.com"));
      url[0] = value.toString();
      return requestBodySpec;
    });
    Mockito.when(requestBodySpec.contentType(ArgumentMatchers.any())).thenReturn(requestBodySpec);
    Mockito.when(requestBodySpec.body(ArgumentMatchers.any())).thenReturn(requestHeadersSpec);

    Mockito.when(webClient.post()).thenReturn(requestBodySpec);

    MessageDto messageDto = new MessageDto(null, null, mockFile());
    telegramClient.sendPhotoMessage(messageDto);

    assertEquals("https://example.com/bottelegramApiToken/sendPhoto?parse_mode=markdown&caption=&chat_id=telegramChatId", url[0]);
  }

  private MultipartFile mockFile() {
    MultipartFile file = Mockito.mock(MultipartFile.class);
    Mockito.when(file.getResource()).thenReturn(new InputStreamResource(new ByteArrayInputStream("".getBytes())));
    return file;
  }
}
