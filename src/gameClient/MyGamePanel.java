package gameClient;


import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import kotlin.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class MyGamePanel extends JPanel {

    private directed_weighted_graph graph;
    private ArrayList<Pokemon> pokemon;
    private Arena arena;

    /**
     * Constructor
     * @param arena
     */
    public MyGamePanel(Arena arena)
    {
        this.arena = arena;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        drawGraph(g);
        drawPokemons(g);

    }




    private void drawGraph(Graphics g)
    {

        graph = arena.getGraph();
        g.setColor(Color.blue);
        node_location location = new NodeLocation(graph);
        double maxX = location.getMaxXNodeData().getLocation().x();
        double minX = location.getMinXNodeData().getLocation().x();
        double maxY = location.getMaxYNodeData().getLocation().y();
        double minY = location.getMinYNodeData().getLocation().y();
//        System.out.println(graph);
//        System.out.println(maxX);
//        System.out.println(minX);
//        System.out.println(maxY);
//        System.out.println(minY);

        int r = 5;
        for(node_data nd : graph.getV())
        {

            double x = scale(nd.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            double y = scale(nd.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            g.fillOval((int)x - r, (int)y - r, 2*r, 2*r);
            //System.out.println("x = " + (int)x + " y = " + (int)y);

        }

        g.setColor(Color.black);
        for(node_data nd : graph.getV())
        {
            for(edge_data e : graph.getE(nd.getKey()))
            {
                node_data destination = graph.getNode(e.getDest());
                double x = scale(nd.getLocation().x(),minX,maxX,20,this.getWidth()-20);
                double y = scale(nd.getLocation().y(),minY,maxY,this.getHeight()-10,150);
                double xx = scale(destination.getLocation().x(),minX,maxX,20,this.getWidth()-20);
                double yy = scale(destination.getLocation().y(),minY,maxY,this.getHeight()-10,150);
                g.drawLine((int)x,(int)y,(int)xx,(int)yy);
            }
        }

    }

    private void drawPokemons(Graphics g)
    {
        pokemon = new ArrayList<>(arena.getPokemons());
        double maxX = arena.maxXPokemon();
        double minX = arena.minXPokemon();
        double maxY = arena.maxYPokemon();
        double minY = arena.minYPokemon();
        System.out.println(maxX);
        System.out.println(minX);
        System.out.println(maxY);
        System.out.println(minY);
        int r = 5;
        for(Pokemon p : pokemon)
        {

            g.setColor(Color.green);
            double x = scale(p.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            double y = scale(p.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            g.fillOval((int)x - r, (int)y - r, 2*r, 2*r);
            System.out.println("B");




        }
    }

    private double scale(double data, double r_min, double r_max, double t_min, double t_max)
    {
        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }


}
