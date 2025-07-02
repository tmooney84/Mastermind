package org.mastermind;

import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        do {
            GameUtils.clearScreen();
            System.out.println("Welcome to Mastermind!");
            System.out.println();


            int choice = GameUtils.chooseGamePlay(in);
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                GameUtils.clearScreen();   //??? check performance
                String code = GameUtils.chooseCode(in);
                GameStats stats = new GameStats(code);
                GameUtils.chooseNumAttempts(stats, in);
                SinglePlayer singlePlayer = new SinglePlayer(stats, in);
                singlePlayer.gameRun();
            } else if (choice == 2) {
                GameUtils.clearScreen();   //??? check performance
                GameStats stats = new GameStats();
                Multiplayer multiplayer = new Multiplayer(stats, in);
                System.out.println("Enter: 1) Start Server \n2) Join Game...");
                //if 1 - nameServer??? or have alias???
                // multiplayer.startServer();

                //if 2 - String otherServer >>>
                System.out.println("Enter server name or address ['localhost' for demo']");

                System.out.println("Enter player name: ");
                //enter 'leave' to disconnect >>> may need to be in the do-while loop in function
                //multiplayer.disconnect()

                //TODO start up thread and implement Runnable in Multiplayer
                //  >>> "Enter server name or address"
                // Multiplayer game = new Multiplayer;
            } else if (choice == -1) {
                System.out.println("Error determining game play.");
            }
        } while (true);

//testing initial input
//        String res = singlePlayerFlag ? "Single Player" : "Multiplayer";
//        System.out.println("Player Number Status: " + res);  //single vs. multiplayer
//        System.out.println("Secret code: " + stats.getSecretCode());
//        System.out.println("Number of Attempts: " + stats.getAttempts());

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
