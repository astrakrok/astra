package com.example.astraapi;

import java.util.Random;

public class TestUtils {
  private static final Random RANDOM = new Random();

  public static int nextInt(int min, int max) {
    return RANDOM.nextInt(max - min) + min;
  }
}
