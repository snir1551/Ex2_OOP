package gameClient;


import Server.Game_Server_Ex2;
import api.*;
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
import java.util.Iterator;

public class MyGamePanel extends JPanel implements MouseListener {

    directed_weighted_graph graph;
    DWGraph_Algo  graph_algo;
    game_service game;
    private Range2Range _w2f;
    public MyGamePanel()
    {
        graph = new DWGraph_DS();
        graph_algo = new DWGraph_Algo(graph);
        game = Game_Server_Ex2.getServer(4);
        graph = Ex2.deserialize(game);

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



        drawGraph(g);

    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        int index = (int)graph.getNode(n.getKey()).getLocation().x() + ((int)graph.getNode(n.getKey()).getLocation().y()*this.getHeight());
        g.fillOval(index%this.getWidth(), index/getHeight(), this.getWidth()-500, this.getHeight());
    }

    private void drawGraph(Graphics g) {
        for(node_data n : graph.getV()) {
            g.setColor(Color.blue);
            drawNode(n, 5, g);
        }
        for(node_data n : graph.getV())
        {
            for(edge_data e : graph.getE(n.getKey()))
            {
                g.setColor(Color.gray);
                drawEdge(e, g);
            }
        }

    }

    private void drawEdge(edge_data e, Graphics g) {

        int index = (int)graph.getNode(e.getSrc()).getLocation().x() + (int)graph.getNode(e.getDest()).getLocation().y()*this.getHeight();
        g.drawLine(index%this.getWidth(), index/getHeight(), this.getWidth(), this.getHeight());
        //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }

    private static Range2D GraphRange(directed_weighted_graph g) {
        Iterator<node_data> itr = g.getV().iterator();
        double x0=0,x1=0,y0=0,y1=0;
        boolean first = true;
        while(itr.hasNext()) {
            geo_location p = itr.next().getLocation();
            if(first) {
                x0=p.x();
                x1=x0;
                y0=p.y();
                y1=y0;
                first = false;
            }
            else {
                if(p.x()<x0) {x0=p.x();} // min x
                if(p.x()>x1) {x1=p.x();} // max x
                if(p.y()<y0) {y0=p.y();} // min y
                if(p.y()>y1) {y1=p.y();} // max y
            }
        }
        Range xr = new Range(x0,x1);
        Range yr = new Range(y0,y1);
        return new Range2D(xr,yr);
    }
//    public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
//        Range2D world = GraphRange(g);
//        Range2Range ans = new Range2Range(world, frame);
//        return ans;
//    }

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
