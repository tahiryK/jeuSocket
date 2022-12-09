package terrainServer;

import javax.swing.JPanel;
import joueurServer.*;
import sourisServer.EventMouse;
import java.awt.*;
import java.io.IOException;

public class Poste extends JPanel {

    boolean isOwn = false;
    Plateau plateau;
    Color color;
    Player myMaster;
    int number;
    boolean selected = false;
    double xPos, yPos;

    public Poste(int x, int y, Plateau plateau, int number) throws IOException {

        this.plateau = plateau;
        this.xPos = ((x + 37.5));
        this.yPos = ((y + 37.5));
        this.number = number;
        this.setBounds(x, y, 75, 75);
        this.setLayout(null);
        this.setBackground(Color.white);
        this.addMouseListener(new EventMouse(this));
    }

    public boolean isNear(Poste poste) {
        double diffX = abs(this.xPos - poste.getxPos());
        double diffY = abs(this.yPos - poste.getyPos());
        if (diffX <= 160 && diffY <= 160 ) {
            return true;
        } else {
            return false;
        }
    }

    public double abs(double a) {
        if (a < 0) {
            a *= -1;
        }
        return a;
    }

    public void paint(Graphics gg) {
        super.paint(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setColor(Color.black);
        if (this.myMaster != null) {
            g.setColor(this.myMaster.getColor());
        }

        g.fillOval(0, 0, this.getWidth(), this.getHeight());
        // g.drawString("" + number, (int) 37.5, (int) 37.5);
        g.setColor(Color.green);
        if (selected) {
            g.setStroke(new BasicStroke(3));
            g.drawOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
        }
    }

    /**
     * @return the isOwn
     */
    public boolean isOwn() {
        return isOwn;
    }

    /**
     * @param isOwn the isOwn to set
     */
    public void setOwn(boolean isOwn) {
        this.isOwn = isOwn;
    }

    /**
     * @return the plateau
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * @return the xPos
     */
    public double getxPos() {
        return xPos;
    }

    /**
     * @param xPos the xPos to set
     */
    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * @return the yPos
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * @param yPos the yPos to set
     */
    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * @param plateau the plateau to set
     */
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the myMaster
     */
    public Player getMyMaster() {
        return myMaster;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param myMaster the myMaster to set
     */
    public void setMyMaster(Player myMaster) {
        this.myMaster = myMaster;
    }

}
