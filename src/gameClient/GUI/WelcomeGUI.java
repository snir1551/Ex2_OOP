package gameClient.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
        String[] optionsMusic = {"YES","NO"};
        int MusicNum = JOptionPane.showOptionDialog(null, "Do you want Music in your game?", "Click a button YES or NO",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, optionsMusic, optionsMusic[0]);
        if(MusicNum == 0)
        {
            SimplePlayer player = new SimplePlayer("src\\gameClient\\resources\\Audio\\PokemonSong.mp3");
            Thread playerThread = new Thread(player);
            playerThread.start();
        }
        else if(MusicNum != 1)
        {
            System.exit(0);
        }
        int tryID = 3;
        while(tryID>0)
        {
            String id = JOptionPane.showInputDialog("Enter your id number" );
            try {
                if(id.length() != 9)
                {
                    tryID--;
                    JOptionPane.showMessageDialog(null, "Invalid input, length of id need to be 9, You have " + tryID +  " more attempts", "Error", JOptionPane.ERROR_MESSAGE);
                    if(tryID == 0)
                        System.exit(0);
                }
                else
                {
                    this.userID = Integer.parseInt(id);
                    break;
                }
            } catch (Exception Ex) {
                JOptionPane.showMessageDialog(null, "Exit", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
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

    private void welcomWindow(){
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
