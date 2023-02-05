package com.example.astraapi.util;

import com.example.astraapi.model.Page;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageUtilsTest {
    @Test
    void shouldReturnEmptyPage() {
        Page<Object> page = PageUtils.mapPage(null, null);

        assertEquals(0, page.getPageSize());
        assertEquals(0, page.getPagesCount());
        assertEquals(0, page.getRows());
        assertEquals(0, page.getItems().size());
    }

    @Test
    void shouldMapPage() {
        Object object = new Object() {
            @Override
            public int hashCode() {
                return 10;
            }
        };

        Function<Object, Object> mapper = (Function<Object, Object>) mock(Function.class);
        when(mapper.apply(object)).thenAnswer(invocation -> {
            Object argument = invocation.getArgument(0);

            return new Object() {
                @Override
                public int hashCode() {
                    return argument.hashCode() * 2;
                }
            };
        });

        Page<Object> page = new Page<>(List.of(object), 1, 1);

        Page<Object> returnedPage = PageUtils.mapPage(page, mapper);

        assertEquals(1, returnedPage.getPageSize());
        assertEquals(1, returnedPage.getPagesCount());
        assertEquals(1, returnedPage.getItems().size());
        assertEquals(20, returnedPage.getItems().get(0).hashCode());
    }

    @Test
    void shouldThrowExceptionWhenTryingToInstantiateClass() throws NoSuchMethodException {
        Constructor<PageUtils> constructor = PageUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }
}
