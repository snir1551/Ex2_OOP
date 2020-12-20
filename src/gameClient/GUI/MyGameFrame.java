package gameClient.GUI;



import gameClient.Arena;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MyGameFrame extends JFrame implements ActionListener {

    private ArrayList<JMenuItem> menuItemsFile; //menu items of the file
    private MyGamePanel myPanel; // panel


    /**
     * Constructor
     */
    public MyGameFrame()
    {
        super("PokemonGame"); // name of the game
        initMyGameFrame(); // init the game
        createMenuBar(); // init menu bar
        initMyGamePanel(); //init panel
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuItemsFile.get(0))
        {
            System.exit(0);
        }
    }


    /**
     * update function
     * @param arena
     */
    public void update(Arena arena) {
        myPanel.update(arena);
        repaint();
    }


    /**
     * this method init the game frame
     */
    private void initMyGameFrame()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dimension.height;
        int width = dimension.width;
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * this method init the game panel
     */
    private void initMyGamePanel()
    {
        myPanel = new MyGamePanel();
        this.add(myPanel);
    }

    /**
     * this method create menu bar
     */
    private void createMenuBar()
    {
        menuItemsFile = new ArrayList<JMenuItem>();
        JMenuBar menuBar = new JMenuBar(); // menubar
        JMenu menu1 = new JMenu("File"); // menu file
        menuBar.add(menu1); // add file to menubar
        this.setJMenuBar(menuBar); // add menubar to this

        menuItemsFile.add(new JMenuItem("Exit",scaleImageIcon(new ImageIcon("src\\gameClient\\resources\\exit.png"),30,30))); //add exit to the file




        for(JMenuItem m : menuItemsFile)
        {
            m.addActionListener(this);
            menu1.add(m);
        }

    }

    /**
     * this method change the size of the image icon
     * @param imageIcon
     * @param width
     * @param height
     * @return image with new size
     */
    private ImageIcon scaleImageIcon(ImageIcon imageIcon, int width, int height)
    {
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }






}
