package com.ap.homebanking.utils;

public class CardUtils {

    public static int getRandomNumberCvv (int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getRandomNumberCard (){
        Integer numberCard, max=9999, min = 1000;
        String numberCardString="";
        for (int i=0; i<4 ;i++){
            numberCard = (int)((Math.random() * (max - min)) + min);
            if (i ==0 ){
                numberCardString =  numberCard.toString();
            }else {
                numberCardString += ("-" + numberCard.toString());
            }
        }
        return numberCardString;
    }
}
