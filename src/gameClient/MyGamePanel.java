package gameClient;


import api.*;
import gameClient.util.Range;
import gameClient.util.Range2D;
import kotlin.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class MyGamePanel extends JPanel implements MouseListener {

    directed_weighted_graph graph;
    DWGraph_Algo  graph_algo;


    public MyGamePanel()
    {
        //this.graph = graph;
        graph_algo = new DWGraph_Algo(graph);

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
