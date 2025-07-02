package org.mastermind;

import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Multiplayer implements GameState {
    private GameStats stats;
    private Config config;
    private Scanner in;

    //private Field gameField???
    private int gamePort;
    private volatile boolean keepListening;
    private volatile boolean keepPlaying;
    private volatile boolean startNewGame;
    private volatile boolean disconnecting;
    private Server server;
    private Thread serverThread;
    private Thread clientThread;

    //*****secret code needs to be randomized
    public Multiplayer(Config config, GameStats stats, Scanner in) {
        this.stats = stats;
        this.config = config;
        this.in = in;
    }

    @Override
    public void gameRun() {
        //use config here
        //***********do I need to create a matrix to capture data? Does 10 attempts mean
        RoundData[] roundsData = new RoundData[stats.getAttempts()];
    }
}
