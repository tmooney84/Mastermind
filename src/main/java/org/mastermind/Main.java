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
                GameUtils.chooseNumAttempts(stats, in, false);
                SinglePlayer singlePlayer = new SinglePlayer(stats, in);
                singlePlayer.gameRun(stats);
            } else if (choice == 2) {
                GameUtils.clearScreen();   //??? check performance
                GameStats game = new GameStats();
                GameUtils.chooseNumAttempts(game, in, true);
                Multiplayer multiplayer = new Multiplayer(game, in);

                //multiplayer.prompt();
                boolean optionChosen = false;
                do {
                    System.out.println("Enter: 1) Start Server \n2) Join Game...");
                    int option = in.nextInt();
                    in.nextLine();

                    if (option == 1) {
                        System.out.println("Enter Server Name: ");
                        String serverName = in.nextLine();
                        Thread serverThread = multiplayer.startServer(serverName);
                        optionChosen = true;
                        try {
                            serverThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //if 2 - String otherServer >>>
                    else if (option == 2) {
                        System.out.println("Enter server name or address ['localhost' for demo']: ");
                        String hostName = in.nextLine();

                        System.out.println("Enter Client Name: ");
                        String clientName = in.nextLine();

                        Thread clientThread = multiplayer.joinGame(hostName, clientName);
                        optionChosen = true;
                        try {
                            clientThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //enter 'leave' to disconnect >>> may need to be in the do-while loop in function
                    //multiplayer.disconnect()

                    //TODO start up thread and implement Runnable in Multiplayer
                    //  >>> "Enter server name or address"
                    // Multiplayer game = new Multiplayer;
                } while (!optionChosen);
            } else if (choice == 3) {
                GameUtils.clearScreen();   //??? check performance
                Config.runConfig();

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
