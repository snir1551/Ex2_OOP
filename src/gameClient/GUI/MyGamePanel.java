package gameClient.GUI;


import Server.Game_Server_Ex2;
import api.*;
import gameClient.*;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class MyGamePanel extends JPanel {

    private directed_weighted_graph graph;
    private ArrayList<Pokemon> pokemon;
    private ArrayList<Agent> agent;
    private Server server;
    private Arena arena;
    private node_location location;
    private Image background;
    ServerManagement serverManagement;
    private Image backgroundImage;
    /**
     * Constructor
     */
    public MyGamePanel()
    {
        super();
        background = new ImageIcon("src\\gameClient\\resources\\Background\\backgroundGame.png").getImage();
    }

    public void update(Arena arena)
    {

        this.arena = arena;
        graph = arena.getGraph();
        //agent = arena.getAgents();
        location = new NodeLocation(graph);
        this.setBackground(Color.blue);
        //background = new ImageIcon("src\\gameClient\\resources\\Background\\backgroundGame.png").getImage();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawImage(background,0,0,this.getWidth(),this.getHeight(),this);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(graph != null) {

            int w = this.getWidth();
            int h = this.getHeight();
            g.clearRect(0, 0, w, h);
            drawGraph(g);
            drawPokemons(g);
            drawAgent(g);
            drawGrade(g);
            drawMoves(g);
            DrawTime(g);
            //drawClock(g);
        }

    }




    private void drawGraph(Graphics g)
    {

        g.setColor(Color.blue);
        //ArrayList<Double> arrayLocation = arena.WorldToFrame();
        double maxX = location.getMaxXNodeData().getLocation().x();
        double minX = location.getMinXNodeData().getLocation().x();
        double maxY = location.getMaxYNodeData().getLocation().y();
        double minY = location.getMinYNodeData().getLocation().y();

        int r = 5;
        for(node_data nd : graph.getV())
        {

            double x = scale(nd.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            double y = scale(nd.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            g.fillOval((int)x - r, (int)y - r, 2*r, 2*r);
            g.drawString(""+nd.getKey(), (int)x- r, (int)y- r);

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
                //g.drawString(""+e.getWeight(), (int)x-20, (int)y- 20);
            }
        }

    }

    private void drawPokemons(Graphics g)
    {
        pokemon = new ArrayList<>(arena.getPokemons());
        int r = 10;
        double maxX = location.getMaxXNodeData().getLocation().x();
        double minX = location.getMinXNodeData().getLocation().x();
        double maxY = location.getMaxYNodeData().getLocation().y();
        double minY = location.getMinYNodeData().getLocation().y();
        BufferedImage PokemonValue;
        for(Pokemon p : pokemon)
        {
            double x = scale(p.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            double y = scale(p.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            if(p.getType() == -1)
            {
                try {
                    File Pokemon = new File("src\\gameClient\\resources\\Pokemons\\pokemon1.png");
                    PokemonValue = ImageIO.read(Pokemon);
                    g.drawImage(PokemonValue,(int)x - r, (int)y - r, 2*r, 2*r,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    File Pokemon = new File("src\\gameClient\\resources\\Pokemons\\pokemon2.png");
                    PokemonValue = ImageIO.read(Pokemon);
                    g.drawImage(PokemonValue,(int)x - r, (int)y - r, 2*r, 2*r,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }
    }

    private void drawAgent(Graphics g)
    {
        agent = arena.getAgents();
        if(agent == null)
        {
            return;
        }
        double maxX = location.getMaxXNodeData().getLocation().x();
        double minX = location.getMinXNodeData().getLocation().x();
        double maxY = location.getMaxYNodeData().getLocation().y();
        double minY = location.getMinYNodeData().getLocation().y();
        g.setColor(Color.red);
        int r = 15;
        BufferedImage agentAsh;
        for(Agent a : agent)
        {
            double x = scale(a.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            double y = scale(a.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            File player = new File("src\\gameClient\\resources\\Player\\ash.png");
            try {
                   agentAsh = ImageIO.read(player);
                   g.drawImage(agentAsh,(int)x - r, (int)y - r, 2*r, 2*r,this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    private void drawGrade(Graphics g)
    {
        server = arena.getServer();
        g.setColor(Color.blue);
        int r = 5;
        g.drawString( "Grade: "+String.valueOf(server.getGrade()),(int)20,(int)50);
    }

    private void drawMoves(Graphics g)
    {
        server = arena.getServer();
        g.setColor(Color.blue);
        int r = 5;
        g.drawString( "Moves: "+String.valueOf(server.getMoves()),(int)100,(int)50);
    }

    private void DrawTime(Graphics g)
    {
        double i =0;
        double time = arena.getServerManagement().timeToEnd()/1000;
        g.setColor(Color.blue);
        g.drawString( "Time: "+ String.valueOf(time),(int)200,(int)50);
    }



//    private void drawClock(Graphics g)
//    {
//        server = arena.getServer();
//        g.setColor(Color.blue);
//        int r = 5;
//        int i = 1;
//        while(server.Game(server.getGame_level()).isRunning())
//        {
//                System.out.println("a");
//                g.drawString( "Time: " + String.valueOf(i),(int)50,(int)20);
//        }
//
//
//    }

//    private void WindowBackGround(Graphics g)
//    {
//        try {
//            img = ImageIO.read(new File("src\\gameClient\\resources\\Background\\backgroundGame.png"));
//            System.out.println("b");
//        } catch(IOException e) {
//            e.printStackTrace();
//            System.out.println("a");
//        }
//        g.drawImage(img,0,0,getWidth(), getHeight(), this);
//        repaint();
//    }

//    private void WindowBackground()
//    {
//        ImageIcon img = new ImageIcon("src\\gameClient\\resources\\Background\\backgroundGame.png");
//        JLabel background = new JLabel("",img,JLabel.CENTER);
//        background.setBounds(0,0,this.getWidth(),this.getHeight());
//        add(background);
//
//    }

    private double scale(double data, double r_min, double r_max, double t_min, double t_max)
    {
        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }




}
