package org.mastermind;

import java.util.*;

import static org.mastermind.GameUtils.*;

//maybe could split up into game config and game stats if using interfaces and composition?
public class GameState {
    private static final int NUM_PIECES = 4;
    private static final int LOW_NUM = 0;
    private static final int HIGH_NUM = 8;
    private static final int MAX_ATTEMPTS = 100;
    private String secretCode;
    private int attempts;
    private int round;
    private boolean codeFound;

    public GameState() {
        this.attempts = 10;
        this.round = 0;
        this.secretCode = randomCode();
        this.codeFound = false;
    }

    public GameState(String secretCode) {
        this.secretCode = secretCode;
    }

//    public static void argPrint()
//    {
//        System.out.printf("Try Again...Please enter arguments in any of the following formats:\n" +
//                "-c [CODE]\n" +
//                "-t [ATTEMPTS]\n" +
//                "-c [CODE] -t [ATTEMPTS]\n" +
//                "-t [ATTEMPTS] -c [CODE]     ...limited to only 4 integer digits]\n");
//    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setCodeFound(boolean codeFound) {
        this.codeFound = codeFound;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getRound() {
        return round;
    }

    public boolean getCodeFound() {
        return codeFound;
    }


    public static int getNumPieces() {
        return NUM_PIECES;
    }

    public static int getLowNum() {
        return LOW_NUM;
    }

    public static int getHighNum() {
        return HIGH_NUM;
    }

    public static int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }
}

