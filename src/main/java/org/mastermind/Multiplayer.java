package org.mastermind;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Multiplayer {
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
    public Multiplayer(GameStats stats, Scanner in) {
        // this(stats, in, gamePort);
//        this.stats = stats;
//        this.in = in;
    }

    public Multiplayer(GameStats stats, Scanner in, int port) {
        this.stats = stats;
        this.in = in;
        this.gamePort = port;
    }

    public void startServer() {
        keepListening = true;
        keepPlaying = false;
        startNewGame = true;
        disconnecting = false;
        server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
        //gameField.setScore(2, "waiting...")
    }

    public void joinGame(String otherServer) {
        clientThread = new Thread(new Client(otherServer));
        clientThread.start();
    }

    public void startGame() {
        startNewGame = true;
    }

    public void disconnect() {
        disconnecting = true;
        keepListening = false;

        if (server != null && keepPlaying == false) {
            server.stopListening();
        }
        keepPlaying = false;
    }

    class Server implements Runnable {
        ServerSocket listener;

        public void run(){
            Socket socket = null;
            try {
                listener = new ServerSocket(gamePort);
                try{
                    listener = new ServerSocket(gamePort);
                    while(keepListening)
                    {
                        socket = listener.accept();
                        InputStream input = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        OutputStream output = socket.getOutputStream();
                    }
                }
            }
        }
    }



//    @Override
//    public void gameRun() {
//        //use config here
//        //***********do I need to create a matrix to capture data? Does 10 attempts mean
//        RoundData[] roundsData = new RoundData[stats.getAttempts()];
//    }
}
