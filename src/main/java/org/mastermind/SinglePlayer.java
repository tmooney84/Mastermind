package org.mastermind;

public class SinglePlayer implements GameState {
    private GameStats stats;
    private Config config;

    public SinglePlayer(Config config, GameStats stats) {
        this.stats = stats;
        this.config = config;
    }

    @Override
    public void gameRun() {
        //use config here
        RoundData[] roundsData = new RoundData[stats.getAttempts()];
    }
}
