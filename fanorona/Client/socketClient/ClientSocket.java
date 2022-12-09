package socketClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import terrainClient.Window;

public class ClientSocket {

    Socket socket;

    public ClientSocket(String ip,int port) throws Exception {

        setUpclient(ip,port);
    }

    public void setUpclient(String ip ,int port) throws IOException {

        this.socket = new Socket(ip,port);
        System.out.println("I'm connected");
        new Window(this.socket);
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
