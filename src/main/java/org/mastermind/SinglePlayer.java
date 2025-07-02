package org.mastermind;

import java.util.Scanner;

public class SinglePlayer implements GameState {
    private GameStats stats;
    private Scanner in;

    public SinglePlayer(GameStats stats, Scanner in) {
        this.stats = stats;
        this.in = in;
    }

    @Override
    public void gameRun() {
        int totalRounds = stats.getAttempts();

        //Save rounds??? for db???

        //TODO Could this a single method that could represent the round for a single player?
        //could the roundsData object just be passed into the method??????????

        RoundData[] roundsData = new RoundData[totalRounds];

        int round = stats.getRound();
        int attempts = stats.getAttempts();
        boolean codeFound = stats.getCodeFound();

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

                //Record data for db???
                roundsData[round].setWellPlaced(currentRoundData.getWellPlaced());
                roundsData[round].setMisplaced(currentRoundData.getMisplaced());
                stats.setRound(round++);
            }
        } while (!codeFound && round < attempts);

        if (codeFound) {
            System.out.println("Congrats! You win!");
        } else if (!codeFound) {
            System.out.println("Sorry, you lose");
        }

    }
}
