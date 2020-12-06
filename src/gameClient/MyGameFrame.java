package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MyGameFrame extends JFrame implements ActionListener {


    private ArrayList<JMenuItem> menuItems;

    public MyGameFrame()
    {
        super("MyGame");
        initMyGameFrame();
        createMenuBar();
        initMyGamePanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void initMyGameFrame()
    {
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMyGamePanel()
    {
        MyGamePanel myPanel = new MyGamePanel();
        this.add(myPanel);
    }

    private void createMenuBar()
    {
        menuItems = new ArrayList<JMenuItem>();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        menuItems.add(new JMenuItem("Open",new ImageIcon("src\\gameClient\\resources\\open.png")));
        menuItems.get(0).addActionListener(this);

        menuItems.add(new JMenuItem("Save",new ImageIcon("src\\gameClient\\resources\\save.png")));
        menuItems.get(1).addActionListener(this);

        menuItems.add(new JMenuItem("Exit",new ImageIcon("src\\gameClient\\resources\\exit.png")));
        menuItems.get(2).addActionListener(this);

        for(JMenuItem m : menuItems)
        {
            menu.add(m);
        }

    }

}
