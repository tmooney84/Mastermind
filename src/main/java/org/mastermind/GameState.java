package org.mastermind;

import java.util.Random;

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

    public static String randomCode()
    {
        Random  random = new Random();
        StringBuilder code = new StringBuilder();
        //***needs to be distinct pieces
        for(int i = 0; i < NUM_PIECES; i++)
        {
           code.append(random.nextInt());
        }

        return code.toString();
    }

    public static void argPrint()
    {
        System.out.printf("Try Again...Please enter arguments in any of the following formats:\n" +
                "-c [CODE]\n" +
                "-t [ATTEMPTS]\n" +
                "-c [CODE] -t [ATTEMPTS]\n" +
                "-t [ATTEMPTS] -c [CODE]     ...limited to only 4 integer digits]\n");
    }

    public static boolean checkNum(String num)
    {
        for(int i = 0; i < NUM_PIECES; i++)
        {
            if(num.charAt(i) < '0' || num.charAt(i) > '9')
            {
                return false;
            }
        }

        return true;
    }

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


}
