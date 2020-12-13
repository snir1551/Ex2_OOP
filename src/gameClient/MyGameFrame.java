package gameClient;

import api.game_service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MyGameFrame extends JFrame implements ActionListener {
    private ArrayList<JMenuItem> menuItems;
    MyGamePanel myPanel;
    public MyGameFrame()
    {
        super("MyGame");
        initMyGameFrame();
        createMenuBar();
        initMyGamePanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuItems.get(2))
        {
            System.exit(0);
        }
        if(e.getSource() == menuItems.get(1))
        {

        }
    }

    public void update(Arena arena) {
        myPanel.update(arena);
        repaint();
    }


    private void initMyGameFrame()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dimension.height;
        int width = dimension.width;
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMyGamePanel()
    {
        myPanel = new MyGamePanel();
        this.add(myPanel);
    }

    private void createMenuBar()
    {
        menuItems = new ArrayList<JMenuItem>();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        menuItems.add(new JMenuItem("Open",scaleImageIcon(new ImageIcon("src\\gameClient\\resources\\open.png"),30,30)));

        menuItems.add(new JMenuItem("Save",scaleImageIcon(new ImageIcon("src\\gameClient\\resources\\save.png"),30,30)));


        menuItems.add(new JMenuItem("Exit",scaleImageIcon(new ImageIcon("src\\gameClient\\resources\\exit.png"),30,30)));


        for(JMenuItem m : menuItems)
        {
            m.addActionListener(this);
            menu.add(m);
        }

    }

    private ImageIcon scaleImageIcon(ImageIcon imageIcon, int width, int height)
    {
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

}
