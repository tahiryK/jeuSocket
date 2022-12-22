package runner;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import Supp.Player;
import frontend.Plateau;
import frontend.Poste;

public class ExchangeData implements Runnable {
    
    DataInputStream dataIn ;
    Plateau plateau;
    Vector<Poste> postes ;
    Player enemy ;
    String[] instructions ;

    public ExchangeData(Socket socket,Plateau plateau) throws IOException
    {
        this.dataIn = new DataInputStream(socket.getInputStream());
        this.plateau = plateau;
        this.postes = plateau.getPostes();
        this.enemy = plateau.getEnemy();
    }

    @Override
    public void run() {
        
        try {
            while(!this.plateau.getWindow().getSocket().isClosed())
            {
                receiveData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveData() throws IOException
    {
       // if(!plateau.isMyTurn())
       // {
            String instruction = dataIn.readUTF();
            this.instructions = instruction.split(";");
            readInstruction(instruction);
           // System.out.println(instruction);
            plateau.verifyWinner();
            plateau.setMyTurn(true);
            
       // }
    }

    public void readInstruction(String instruction )
    {
        String[]  instrus = instruction.split(";");
        for(int i=0;i<instrus.length;i++)
        {
            if(instrus[i].contains("mode"))
            {

            }
            if(instrus[i].contains("action"))
            {
                String[] ordre = instrus[i].split(":");
                doInstruction(ordre[1]);
            }
        }
    }

    public void doInstruction(String ordre)
    {
        if(ordre.equalsIgnoreCase("placement"))
        {
            for(int i=0;i<instructions.length;i++)
            {
                if(instructions[i].contains("positionnement"))
                {
                    String[] instru = instructions[i].split(":");
                    ennemyPlacement(Integer.valueOf(instru[1]));
                }
            }
        }
        if(ordre.equalsIgnoreCase("deplacement"))
        {
            int numSelected = 0;
            for(int i=0;i<instructions.length;i++)
            {
                if(instructions[i].contains("selected"))
                {
                    String[] instru = instructions[i].split(":");
                    numSelected = Integer.valueOf(instru[1]);
                }
                if(instructions[i].contains("target"))
                {
                    String[] instru = instructions[i].split(":");
                    ennemyDeplacement(numSelected,Integer.valueOf(instru[1]));
                }
            }
        }
    }


    public void ennemyDeplacement(int selected,int target)
    {
        for(int i=0;i<postes.size();i++)
        {
            if(postes.get(i).getNumber()==selected)
            {
                postes.get(i).setMyMaster(null);
                postes.get(i).repaint();
                continue;
            }
            if(postes.get(i).getNumber()==target)
            {
                postes.get(i).setMyMaster(enemy);
                postes.get(i).repaint();
                continue;
            }
        }
    }

    public void ennemyPlacement(int numero)
    {
        for(int i=0;i<postes.size();i++)
        {
            if(postes.get(i).getNumber()==numero)
            {
                postes.get(i).setMyMaster(enemy);
                postes.get(i).getMyMaster().setPions(postes.get(i).getMyMaster().getPions() + 1);
                postes.get(i).repaint();
                plateau.verifyAllPieceSetted();
                break;
            }
        }
    }

    

}
