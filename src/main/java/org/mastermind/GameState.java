package org.mastermind;

import java.util.*;

import static org.mastermind.GameUtils.*;

public class GameState {
    private static final int NUM_PIECES = 4;
    private String secretCode;
    private int attempts;
    private int round;


    public GameState()
    {
       this.attempts = 10;
       this.round = 0;
       this.secretCode = randomCode();
    }

    public GameState(String secretCode)
    {
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

    public String getSecretCode(){
        return secretCode;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public int getRound()
    {
        return round;
    }
    public static int getNumPieces()
    {
        return NUM_PIECES;
    }
}
