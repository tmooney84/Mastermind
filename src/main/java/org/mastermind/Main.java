package org.mastermind;

import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean singlePlayerFlag = false;
        boolean multiPlayerFlag = false;
        GameStats currentGameStats = new GameStats();
        Scanner in = new Scanner(System.in);

        //*********************do I need low and high?
        int low = Config.getLowNum();
        int high = Config.getHighNum();
        boolean codeFound = currentGameStats.getCodeFound();

        //intro banner for command line version
        System.out.println("Welcome to Mastermind!");
        System.out.println();


        //Single Player, Multiplayer???
        do {
            System.out.println("Enter 's' for single player or 'm' for multiplayer");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("s")) {
                singlePlayerFlag = true;
            } else if (input.equalsIgnoreCase("m")) {
                multiPlayerFlag = true;
            } else {
                System.out.println("Incorrect input. Please enter 's' or 'm' to continue");
            }
        } while (!singlePlayerFlag && !multiPlayerFlag);


        //Default Settings, Custom Settings??? >>> could be a tab to click otherwise defaults
        //could change number of pieces, letters, etc.

        //Enter custom code?
        boolean codeChosen = false;
        do {
            System.out.println("Enter 'r' for random code or 'c' for custom code");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("r")) {
                codeChosen = true;
                String secretCode = GameUtils.randomCode();
                currentGameStats.setSecretCode(secretCode);
            } else if (input.equalsIgnoreCase("c")) {
                codeChosen = true;
                boolean validCode = false;
                do {
                    System.out.println("Please enter 4-digit code with unique values between "
                            + Config.getLowNum() + " and " + Config.getHighNum() + " ie: 4537");
                    String code = in.nextLine();
                    if (GameUtils.checkUniqueCode(code)) {
                        currentGameStats.setSecretCode(code);
                        System.out.println("Custom Secret Code Set.");
                        validCode = true;
                    } else {
                        System.out.println("Invalid code. Please enter 4-digit code with unique values between 0 and 8. ie: 4537");
                    }
                } while (!validCode);

            } else {
                System.out.println("Incorrect input. Please enter 'r' for random code or 'c' for custom code");
            }
        } while (!codeChosen);

        //How many attempts?
        boolean setAttemptsFlag = false;
        do {
            //???maybe describe with [10 Attempts] or different amount for multiplayer
            System.out.println("Enter how many attempts the player is given or 'd' for default.");
            String input = in.nextLine();
            //********** is a in.nextLine(); needed?
            if (input.equalsIgnoreCase("d")) {
                setAttemptsFlag = true;
            } else if (GameUtils.isValidNum(input)) {
                int num = Integer.parseInt(input);
                if (num <= Config.getMaxAttempts()) {
                    currentGameStats.setAttempts(num);
                    setAttemptsFlag = true;
                } else {
                    System.out.println("Input exceeds maximum allowable attempts.");
                }
            } else {
                System.out.println("Incorrect input.");
            }
        } while (!setAttemptsFlag);

//testing initial input
        String res = singlePlayerFlag ? "Single Player" : "Multiplayer";
        System.out.println("Player Number Status: " + res);  //single vs. multiplayer
        System.out.println("Secret code: " + currentGameStats.getSecretCode());
        System.out.println("Number of Attempts: " + currentGameStats.getAttempts());


        //main game logic
//TODO do-while loop that gets user input do essentially a REPL loop
        //if the player finds the code, they win, and the game stops
        //a misplaced piece is a piece that is present in the secret
        //code but is not in a good position

        int round = currentGameStats.getRound();
        int attempts = currentGameStats.getAttempts();

        System.out.println("Will you find the secret code?");
        System.out.println("Please enter 4-digit code with unique values between "
                + Config.getLowNum() + " and " + Config.getHighNum());

        do {
            String code = "";
            //***could also have a  RoundData Array to track the info of each round???
            RoundData currentRoundData = new RoundData();
            boolean goodCodeFlag = false;

            System.out.println("---");
            System.out.println("Round " + round);

            while (!goodCodeFlag) {
                System.out.print(">");
                code = in.nextLine();

                if (!GameUtils.checkUniqueCode(code)) {
                    System.out.println("Wrong input!");
                } else {
                    goodCodeFlag = true;
                }
            }

            if (GameUtils.checkSolution(currentGameStats, code, currentRoundData)) {
                currentGameStats.setCodeFound(true);
                codeFound = true;
                currentGameStats.setCodeFound(codeFound);
            } else {
                System.out.println("Well placed pieces: " + currentRoundData.getWellPlaced());
                System.out.println("Misplaced pieces: " + currentRoundData.getMisplaced());
                currentGameStats.setRound(round++);
            }
        } while (!codeFound && round < attempts);

        if (codeFound) {
            System.out.println("Congrats! You win!");
        } else if (!codeFound) {
            System.out.println("Sorry, you lose");
        }

        //closing scanner
        in.close();
    }
}

//---
//Round O
//>1234
//Well placed pieces: 0
//Misplaced pieces: 1
//---
//>tata
//Wrong Input!
//>4123
//Well placed pieces: 1
//Misplaced pieces: 2
//---
//Round 2
//>0123
//Congrats! You did it!
