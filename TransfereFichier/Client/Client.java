package Client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Client {
    public static void main(String[] args) {
        File[] filetoSend = new File[1];

        JFrame jFrame = new JFrame("Client");
        jFrame.setSize(450, 450);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        JLabel jtitle = new JLabel("Fichier Envoyer");
        jtitle.setFont(new Font("Arial", Font.BOLD, 25));
        jtitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jlFileName = new JLabel("Veuillez choisir le fichier");

        jlFileName.setFont(new Font("Arial", Font.ITALIC, 20));
        jlFileName.setBorder(new EmptyBorder(50, 0, 0, 0));
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton = new JPanel();
        jpButton.setBackground(Color.BLACK);
        jpButton.setBorder(new EmptyBorder(75, 0, 10, 0));

        // bouton Envoyer
        JButton jbSendFile = new JButton("Envoyer");
        jbSendFile.setPreferredSize(new Dimension(150, 75));
        jbSendFile.setFont(new Font("Arial", Font.BOLD, 20));
        jbSendFile.setBackground(Color.GREEN);
        jpButton.add(jbSendFile);

        // bouton Choisir
        JButton jbChooseFile = new JButton("Choisir");
        jbChooseFile.setPreferredSize(new Dimension(150, 75));
        jbChooseFile.setFont(new Font("Arial", Font.BOLD, 20));
        jbChooseFile.setBackground(Color.PINK);
        jpButton.add(jbChooseFile);

        jbChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFile = new JFileChooser();
                jFile.setDialogTitle("Veuillez choisir");

                if (jFile.showOpenDialog(null) == jFile.APPROVE_OPTION) {
                    filetoSend[0] = jFile.getSelectedFile();
                    jlFileName.setText("Envoyer : " + filetoSend[0].getName());
                }
            }
        });

        jbSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filetoSend[0] == null) {
                    jlFileName.setText("veuillez choisir");
                } else {
                    try {
                        // Adresse IP
                        FileInputStream fileInputStream = new FileInputStream(filetoSend[0].getAbsolutePath());
                        Socket socket = new Socket("localhost", 1234);

                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                        String filename = filetoSend[0].getName();
                        byte[] filenameBytes = filename.getBytes();

                        byte[] fileContentBytes = new byte[(int) filetoSend[0].length()];
                        fileInputStream.read(fileContentBytes);

                        dataOutputStream.writeInt(filenameBytes.length);
                        dataOutputStream.write(filenameBytes);

                        dataOutputStream.writeInt(fileContentBytes.length);
                        dataOutputStream.write(fileContentBytes);

                        socket.close();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            }
        });

        jFrame.add(jtitle);
        jFrame.add(jlFileName);
        jFrame.add(jpButton);
        jFrame.setVisible(true);
    }
}