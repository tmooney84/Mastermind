package org.mastermind;

import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    ServerSocket listener;

    public void run(){
        Socket socket = null;
        try {
            listener = new ServerSocket(gamePort);
        }
    }
}
