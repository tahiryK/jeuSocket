package Supp;

import java.awt.*;

public class Player {

    String nom;
    Color color;
    Integer points;
    // nombre de pions sur le terrain
    Integer pions = 0;

    public Player(String nom) {
        this.nom = nom;
    }

    /**
     * @return the pions
     */
    public Integer getPions() {
        return pions;
    }

    /**
     * @param pions the pions to set
     */
    public void setPions(Integer pions) {
        this.pions = pions;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
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
     * @return the points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(Integer points) {
        this.points = points;
    }
}
