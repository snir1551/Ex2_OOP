package gameClient.GUI;

import api.*;
import gameClient.*;
import gameClient.Audio.SimplePlayer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MyGamePanel extends JPanel {

    private directed_weighted_graph graph;
    private Server server;
    private Arena arena;
    private node_location location;
    private Image background;
    private double maxX;
    private double minX;
    private double maxY;
    private double minY;

    /**
     * Constructor MyGamePanel
     */
    public MyGamePanel()
    {
        super();
        background = new ImageIcon("src\\gameClient\\resources\\Background\\backgroundGame.png").getImage();
        setFont(new Font("Verdana", Font.BOLD, 12));
        buttonMusic();
    }

    public void update(Arena arena)
    {
        this.arena = arena;
        graph = arena.getGraph();
        location = new NodeLocation(graph);
        maxX = location.getMaxXNodeData().getLocation().x();
        minX = location.getMinXNodeData().getLocation().x();
        maxY = location.getMaxYNodeData().getLocation().y();
        minY = location.getMinYNodeData().getLocation().y();
        this.setBackground(Color.blue);
    }


    /**
     * This override method paints all of our methods
     * @param g
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(graph != null) {
            g.drawImage(background,0,0,this.getWidth(),this.getHeight(),this);
            drawGraph(g);
            drawPokemons(g);
            drawAgent(g);
            drawGrade(g);
            drawMoves(g);
            DrawTime(g);
        }

    }


    /**
     * This method draw the graph of our game in our panel.
     * @param g
     */
    private void drawGraph(Graphics g)
    {
        g.setColor(Color.blue);
        int r = 5;
        double xNode;
        double yNode;
        for(node_data nd : graph.getV())
        {

            xNode = scale(nd.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            yNode = scale(nd.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            g.fillOval((int)xNode - r, (int)yNode - r, 2*r, 2*r);
            g.drawString(""+nd.getKey(), (int)xNode- r, (int)yNode- r);

        }

        g.setColor(Color.black);

        double xNodeSrc;
        double yNodeSrc;
        double xNodeDest;
        double yNodeDest;
        for(node_data nd : graph.getV())
        {
            for(edge_data e : graph.getE(nd.getKey()))
            {
                node_data destination = graph.getNode(e.getDest());
                xNodeSrc = scale(nd.getLocation().x(),minX,maxX,20,this.getWidth()-20);
                yNodeSrc = scale(nd.getLocation().y(),minY,maxY,this.getHeight()-10,150);
                xNodeDest = scale(destination.getLocation().x(),minX,maxX,20,this.getWidth()-20);
                yNodeDest = scale(destination.getLocation().y(),minY,maxY,this.getHeight()-10,150);
                g.drawLine((int)xNodeSrc,(int)yNodeSrc,(int)xNodeDest,(int)yNodeDest);
            }
        }

    }

    /**
     * This method draw the pokemons of the game on the graph in our panel.
     * @param g
     */
    private void drawPokemons(Graphics g)
    {
        ArrayList<Pokemon> pokemon = new ArrayList<>(arena.getPokemons());
        int r = 10;
        double x;
        double y;
        for(Pokemon p : pokemon)
        {
            x = scale(p.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            y = scale(p.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            g.drawImage(p.getImg(),(int)x - r, (int)y - r, 2*r, 2*r,this);
        }
    }

    /**
     * This method draw the agents of our game on the graph in our panel.
     * @param g
     */
    private void drawAgent(Graphics g)
    {
        ArrayList<Agent> agent = arena.getAgents();

        g.setColor(Color.red);
        int r = 15;
        for(Agent a : agent)
        {
            double x = scale(a.getLocation().x(),minX,maxX,20,this.getWidth()-20);
            double y = scale(a.getLocation().y(),minY,maxY,this.getHeight()-10,150);
            g.drawImage(a.getImg(),(int)x - r, (int)y - r, 2*r, 2*r,this);
        }

    }

    /**
     * This method draw the our grades of the game on the panel.
     * @param g
     */
    private void drawGrade(Graphics g)
    {
        server = arena.getServer();
        g.setColor(Color.blue);
        g.drawString( "Grade: "+ server.getGrade(),20,50);
    }

    /**
     * This method draw the our moves of the game on the panel.
     * @param g
     */
    private void drawMoves(Graphics g)
    {
        server = arena.getServer();
        g.setColor(Color.blue);
        g.drawString( "Moves: "+ server.getMoves(),this.getWidth()/2,50);
    }

    /**
     * This method draw the our time of the game on the panel.
     * @param g
     */
    private void DrawTime(Graphics g)
    {
        double time = (int)(arena.getServerManagement().timeToEnd()/1000);
        g.setColor(Color.blue);
        g.drawString( "Time: "+ time, getWidth()-100,50);
    }



    private double scale(double data, double r_min, double r_max, double t_min, double t_max)
    {
        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }

    private void buttonMusic()
    {
        Button musicButton = new Button("MusicOFF");
        musicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.add(musicButton);
    }



}
