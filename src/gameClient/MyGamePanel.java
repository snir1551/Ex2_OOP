package gameClient;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class MyGamePanel extends JPanel implements MouseListener {


    public MyGamePanel()
    {
        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image img = null;
        super.paintComponent(g);
        try {
              img = ImageIO.read(new File("src\\gameClient\\resources\\gamebackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);

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
