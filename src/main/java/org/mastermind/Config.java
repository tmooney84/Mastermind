package org.mastermind;

public class Config {
    private static int numPieces = 4;
    private static int lowNum = 0;
    private static int highNum = 8;
    private static int maxAttempts = 100;  //*********Is this needed???

    public static void setNumPieces(int numPieces) {
        Config.numPieces = numPieces;
    }

    public static void setLowNum(int lowNum) {
        Config.lowNum = lowNum;
    }

    public static void setHighNum(int highNum) {
        Config.highNum = highNum;
    }

    public static void setMaxAttempts(int maxAttempts) {
        Config.maxAttempts = maxAttempts;
    }

    public static int getNumPieces() {
        return numPieces;
    }

    public static int getLowNum() {
        return lowNum;
    }

    public static int getHighNum() {
        return highNum;
    }

    public static int getMaxAttempts() {
        return maxAttempts;
    }
}
