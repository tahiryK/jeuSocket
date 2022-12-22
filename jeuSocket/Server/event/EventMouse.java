package event;

import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;

import frontend.Plateau;
import frontend.Poste;

import java.awt.event.MouseEvent;

public class EventMouse implements MouseListener {

    Poste poste;
    Plateau plateau;
    DataOutputStream dataOut;
    String requete = "";

    public EventMouse(Poste poste) throws IOException {
        this.poste = poste;
        this.plateau = poste.getPlateau();
        this.dataOut = new DataOutputStream(poste.getPlateau().getWindow().getSocket().getOutputStream());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!plateau.isShowLineWinner())
        {
        try {

            if(plateau.isMyTurn())
            {
                requete = "";
                if (plateau.getMode() == 0) {
                    requete += "mode:"+plateau.getMode()+";";
                    placementSimple();
                }
                if (plateau.getMode() == 1) {
                    requete += "mode:"+plateau.getMode()+";";
                    deplacement();
                }
                plateau.verifyWinner();
            }
            else
            {
                System.out.println("It's not your turn");
            }
        } catch (Exception e1) {
            
            System.out.println(e1);
        }
        }
        else
        {
            System.out.println("The game has ended");
        }
       
        this.poste.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void placementSimple() throws IOException {
        if (poste.getMyMaster() == null && this.plateau.getInService().getPions() <= 2) {
            this.poste.setMyMaster(this.plateau.getInService());
            poste.getMyMaster().setPions(poste.getMyMaster().getPions() + 1);
            plateau.verifyAllPieceSetted();
            requete += "positionnement:" + this.poste.getNumber() + ";";
            requete += "action:placement;";
            //plateau.swicthPlayer();
            dataOut.writeUTF(requete);
            plateau.setMyTurn(false);
        } else {
            System.out.println("This place is already taken");
        }
    }

    public void deplacement() throws IOException {

        if (poste.getMyMaster() != null) {
            if (poste.getMyMaster().equals(this.plateau.getInService())) {
                plateau.removeWhoIsSelected();
                poste.setSelected(true);
                this.plateau.setSelectedPoste(poste);
            }
        } else if (poste.getMyMaster() == null
                && plateau.getSelectedPoste().isNear(poste)) {
            poste.setMyMaster(plateau.getSelectedPoste().getMyMaster());
            poste.repaint();
            requete += "action:deplacement;selected:"+ plateau.getSelectedPoste().getNumber()+";";
            requete += "target:"+poste.getNumber()+";";
            plateau.getSelectedPoste().setMyMaster(null);
            plateau.getSelectedPoste().setSelected(false);
            plateau.getSelectedPoste().repaint();
            plateau.setSelectedPoste(null);
            plateau.removeWhoIsSelected();
           // plateau.swicthPlayer();
           dataOut.writeUTF(requete);
           plateau.setMyTurn(false);
        }
    }

    public double abs(double a) {
        if (a < 0) {
            a *= -1;
        }
        return a;
    }

    /**
     * @return the poste
     */
    public Poste getPoste() {
        return poste;
    }

    /**
     * @param poste the poste to set
     */
    public void setPoste(Poste poste) {
        this.poste = poste;
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

}
