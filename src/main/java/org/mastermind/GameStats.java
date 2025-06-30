package org.mastermind;

import static org.mastermind.GameUtils.randomCode;

public class GameStats {
    private String secretCode;
    private int attempts = 10;
    private int round;
    private boolean codeFound;

    public GameStats() {
        this.round = 0;
        this.secretCode = randomCode();
        this.codeFound = false;
    }

    public GameStats(String secretCode) {
        this.round = 0;
        this.secretCode = secretCode;
        this.codeFound = false;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setCodeFound(boolean codeFound) {
        this.codeFound = codeFound;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getRound() {
        return round;
    }

    public boolean getCodeFound() {
        return codeFound;
    }
}
