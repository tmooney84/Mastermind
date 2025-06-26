package org.mastermind;

import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
            boolean allFound = false;
            boolean singlePlayerFlag = false;
            boolean multiPlayerFlag = false;

            GameState currentGame = new GameState();
            Scanner in = new Scanner(System.in);

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
        }while(!singlePlayerFlag && !multiPlayerFlag);
            //Default Settings, Custom Settings???

        do {
            System.out.println("Enter 'r' for random code or 'c' for custom code");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("s")) {
                singlePlayerFlag = true;
            } else if (input.equalsIgnoreCase("m")) {
                multiPlayerFlag = true;
            } else {
                System.out.println("Incorrect input. Please enter 's' or 'm' to continue");
            }
        }while(!singlePlayerFlag && !multiPlayerFlag);


        //Enter custom code?
            //How many attempts?


// parsing command line input
            if(args.length < 2 || args.length > 5)
            {
                GameState.argPrint();
                System.exit(1);
            }

            if(args.length == 3 && args[1].equals("-c"))
            {
//TODO make sure to abstract as much as possible
                char[] nChars = args[2].toCharArray();
                boolean isDigit = true;
                for(char nChar:nChars)
                {
                   if(!Character.isDigit(nChar))
                   {
                       isDigit = false;
                   }
                }
                System.out.println("Error... ")

                if(args[2])
                currentGame.setSecretCode(args[2])
            //need to convert string to CharacterArray
            Character.isDigit

            }

            else if(args.length == 3 && args[1].equals("-t"))
            {

            }

            else if((args.length == 5 && args[1].equals("-c")) && args[3].equals("-t"))
            {

            }

            else if((args.length == 5 && args[1].equals("-t")) && args[3].equals("-c"))
            {

            }

            //main game logic
//TODO do-while loop that gets user input do essentially a REPL loop

        //closing scanner
        in.close();
    }
}