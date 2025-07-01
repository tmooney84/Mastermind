package org.mastermind;

public class SinglePlayer implements GameState {
    private GameStats stats;

    public SinglePlayer(GameStats stats) {
        this.stats = stats;
    }

    @Override
    public void gameRun() {
        //use config here
        RoundData[] roundsData = new RoundData[stats.getAttempts()];
    }
}
