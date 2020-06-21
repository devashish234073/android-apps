package com.devashish.ludo;

import java.util.Random;

public class Dice {
    private static Random rndm = new Random();
    public static int roll(){
        int value = rndm.nextInt(6) + 1;
        return value;
    }
}
