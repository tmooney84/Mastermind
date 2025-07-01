package org.mastermind;

import java.util.*;

public class GameUtils {
    //Single Player, Multiplayer???
    public static int numPlayers() {
        Scanner in = new Scanner(System.in);

        boolean singlePlayerFlag = false;
        boolean multiPlayerFlag = false;
        do {
            System.out.println("Enter 's' for single player, 'm' for multiplayer or 'c' to adjust configuration");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("s")) {
                singlePlayerFlag = true;
            } else if (input.equalsIgnoreCase("m")) {
                multiPlayerFlag = true;
            } else if (input.equalsIgnoreCase("c")) {
                Config.runConfig();
            } else {
                System.out.println("Incorrect input. Please enter 's' or 'm' to continue");
            }
        } while (!singlePlayerFlag && !multiPlayerFlag);

        in.close();

        if (singlePlayerFlag) {
            return 1;
        } else if (multiPlayerFlag) {
            return 2;
        } else {
            System.out.println("Error determining number of players. Please try again.");
        }

        return -1;
    }

    public static String chooseCode() {
        boolean codeChosen = false;
        String secretCode = "";
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter 'r' for random code or 'c' for custom code");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("r")) {
                codeChosen = true;
                secretCode = GameUtils.randomCode();
            } else if (input.equalsIgnoreCase("c")) {
                codeChosen = true;
                boolean validCode = false;
                do {
                    System.out.println("Please enter 4-digit code with unique values between "
                            + Config.getLowNum() + " and " + Config.getHighNum() + " ie: 4537");
                    secretCode = in.nextLine();
                    if (GameUtils.checkUniqueCode(secretCode)) {
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

        return secretCode;
    }


    //unique digits compose number
    public static boolean checkUniqueCode(String code) {
        int numPieces = Config.getNumPieces();
        if (code.length() != numPieces) {
            return false;
        }

        int low = Config.getLowNum();
        int high = Config.getHighNum();

        Set<Character> seenDigits = new HashSet<>();
        int length = code.length();
        for (int i = 0; i < length; i++) {
            char c = code.charAt(i);

            if (c < (char) (low + '0') || c > (char) (high + '0')) {
                return false;
            }

            if (seenDigits.contains(c)) {
                return false;
            }
            seenDigits.add(c);
        }
        return true;
    }

    public static String randomCode() {
        ArrayList<Integer> digits = new ArrayList<>();
        int NUM_PIECES = Config.getNumPieces();
        int low = Config.getLowNum();
        int high = Config.getHighNum();
        for (int i = low; i <= high; i++) {
            digits.add(i);
        }

        //same seed for replaying game state
        Collections.shuffle(digits);

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < NUM_PIECES; i++) {
            code.append(digits.get(i));
        }

        return code.toString();
    }

    public static boolean isValidNum(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

//    //Validating that a code could potentially work
//    public static boolean isValidPotCode(String strNum, int low, int high) {
//        if (strNum == null || strNum.trim().isEmpty()) {
//            return false;
//        }
//
//        if (!checkUniqueNumbers(strNum)) {
//            return false;
//        }
//
//        int numLen = strNum.length();
//
//        for (int i = 0; i < numLen; i++) {
//            char c = strNum.charAt(i);
//            if (c < (char) (low + '0') || c > (char) (high + '0')) {
//                return false;
//            }
//        }
//        return true;
//    }


    // TODO GameUtils.checkSolution() method
    public static boolean checkSolution(GameStats currentGameStats, String numString, RoundData data) {
        String secretCode = currentGameStats.getSecretCode();
        int sLength = secretCode.length();

        Set<Character> secretSet = new HashSet<>();
        for (char c : secretCode.toCharArray()) {
            secretSet.add(c);
        }

        int nLength = numString.length();
        boolean matchedCode = true;

        if (!checkUniqueCode(numString) || sLength != nLength) {
            matchedCode = false;
        } else {
            int len = numString.length();
            int misplaced = 0;
            int wellPlaced = 0;

            for (int i = 0; i < len; i++) {
                if (numString.charAt(i) != secretCode.charAt(i)) {
                    matchedCode = false;

                    if (secretSet.contains(numString.charAt(i))) {
                        misplaced++;
                    }
                } else if (numString.charAt(i) == secretCode.charAt(i)) {
                    wellPlaced++;
                }
            }
            data.setMisplaced(misplaced);
            data.setWellPlaced(wellPlaced);
        }
        return matchedCode;
    }

    // GameUtils.matchedPieces() method >>> use hashset again
}
