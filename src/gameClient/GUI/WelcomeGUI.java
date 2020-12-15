package gameClient.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import Server.Game_Server_Ex2;
import gameClient.Audio.SimplePlayer;
import gameClient.Client;
import gameClient.ServerManagement;

public class WelcomeGUI extends JFrame {

    private int userID;
    private int stage;
    private ServerManagement serverManagement;
    public WelcomeGUI()
    {
        super("Login");
        initGUI();
        welcomWindow();
        initLogin();

    }


    private void initGUI()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dimension.height;
        int width = dimension.width;
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initLogin()
    {
        SimplePlayer player = new SimplePlayer("src\\gameClient\\resources\\Audio\\PokemonSong.mp3");
        Thread playerThread = new Thread(player);
        playerThread.start();
        String id = JOptionPane.showInputDialog("Enter your id number" );
        try {
            if(id.length() != 9)
            {
                JOptionPane.showMessageDialog(null, "Invalid input, length of id need to be 9", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            else
            {
                this.userID = Integer.parseInt(id);
            }
        } catch (Exception Ex) {
            JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
        File r = new File("src\\gameClient\\resources\\ball.png");
        try {
            Image robo = ImageIO.read(r);
            Image newimg = robo.getScaledInstance(130, 150,  Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(newimg);
            String[] options = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
            int gameNum = JOptionPane.showOptionDialog(null, "Choose the level you want to play", "Click a button",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options[0]);
            this.stage = gameNum;
            if (gameNum == JOptionPane.CLOSED_OPTION){
                System.exit(0);
            }
            Thread client = new Thread(new Client(userID,stage));
            client.start();
            this.setVisible(false);

        } catch (IOException | HeadlessException e) {
            e.printStackTrace();
        }
    }

    public void welcomWindow(){
        try {
            this.setContentPane(new JLabel(scaleImageIcon(new ImageIcon(ImageIO.read(new File("src\\gameClient\\resources\\background.png"))),this.getWidth(),this.getHeight())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.pack();
    }

    private ImageIcon scaleImageIcon(ImageIcon imageIcon, int width, int height)
    {
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
