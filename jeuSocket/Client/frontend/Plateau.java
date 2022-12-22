package frontend;

import javax.swing.JPanel;
import Supp.Player;
import runner.ExchangeData;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class Plateau extends JPanel {

    Window window;
    Color myColor = new Color(204, 27, 27);
    Color enemyColor = new Color(122, 33, 143);
    Vector<Poste> postes = new Vector<Poste>();
    Player me;
    Player enemy;
    Player inService;

    Poste selectedPoste;
    ExchangeData exhangeData;
    // Server go first
    boolean myTurn = false;
    int mode = 0;

    boolean showLineWinner = false;

    Poste firstPostePos ;
    Poste lastPostePos ;

    Player winner ;

    public Plateau(Window window) throws IOException {
        this.window = window;
        this.setBounds(0, 0, window.getWidth() - 300, window.getHeight());
        this.setLayout(null);
        setUpPlayer();
        placingPostes();
        exhangeData = new ExchangeData(window.getSocket(), this);
        Thread th = new Thread(exhangeData);
        th.start();
    }

    public void placingPostes() throws IOException {

        int x = 60;
        int y = 40;
        for (int i = 0; i < 9; i++) {
            Poste poste = new Poste(x, y, this, i);
            postes.add(poste);
            this.add(postes.get(i));
            x += 160;
            if (i == 2 || i == 5) {
                x = 60;
                y += 150;
            }
        }
    }

    public void setUpPlayer() {
        // Données de test
        this.me = new Player("Player 2");
        this.me.setColor(myColor);
        this.enemy = new Player("Player 1");
        this.enemy.setColor(enemyColor);
        // First
        this.inService = this.me;
    }

    public void paint(Graphics gg) {
        super.paint(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawPlateau(g);
        if(showLineWinner)
        {
            g.setColor(winner.getColor());
            g.setStroke(new BasicStroke(5));
            g.drawLine((int) firstPostePos.getxPos(), (int) firstPostePos.getyPos(), (int) lastPostePos.getxPos(), (int) lastPostePos.getyPos());
        }
        repaintAllPoste();
    }

    public void drawPlateau(Graphics2D g) {

        g.setColor(Color.DARK_GRAY);

        // horizontale
        g.drawLine((int) 97.5, (int) 77.5, (int) (this.getWidth() - 97.5), (int) 77.5);
        g.drawLine((int) 97.5, (int) 227.5, (int) (this.getWidth() - 97.5), (int) 227.5);
        g.drawLine((int) 97.5, (int) (this.getHeight() - 117.5), (int) (this.getWidth() - 97.5),
                (int) (this.getHeight() - 117.5));
        // verticale
        g.drawLine((int) 97.5, (int) 77.5, (int) 97.5, (int) (this.getHeight() - 97.5));
        g.drawLine((int) 257.5, (int) 77.5, (int) 257.5, (int) (this.getHeight() - 97.5));
        g.drawLine((int) 417.5, (int) 77.5, (int) 417.5, (int) (this.getHeight() - 97.5));
        // diagonale
        g.drawLine((int) 97.5, (int) 77.5, (int) 417.5, (int) (this.getHeight() - 97.5));
        g.drawLine((int) (this.getWidth() - 97.5), (int) 77.5, (int) 97.5, (int) (this.getHeight() - 97.5));
       g.drawLine((int) 227.5, (int) 105, (int) 97.5, (int) 227.5);
        g.drawLine((int) 97.5, (int) 227.5, (int) 227.5, (int) (this.getHeight() - 130));
        g.drawLine((int) 235, (int) 60, (int) (this.getWidth() - 97.5), (int) 227.5) ;
        g.drawLine((int) (this.getWidth() - 97.5), (int) 227, (int) 280, (int) (this.getHeight() - 130)) ; 
    }

    public void repaintAllPoste() {
        for (int i = 0; i < postes.size(); i++) {
            postes.get(i).repaint();
        }
    }

    public void swicthPlayer() {
        if (this.inService == this.me) {
            this.inService = this.enemy;
        } else {
            this.inService = this.me;
        }
    }

    public void verifyAllPieceSetted() {
        // Verifie si tout les 3 pions de chaque joueurs sont placer
        if (this.me.getPions() == 3 && this.enemy.getPions() == 3) {
            this.mode = 1;
           // System.out.println("MODEEEEE 1");
        }
    }

    public void removeWhoIsSelected() {
        for (int i = 0; i < this.postes.size(); i++) {
            if (this.postes.get(i).getMyMaster() != null) {
                if (this.postes.get(i).getMyMaster().equals(this.inService)) {
                    this.postes.get(i).setSelected(false);
                    this.postes.get(i).repaint();
                }
            }
        }
    }

    public boolean verifyWinner()
    {
        boolean weHaveAWinner = false ;
        for(int i=0;i<postes.size();i++)
        {
            if(postes.get(i).getMyMaster()==null){ continue; }
            // verticale
            if(i==0||i==1||i==2)
            {
                if(postes.get(i+3).getMyMaster()!=null&&postes.get(i+6).getMyMaster()!=null){ 
                if(postes.get(i+3).getMyMaster().equals(postes.get(i).getMyMaster())&&postes.get(i+6).getMyMaster().equals(postes.get(i).getMyMaster()))
                {
                    winner =postes.get(i).getMyMaster();
                  //  System.out.println("Verticaly");
                    this.firstPostePos = postes.get(i);
                    this.lastPostePos = postes.get(i+6);
                    weHaveAWinner = true;
                    break;
                }   
                }
            }
            // horizontale
            if(i==0||i==3||i==6)
            {
                if(postes.get(i+1).getMyMaster()!=null&&postes.get(i+2).getMyMaster()!=null){
                if(postes.get(i+1).getMyMaster().equals(postes.get(i).getMyMaster())&&postes.get(i+2).getMyMaster().equals(postes.get(i).getMyMaster()))
                {
                    winner =postes.get(i).getMyMaster();
                  //  System.out.println("Horizontaly");
                    this.firstPostePos = postes.get(i);
                    this.lastPostePos = postes.get(i+2);
                    weHaveAWinner = true;
                    break;
                }    
                }
            }
            // diagonale gauche
           // System.out.println("Player 2");
            if(i==0)
            {
             //   System.out.println("Verif diagonale");
              //  System.out.println(postes.get(i+4).getMyMaster());
              //  System.out.println(postes.get(i+8).getMyMaster());
                if(postes.get(i+4).getMyMaster()!=null&&postes.get(i+8).getMyMaster()!=null){ 
                if(postes.get(i+4).getMyMaster().equals(postes.get(i).getMyMaster())&&postes.get(i+8).getMyMaster().equals(postes.get(i).getMyMaster()))
                {
                    winner =postes.get(i).getMyMaster();
                 //   System.out.println("diagonale gauche");
                    weHaveAWinner = true;
                    this.firstPostePos = postes.get(i);
                    this.lastPostePos = postes.get(i+8);
                    break;
                }   
                }  
            }
             // diagonale droite
             if(i==2)
             {
                if(postes.get(i+2).getMyMaster()!=null&&postes.get(i+4).getMyMaster()!=null){ 
                 if(postes.get(i+2).getMyMaster().equals(postes.get(i).getMyMaster())&&postes.get(i+4).getMyMaster().equals(postes.get(i).getMyMaster()))
                 {
                    winner =postes.get(i).getMyMaster();
                   //  System.out.println("diagonale droite");
                     this.firstPostePos = postes.get(i);
                     this.lastPostePos = postes.get(i+4);
                     weHaveAWinner = true;
                     break;
                 }  
                }   
             }
        }

        if(weHaveAWinner)
        {
            this.showLineWinner = true;
            this.repaint();
            this.window.getStatistique().repaint();
            try {
                this.window.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }   

        }
        return weHaveAWinner;
    }
    /**
     * @return the window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * @param window the window to set
     */
    public void setWindow(Window window) {
        this.window = window;
    }

    public ExchangeData getExhangeData() {
        return exhangeData;
    }

    public void setExhangeData(ExchangeData exhangeData) {
        this.exhangeData = exhangeData;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * @return the myColor
     */
    public Color getMyColor() {
        return myColor;
    }

    /**
     * @param myColor the myColor to set
     */
    public void setMyColor(Color myColor) {
        this.myColor = myColor;
    }

    /**
     * @return the enemyColor
     */
    public Color getEnemyColor() {
        return enemyColor;
    }

    /**
     * @param enemyColor the enemyColor to set
     */
    public void setEnemyColor(Color enemyColor) {
        this.enemyColor = enemyColor;
    }

    /**
     * @return the postes
     */
    public Vector<Poste> getPostes() {
        return postes;
    }

    /**
     * @param postes the postes to set
     */
    public void setPostes(Vector<Poste> postes) {
        this.postes = postes;
    }

    /**
     * @return the me
     */
    public Player getMe() {
        return me;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * @param me the me to set
     */
    public void setMe(Player me) {
        this.me = me;
    }

    /**
     * @return the enemy
     */
    public Player getEnemy() {
        return enemy;
    }

    /**
     * @param enemy the enemy to set
     */
    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    /**
     * @return the inService
     */
    public Player getInService() {
        return inService;
    }

    /**
     * @param inService the inService to set
     */
    public void setInService(Player inService) {
        this.inService = inService;
    }

    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isShowLineWinner() {
        return showLineWinner;
    }

    public void setShowLineWinner(boolean showLineWinner) {
        this.showLineWinner = showLineWinner;
    }

    public Poste getFirstPostePos() {
        return firstPostePos;
    }

    public void setFirstPostePos(Poste firstPostePos) {
        this.firstPostePos = firstPostePos;
    }

    public Poste getLastPostePos() {
        return lastPostePos;
    }

    public void setLastPostePos(Poste lastPostePos) {
        this.lastPostePos = lastPostePos;
    }

    /**
     * @return the selectedPoste
     */
    public Poste getSelectedPoste() {
        return selectedPoste;
    }

    /**
     * @param selectedPoste the selectedPoste to set
     */
    public void setSelectedPoste(Poste selectedPoste) {
        this.selectedPoste = selectedPoste;
    }

}
