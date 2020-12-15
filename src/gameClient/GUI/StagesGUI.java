package gameClient.GUI;

import gameClient.Client;
import gameClient.ServerManagement;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class StagesGUI extends JFrame implements MouseListener {

    private int userID;
    ServerManagement serverManagement;
    private JButton _buttonPress;
    public StagesGUI()
    {
        super("Login");
        initGUI();
        welcomWindow();
        buttons();
        //initLogin();

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

    private void buttons()
    {
        JButton _buttonPress =new JButton("Stage 0", scaleImageIcon(new ImageIcon("src\\gameClient\\resources\\ball.png"),30,30));
        _buttonPress.setVisible(true);
        _buttonPress.setBounds(100,100,140, 40);
        this.add(_buttonPress);

        JButton _buttonPress1 = new JButton("Press Me");
        this.add(_buttonPress1);
        _buttonPress1.setVisible(true);
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
