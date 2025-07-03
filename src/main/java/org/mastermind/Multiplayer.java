package org.mastermind;

import java.io.*;
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

        public void run() {
            Socket socket = null;
            try {
                listener = new ServerSocket(gamePort);
                while (keepListening) {
                    socket = listener.accept();
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true); //autoflush

                    while (startNewGame) {
                        //*** creates a new game, but assumes this will be the last
                        writer.println("NEW_GAME");
                        startNewGame = false;

                        //*** if client agrees, send over the location of the trees
                        String response = reader.readLine();
                        if (response != null && response.equals("OK")) {
                            gameField.setupNewGame();
                            //loop through writing tree positions
                        } else {
                            System.err.println("Unxpected start response: " + response);
                            System.err.println("Skipping game and waiting again.");
                            keepPlaying = false;
                            break;
                        }

                        //Start the action!
                        writer.println("START");
                        response = reader.readLine();
                        keepPlaying = response.equals("OK");

                        while (keepPlaying) {
                            try {
                                if (gameField.trees.size() > 0) {
                                    writer.print("SCORE");
                                } else {
                                    write.print("END ");
                                    keepPlaying = false;
                                }
                                writer.println(gameField.getScore(1));
                                response = reader.readLine();
                                if (response == null) {
                                    keepPlaying = false;
                                    disconnecting = true;
                                } else {
                                    String parts[] = response.split(" ");
                                    switch (parts[0]) {
                                        case "END":
                                            keepPlaying = false;
                                        case "SCORE":
                                            gameField.setScore(2, parts[1]);
                                            break;
                                        case "DISCONNECT":
                                            disconnecting = true;
                                            keepPlaying = false;
                                            break;
                                        default:
                                            System.err.println("Warning. Unexpected command: "
                                                    + parts[0] + ". Ignoring.");
                                    }
                                }
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                System.err.println("Interrupted while polling. Ignoring");
                            }
                        }

                        if (!disconnecting) {
                            String message = gameField.getWinner() + " Would you like to ask them to play again?";
                            boolean myPlayAgain = //??? "Play Again Y/n"

                            if (myPlayAgain == true) {
                                writer.println("PLAY_AGAIN");
                                String playAgain = reader.readLine();
                                if (playAgain != null) {
                                    switch (playAgain) {
                                        case "YES":
                                            startNewGame = true;
                                            break;
                                        case "DISCONNECT":
                                            keepPlaying = false;
                                            startNewGame = false;
                                            disconnecting = true;
                                            break;
                                        default:
                                            System.err.println("Warning. Unexpected response: " + playAgain + ". Not playing again.");
                                    }
                                }
                            }
                        }
                    }


                }
                if (socket.isConnected()) {
                    // say goodbye
                    writer.println("DISCONNECT");
                    socket.close();
                }
            }
        } catch(
        SocketException se)

        {
            System.err.println("Disconnecting. Closing down server.");
            keepListening = false;
        } catch(
        IOException ioe)

        {
            System.err.println("Networking error. Closing down server.");
            ioe.printStackTrace();
            keepListening = false;
        } catch(
        Exception e)

        {
            System.err.println("Other exception occurred. Closing down server.");
            e.printStackTrace();
            keepListening = false;
        } finally

        {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException closingException) {
                System.err.println("Error closing client socket: " + closingException.getMessage());
            }
        }

        public void stopListening() {
            if (listener != null) {
                try{
                    listener.close();
                } catch (IOException ioe){
                    System.err.println("Error disconnecting listener: " + ioe.getMessage());
                }
            }
        }
    }

//    class Client implements Runnable{
//
//    }


//    @Override
//    public void gameRun() {
//        //use config here
//        //***********do I need to create a matrix to capture data? Does 10 attempts mean
//        RoundData[] roundsData = new RoundData[stats.getAttempts()];
//    }
}
