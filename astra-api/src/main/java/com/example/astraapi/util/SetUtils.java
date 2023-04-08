package com.example.astraapi.util;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class SetUtils {
    public static <T> Set<T> getDifference(Set<T> firstSet, Set<T> secondSet) {
        Set<T> difference = new HashSet<>(firstSet);
        difference.removeAll(secondSet);
        return difference;
    }
}
