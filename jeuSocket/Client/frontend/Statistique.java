package frontend;

import javax.swing.JPanel;
import java.awt.*;
import Supp.Player;

public class Statistique extends JPanel {

    Player me ;
    Player ennemy ;
    Plateau plateau ;
    Window window ;
    public Statistique(Window window)
    {
        setting(window);
        buildMe();

    } 

    public void buildMe()
    {
        this.setBounds(500, 0,300, window.getHeight());
    }

    public void setting(Window window)
    {
        this.plateau = window.getPlateau();
        this.me = plateau.getMe();
        this.ennemy = plateau.getEnemy();
        this.window = window;
    }

    public void paint(Graphics gg) {
        super.paint(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setColor(Color.green);
        buildAboutPlayer(g, me, 100);
        buildAboutPlayer(g, ennemy, 350);
    }
    
    public void buildAboutPlayer(Graphics2D g,Player player,int posY)
    {
        g.setColor(player.getColor());
        g.setFont(new Font("Ink Free",Font.BOLD,22));
        g.drawString(""+player.getNom(), 100,posY );
        if(plateau.getWinner()!=null)
        {
        if(plateau.getWinner().equals(player))
        {
            g.drawString("You Win", 100,posY+60 );
        }
        else
        {
            g.drawString("You Lose", 100,posY+60 );
        }
        }
    }
}
