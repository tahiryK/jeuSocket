package socketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import terrainServer.Window;

public class Server {

    Socket socket;

    public Server(int port) throws Exception {

        setUpServer(port);
    }

    public void setUpServer(int port) throws IOException {

        ServerSocket sS = new ServerSocket(port);
        System.out.println("Server ON --");
        System.out.println("Waiting for client to connect ...");
        this.socket = sS.accept();
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
