package frontend;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

public class Window extends JFrame {

    Socket socket;
    Plateau plateau;
    Statistique statistique ;

    public Window(Socket socket) throws IOException {
        super("Server");
        this.socket = socket;
        this.setSize(800, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // --
        setUpPlateau();
        setUpStat();
        this.setVisible(true);
    }

    public void setUpPlateau() throws IOException {
        this.plateau = new Plateau(this);
        this.add(this.plateau);
    }

    public void setUpStat()
    {
        this.statistique = new Statistique(this);
        this.add(this.statistique);
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

    /**
     * @return the plateau
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * @param plateau the plateau to set
     */
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Statistique getStatistique() {
        return statistique;
    }

    public void setStatistique(Statistique statistique) {
        this.statistique = statistique;
    }

}
