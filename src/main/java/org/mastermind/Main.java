package org.mastermind;

import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean configFlag = false;

//        //TODO Put into the Single Player and Multi Player instead of here
//        GameStats stats = new GameStats();
//        boolean codeFound = stats.getCodeFound();

        Scanner in = new Scanner(System.in);

        //intro banner for command line version
        System.out.println("Welcome to Mastermind!");
        System.out.println();

        int choice = GameUtils.numPlayers();
        if (choice == 1) {
            String code = GameUtils.chooseCode();
            GameStats stats = new GameStats(code);
            SinglePlayer singlePlayer = new SinglePlayer(stats);
            singlePlayer.gameRun();
        } else if (choice == 2) {
            System.out.println("Enter: 1) Start Server \n2) Join Game");
            //  >>> "Enter server name or address"
            // Multiplayer game = new Multiplayer;
        } else if (choice == -1) {
            System.out.println("Error determining if single player or multiplayer game.");
        }


        //How many attempts?
        //TODO put into method, potentially could go into GameUtils
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
                if (num <= Config.MAX_ATTEMPTS) {
                    GameStats =
                            stats.setAttempts(num);
                    setAttemptsFlag = true;

                } else {
                    System.out.println("Input exceeds maximum allowable attempts.");
                }
            } else {
                System.out.println("Incorrect input.");
            }
        } while (!setAttemptsFlag);

//testing initial input
//        String res = singlePlayerFlag ? "Single Player" : "Multiplayer";
//        System.out.println("Player Number Status: " + res);  //single vs. multiplayer
//        System.out.println("Secret code: " + stats.getSecretCode());
//        System.out.println("Number of Attempts: " + stats.getAttempts());


        //main game logic
//TODO do-while loop that gets user input do essentially a REPL loop
        //if the player finds the code, they win, and the game stops
        //a misplaced piece is a piece that is present in the secret
        //code but is not in a good position

        int round = stats.getRound();
        int attempts = stats.getAttempts();

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

            if (GameUtils.checkSolution(stats, code, currentRoundData)) {
                stats.setCodeFound(true);
                codeFound = true;
                stats.setCodeFound(codeFound);
            } else {
                System.out.println("Well placed pieces: " + currentRoundData.getWellPlaced());
                System.out.println("Misplaced pieces: " + currentRoundData.getMisplaced());
                stats.setRound(round++);
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
