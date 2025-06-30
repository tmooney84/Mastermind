package org.mastermind;

public class Multiplayer implements GameState {
    private GameStats stats;
    private Config config;

    //*****secret code needs to be randomized
    public Multiplayer(Config config, GameStats stats) {
        this.stats = stats;
        this.config = config;
    }

    @Override
    public void gameRun() {
        //use config here
        //***********do I need to create a matrix to capture data? Does 10 attempts mean
        RoundData[] roundsData = new RoundData[stats.getAttempts()];
    }
}
