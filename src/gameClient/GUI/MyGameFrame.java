package gameClient.GUI;

import gameClient.Arena;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MyGameFrame extends JFrame implements ActionListener {

    private ArrayList<JMenuItem> menuItems;
    MyGamePanel myPanel;
    Arena arena;
    private Image backgroundImage;
    public MyGameFrame()
    {
        super("MyGame");
        initMyGameFrame();
        createMenuBar();
        Image src;

        initMyGamePanel();
//        src = Toolkit.getDefaultToolkit().createImage("src\\gameClient\\resources\\Background\\pokemon2.png");
//        try {
//            src = ImageIO.read(new File("src\\gameClient\\resources\\Background\\pokemon2.png"));
//        } catch (IOException ex) {
//
//        }
//        setContentPane(new JLabel(new ImageIcon(src)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuItems.get(2))
        {
            System.exit(0);
        }
//        if(e.getSource() == menuItems.get(0))
//        {
//            myPanel.update(this.arena);
//            arena.load("asda");
//        }

//        if(e.getSource() == menuItems.get(1))
//        {
//            myPanel.update(this.arena);
//            //arena.save();
//        }
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


//    private void WindowBackground()
//    {
//        ImageIcon img = new ImageIcon("src\\gameClient\\resources\\Background\\backgroundGame.png");
//        JLabel background = new JLabel("",img,JLabel.CENTER);
//        background.setBounds(0,0,this.getWidth(),this.getHeight());
//        add(background);
//    }





}
