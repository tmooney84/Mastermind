package org.mastermind;

import java.util.Scanner;

import static org.mastermind.GameUtils.isValidNum;

public class Config {
    private static int numPieces = 4;
    private static int lowNum = 0;
    private static int highNum = 8;
    public static final int MAX_ATTEMPTS = 100;  //*********Is this needed???

    public static void setNumPieces(int numPieces) {
        Config.numPieces = numPieces;
    }

    public static void setLowNum(int lowNum) {
        Config.lowNum = lowNum;
    }

    public static void setHighNum(int highNum) {
        Config.highNum = highNum;
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

    public static void runConfig() {
        Scanner in = new Scanner(System.in);
        String input = "";
        do {
            System.out.println("Enter 1. Number of Pieces \n2. Low Number(0-9) \n3. High Number(0-9)" +
                    "\n'm' for main menu.");
            input = in.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.println("Enter the Number of Pieces: ");
                    in.nextLine();
                    if (GameUtils.isValidNum(input)) {
                        numPieces = Integer.parseInt(input);
                    }
                    System.out.println("Number of pieces set to " + input);
                }
                case "2" -> {
                    System.out.println("Enter the Low Number: ");
                    in.nextLine();
                    if (GameUtils.isValidNum(input) && Integer.parseInt(input) < highNum) {
                        lowNum = Integer.parseInt(input);
                    }
                    System.out.println("Low Number set to " + input);
                }
                case "3" -> {
                    System.out.println("Enter the High Number: ");
                    in.nextLine();
                    if (GameUtils.isValidNum(input) && Integer.parseInt(input) > lowNum) {
                        highNum = Integer.parseInt(input);
                    }
                    System.out.println("High Number set to " + input);
                }
                default -> System.out.println("Incorrect input.");
            }
        } while (!input.equals("m"));
        in.close();
    }
}
