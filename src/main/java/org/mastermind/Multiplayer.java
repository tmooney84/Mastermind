package org.mastermind;


import java.io.*;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Multiplayer {
    private GameStats game;
    /// /////////////////////////////
    private volatile Scanner scanner;

    //private Field gameField???
    private int gamePort;
    private volatile boolean keepListening;
    private volatile boolean keepPlaying;
    private volatile boolean startNewGame;
    private volatile boolean disconnecting;
    private volatile boolean winner;
    private Server server;
    private Thread serverThread;
    private Thread clientThread;

    public Multiplayer(GameStats game, Scanner scanner) {
        this(game, scanner, 32581);
    }

    public Multiplayer(GameStats game, Scanner scanner, int port) {
        this.game = game;
        this.scanner = scanner;
        this.gamePort = port;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean getWinner() {
        return winner;
    }

    public Thread startServer(String serverName) {
        keepListening = true;
        keepPlaying = false;
        startNewGame = true;
        disconnecting = false;
        winner = true;           //Server gets first guess by default
        server = new Server();
        serverThread = new Thread(server);
        serverThread.start();

        server.setServerName(serverName);
        //gameField.setScore(2, "waiting...")

        //??? do I need to create a score??? could be inversely proportional to the
        //number of attempts?
        return serverThread;
    }

    public Thread joinGame(String otherServer, String clientName) {
        clientThread = new Thread(new Client(otherServer, clientName));
        clientThread.start();
        return clientThread;
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

    public void sendRunRound(GameStats game, PrintWriter writer) {
        // If we're not disconnecting, reply with our current score
        game.runRound(scanner);

        //"CODE_FOUND *Round_num* 4 0" "NO_CODE 2 2" "DISCONNECT"
        boolean codeFound = game.getCodeFound();
        int currentRound = game.getCurrentRound();
        RoundData current = game.getRoundData(currentRound);

        int wellPlaced = current.getWellPlaced();
        int misplaced = current.getMisplaced();

        if (!codeFound) {
            writer.println("NO_CODE " + currentRound + " " + wellPlaced + " " + misplaced);
            //increments to next round
            //???game.setCurrentRound(game.getCurrentRound() + 1);
        } else {
            keepPlaying = false;
            writer.println("CODE_FOUND " + currentRound + " " + wellPlaced + " " + misplaced);
        }
    }

    class Server implements Runnable {
        ServerSocket listener;
        private String serverName;
        private String clientName;

        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getServerName() {
            return serverName;
        }

        public String getClientName() {
            return clientName;
        }

        public void run() {
            Socket socket = null;
            try {
                listener = new ServerSocket(gamePort);
                while (keepListening) {
                    System.out.println("Waiting for opponent to join...");
                    socket = listener.accept();
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true); //autoflush

                  /*
                 //***may be a good idea to send userName with the play game???
                  So the three outcomes are "YOU WIN" "YOU LOSE" and "TIE"
                  -at the beginning of the game, the client should start with
                   "WAITING FOR OPPONENT'S GUESS", once the server player has
                   gone, then the game should be run

                  -after each attempt the RoundData should be sent over the
                  wire and added to the local client game. It needs to say
                  something like "WAITING FOR OPPONENT'S GUESS" when waiting for the
                  response



                   */


                    while (startNewGame) {
                        //need to reset CLIENT_NAME if necessary if new CLIENT JOINS??? depends
                        //on the "OK" if it is every time

                        //!!!!!!!!!!!!!!!!!!!!!!!!! pass "NEW_GAME SERVER_USERNAME"
                        //and same for the Client userName as well
                        //*** creates a new game, but assumes this will be the last
                        writer.println("NEW_GAME");
                        startNewGame = false;

                        //*** if client agrees, send over the location of the trees
                        //"OK CLIENT_NAME"
                        String response = reader.readLine();
                        String[] resParts = response.split(" ");
                        if (resParts != null && resParts[0].equals("OK") && resParts[1] != null) {
                            //gameField.setupNewGame(); //!!!!!!!!!!!!!!!!!!!!!!!!

                            setClientName(resParts[1]);

                            //loop through writing tree positions
                        } else {
                            System.err.println("Unexpected start response: " + response);
                            System.err.println("Skipping game and waiting again.");
                            keepPlaying = false;
                            break;
                        }

                        //Start the action!
                        writer.println("START " + game.getSecretCode());
                        response = reader.readLine();
                        keepPlaying = response.equals("OK");

                        while (keepPlaying) {
                            try {

                                if (winner && game.getCurrentRound() == 0) {
                                    sendRunRound(game, writer);
                                }

//                                if (gameField.trees.size() > 0) {
//                                    writer.println("SCORE");
//                                } else {
//                                    write.print("END ");
//                                    keepPlaying = false;
//                                }
//                                writer.println(gameField.getScore(1));

                                response = reader.readLine();
                                System.out.println("DEBUG: --" + response + "--");
                                /// ???????? Watch this code vvv
                                if (response == null) {
                                    keepPlaying = false;
                                    disconnecting = true;
                                } else {


                                    String[] parts = response.split(" ");
                                    int round = Integer.parseInt(parts[1]);

//TODO           "CODE_FOUND *round_num* 4 0" "NO_CODE round_num 2 2" "DISCONNECT"
                                    switch (parts[0]) {
                                        case "CODE_FOUND":
                                            if (!GameUtils.isValidNum(parts[1]) || !GameUtils.isValidNum(parts[2]) || !GameUtils.isValidNum(parts[3])) {
                                                System.err.println("Unexpected game command: " + response + ". Ignoring.");
                                                break;
                                            }

                                            game.setRoundStats(parts[2], parts[3], round);
                                            keepPlaying = false;
                                            winner = false;

                                            System.out.println("---");
                                            System.out.println("Round " + round / 2);
                                            System.out.println("The secret code was: " + game.getSecretCode());
                                            System.out.println(clientName + " wins the game in Round " + game.getCurrentRound() / 2 + "!");
                                            //***Persist to SQLITE database!!!***********//
                                            break;
                                        case "NO_CODE":
                                            if (!GameUtils.isValidNum(parts[1]) || !GameUtils.isValidNum(parts[2]) || !GameUtils.isValidNum(parts[3])) {
                                                System.err.println("Unexpected game command: " + response + ". Ignoring.");
                                                break;
                                            }

                                            game.setRoundStats(parts[2], parts[3], round);

                                            System.out.println("---");
                                            System.out.println("Round " + round / 2 + " -- " + clientName);
                                            System.out.println("Well placed pieces: " + parts[1]);
                                            System.out.println("Misplaced pieces: " + parts[2]);

                                            if (round == game.getAttempts() - 1) {
                                                System.out.println("Game Over...You Tied with " + clientName);
                                                //***Persist to SQLITE database!!!***********//
                                                break;
                                            }
                                            game.setCurrentRound(round + 1);

                                            break;
                                        case "DISCONNECT":
                                            disconnecting = true;
                                            keepPlaying = false;
                                            break;
                                        default:
                                            System.err.println("Unexpected game command: " + response + ". Ignoring.");
                                    }

                                }
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                System.err.println("Interrupted while polling. Ignoring");
                            }
//                        }
//
                            if (!disconnecting) {
                                String winMessage = winner ? "You Win!" : "You Lose.";
                                System.out.println(winMessage + " Would you like to play again? Y/n");
                                String myPlayAgain = scanner.nextLine();
                                if (myPlayAgain.equalsIgnoreCase("y")) {
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
            } catch (
                    SocketException se) {
                System.err.println("Disconnecting. Closing down server.");
                keepListening = false;
            } catch (
                    IOException ioe) {
                System.err.println("Networking error. Closing down server.");
                ioe.printStackTrace();
                keepListening = false;
            } catch (
                    Exception e) {
                System.err.println("Other exception occurred. Closing down server.");
                e.printStackTrace();
                keepListening = false;
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException closingException) {
                    System.err.println("Error closing client socket: " + closingException.getMessage());
                }
            }
        }

        public void stopListening() {
            if (listener != null) {
                try {
                    listener.close();
                } catch (IOException ioe) {
                    System.err.println("Error disconnecting listener: " + ioe.getMessage());
                }

            }
        }
    }

    class Client implements Runnable {
        String gameHost;
        String userName;
        boolean startNewGame;

        public Client(String host, String clientName) {
            gameHost = host;
            userName = clientName;
            keepPlaying = false;
            startNewGame = false;
            winner = false;
        }

        public void setGameHost(String gameHost) {
            this.gameHost = gameHost;
        }

        public void setUsername(String userName) {
            this.userName = userName;
        }

        public String getGameHost() {
            return gameHost;
        }

        public String getUsername() {
            return userName;
        }

        public void run() {
            try (Socket socket = new Socket(gameHost, gamePort)) {

                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                OutputStream out = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);

                // Assume the first game will start...
                startNewGame = true;
                while (startNewGame) {
                    // ... but only the first
                    startNewGame = false;

                    // We expect to see the NEW_GAME command first
                    String response = reader.readLine();
                    // If we don't see that command, bail
                    if (response == null || !response.equals("NEW_GAME")) {
                        System.err.println("Unexpected initial command: " + response);
                        System.err.println("Disconnecting");
                        writer.println("DISCONNECT");
                        return;
                    }

                    // Yay! We're going to play a game. Acknowledge this command
                    writer.println("OK " + userName);
                    // And now gather the trees and setup our field
                    //gameField.trees.clear(); !!!!!!
                    response = reader.readLine();

                    String[] resParts = response.split(" ");
                    if (!resParts[0].equals("START") || resParts[1] == null) {
                        // Hmm, we should have ended the list of trees with a START, but didn't. Bail out.
                        System.err.println("Unexpected start to the game: " + response);
                        System.err.println("Disconnecting");
                        writer.println("DISCONNECT");
                        return;
                    } else {
                        game.setSecretCode(resParts[1]);
                        // Yay again! We're starting a game. Acknowledge this command
                        writer.println("OK");
                        keepPlaying = true;
                        //gameField.repaint();
                    }
                    while (keepPlaying) {
                        //if Round 0, winner should go first or server...
                        if (winner && game.getCurrentRound() == 0) {
                            sendRunRound(game, writer);
                        }
                        response = reader.readLine();
                        System.out.println("DEBUG: --" + response + "--");
                        String[] parts = response.split(" ");
                        int round = Integer.parseInt(parts[1]);

//TODO           "CODE_FOUND *round_num* 4 0" "NO_CODE round_num 2 2" "DISCONNECT"
                        switch (parts[0]) {
                            case "CODE_FOUND":
                                if (!GameUtils.isValidNum(parts[1]) || !GameUtils.isValidNum(parts[2]) || !GameUtils.isValidNum(parts[3])) {
                                    System.err.println("Unexpected game command: " + response + ". Ignoring.");
                                    break;
                                }

                                game.setRoundStats(parts[2], parts[3], round);
                                keepPlaying = false;
                                winner = false;

                                System.out.println("---");
                                System.out.println("Round " + round / 2);
                                System.out.println("The secret code was: " + game.getSecretCode());
                                System.out.println(gameHost + " wins the game in Round " + game.getCurrentRound() / 2 + "!");
                                break;
                            case "NO_CODE":
                                if (!GameUtils.isValidNum(parts[1]) || !GameUtils.isValidNum(parts[2]) || !GameUtils.isValidNum(parts[3])) {
                                    System.err.println("Unexpected game command: " + response + ". Ignoring.");
                                    break;
                                }

                                game.setRoundStats(parts[2], parts[3], round);

                                System.out.println("---");
                                System.out.println("Round " + round / 2 + " -- " + gameHost);
                                System.out.println("Well placed pieces: " + parts[1]);
                                System.out.println("Misplaced pieces: " + parts[2]);

                                if (round == game.getAttempts() - 1) {
                                    System.out.println("Game Over...You Tied with " + gameHost);
                                    break;
                                }
                                game.setCurrentRound(round + 1);

                                break;
                            case "DISCONNECT":
                                disconnecting = true;
                                keepPlaying = false;
                                break;
                            default:
                                System.err.println("Unexpected game command: " + response + ". Ignoring.");
                        }


                        if (disconnecting) {
                            // We're disconnecting or they are. Acknowledge and quit.
                            writer.println("DISCONNECT");
                            return;
                        } else {
                            sendRunRound(game, writer);
                        }
                    }

                    if (!disconnecting) {
                        // Check to see if they want to play again
                        response = reader.readLine();
                        if (response != null && response.equals("PLAY_AGAIN")) {
                            // Do we want to play again?
                            String winMessage = winner ? "You Win!" : "You Lose.";
                            System.out.println(winMessage + " Would you like to play again? Y/n");
                            String myPlayAgain = scanner.nextLine();
                            if (myPlayAgain.equalsIgnoreCase("y")) {
                                writer.println("YES");
                                startNewGame = true;
                            } else {
                                // Not playing again so disconnect.
                                disconnecting = true;
                                writer.println("DISCONNECT");
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Networking error. Closing down client.");
                e.printStackTrace();
            }
        }
    }
}