package com.example.astraapi.meta;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Regex {
  public static final String PASSWORD = "^(?=.*[a-zа-щьюяґєії])(?=.*[A-ZА-ЩЬЮЯҐЄІЇ])(?=.*\\d)[a-zа-щьюяґєіїA-ZА-ЩЬЮЯҐЄІЇ\\d\\w\\W]{8,}$";
}
