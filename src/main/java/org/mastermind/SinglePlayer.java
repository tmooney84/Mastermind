package org.mastermind;

import java.util.Scanner;

public class SinglePlayer {
    private GameStats game;
    private Scanner in;

    public SinglePlayer(GameStats game, Scanner in) {
        this.game = game;
        this.in = in;
    }
    //Save rounds??? for db???

    //TODO Could this a single method that could represent the round for a single player?
    //could the roundsData object just be passed into the method??????????

    public void gameRun(GameStats game) {
        boolean codeFound = game.getCodeFound();
        int round = game.getCurrentRound();
        int attempts = game.getAttempts();
        System.out.println("Will you find the secret code?");
        System.out.println("Please enter 4-digit code with unique values between "
                + Config.getLowNum() + " and " + Config.getHighNum());

        do {
            game.runRound(in);
            codeFound = game.getCodeFound();
            round = game.getCurrentRound();
        } while (!codeFound && round < attempts);

        if (codeFound) {
            System.out.println("Congrats! You win!");
        } else if (!codeFound) {
            System.out.println("Sorry, you lose");
        }
    }
}

//************************************************//
//        public void runRound (GameStats game){
//            String code = "";
//            //***could also have a  RoundData Array to track the info of each round???
//            int currentRound = game.getCurrentRound();
//            RoundData currentRoundData = game.getRoundData(currentRound);
//            boolean goodCodeFlag = false;
//            boolean codeFound = game.getCodeFound();
//
//            System.out.println("---");
//            System.out.println("Round " + currentRound);
//
//            while (!goodCodeFlag) {
//                System.out.print(">");
//                code = in.nextLine();
//
//                if (!GameUtils.checkUniqueCode(code)) {
//                    System.out.println("Wrong input!");
//                } else {
//                    goodCodeFlag = true;
//                }
//            }
//
//            if (GameUtils.checkSolution(game, code, currentRoundData)) {
//                codeFound = true;
//                game.setCodeFound(codeFound);
//            } else {
//                System.out.println("Well placed pieces: " + currentRoundData.getWellPlaced());
//                System.out.println("Misplaced pieces: " + currentRoundData.getMisplaced());
//
//
//                //Record data for db???
//                //RoundData currentRound = new RoundData();
//
//                game.setRoundData(currentRoundData, currentRound);
//
//                game.setCurrentRound(currentRound++);
//            }
//        }
//    }
//************************************************//

//        do {
//            String code = "";
//            //***could also have a  RoundData Array to track the info of each round???
//            RoundData currentRoundData = new RoundData();
//            boolean goodCodeFlag = false;
//
//            System.out.println("---");
//            System.out.println("Round " + round);
//
//            while (!goodCodeFlag) {
//                System.out.print(">");
//                code = in.nextLine();
//
//                if (!GameUtils.checkUniqueCode(code)) {
//                    System.out.println("Wrong input!");
//                } else {
//                    goodCodeFlag = true;
//                }
//            }
//
//            if (GameUtils.checkSolution(game, code, currentRoundData)) {
//                game.setCodeFound(true);
//                codeFound = true;
//                game.setCodeFound(codeFound);
//            } else {
//                System.out.println("Well placed pieces: " + currentRoundData.getWellPlaced());
//                System.out.println("Misplaced pieces: " + currentRoundData.getMisplaced());
//
//                //Record data for db???
//                RoundData currentRound = new RoundData();
//                currentRound.setWellPlaced(currentRound.getWellPlaced());
//                currentRound.setMisplaced(currentRoundData.getMisplaced());
//
//                game.setRoundsData(currentRound, round);
//                game.setCurrentRound(round++);
//            }
//        } while (!codeFound && round < attempts);
//
//        if (codeFound) {
//            System.out.println("Congrats! You win!");
//        } else if (!codeFound) {
//            System.out.println("Sorry, you lose");
//        }
//
//    }
//}
