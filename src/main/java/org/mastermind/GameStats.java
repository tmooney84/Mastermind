package org.mastermind;

import java.util.Scanner;

import static org.mastermind.GameUtils.randomCode;

public class GameStats {
    private String secretCode;
    private int attempts = 10;
    private int currentRound;
    private boolean codeFound;
    private RoundData[] roundsData;
    private int points;

    public GameStats() {
        this.currentRound = 0;
        this.secretCode = randomCode();
        this.codeFound = false;
        this.roundsData = new RoundData[attempts];
        this.points = 0;
    }

    public GameStats(String secretCode) {
        this.currentRound = 0;
        this.secretCode = secretCode;
        this.codeFound = false;
        this.roundsData = new RoundData[attempts];
        this.points = 0;
    }

    public void setRoundData(RoundData currentData, int round) {
        this.roundsData[round] = currentData;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setCodeFound(boolean codeFound) {
        this.codeFound = codeFound;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public String getSecretCode() {
        return secretCode;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public boolean getCodeFound() {
        return codeFound;
    }

    public RoundData[] getRoundData() {
        return roundsData;
    }

    public RoundData getRoundData(int roundNumber) {
        return roundsData[roundNumber];
    }

    public int getPoints() {
        return points;
    }

    public void runRound(Scanner in) {
        String code = "";
        //***could also have a  RoundData Array to track the info of each round???
        int currentRound = this.getCurrentRound();
        RoundData currentRoundData = new RoundData();
        boolean goodCodeFlag = false;
        boolean codeFound = this.getCodeFound();

        System.out.println("---");
        System.out.println("Round " + currentRound);

        while (!goodCodeFlag) {
            System.out.print(">");
            code = in.nextLine();

            if (!GameUtils.checkUniqueCode(code)) {
                System.out.println("Wrong input!");
            } else {
                goodCodeFlag = true;
            }
        }

        if (GameUtils.checkSolution(this, code, currentRoundData)) {
            codeFound = true;
            this.setCodeFound(codeFound);
        } else {
            System.out.println("Well placed pieces: " + currentRoundData.getWellPlaced());
            System.out.println("Misplaced pieces: " + currentRoundData.getMisplaced());


            //Record data for db???
            //RoundData currentRound = new RoundData();

            this.setRoundData(currentRoundData, currentRound);
        }
    }

    public void setRoundStats(String num1, String num2, int currentRound) {
        int wellPlaced = Integer.parseInt(num1);
        int misplaced = Integer.parseInt(num2);

        RoundData current = new RoundData(wellPlaced, misplaced);
        //int currentRound = this.getCurrentRound();
        this.setRoundData(current, currentRound);
        return;
    }
}




