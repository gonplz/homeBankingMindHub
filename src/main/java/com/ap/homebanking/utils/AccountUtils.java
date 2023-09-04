package com.ap.homebanking.utils;

public class AccountUtils {
    public static  int getRandomNumberAccount (int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);}
}
