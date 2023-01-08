package com.example.astraapi.util;

import com.example.astraapi.model.Page;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

@UtilityClass
public class PageUtils {
    public static <T, R> Page<R> mapPage(Page<T> page, Function<T, R> mapper) {
        if (page == null) {
            return new Page<>();
        }
        return page.map(mapper);
    }
}
