package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;

    private final int IMAGE_SIZE = 30; // The size of the pictures is the same in x and y.
    public static void main(String[] args)
    {
        new JavaSweeper();
    }

    private JavaSweeper ()
    {
        int ROWS = 9; // Lines
        int BOMBS = 5; // Number of bombs
        int COLS = 9; // Columns
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel ()
    {
        label = new JLabel("Don't step on the bombs");
        add (label, BorderLayout.SOUTH);
    }

    private void initPanel ()
    {
        panel = new JPanel() // When you initialize the output images.
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, this); //Type conversion to image.

            }
        };

        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) // LMB.
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3) // RBM.
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2) // MMB.
                    game.start (); // Restart the game.
                label.setText(getessage ());
                panel.repaint(); // Redraw the panel after actions.
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getessage()
    {
        switch (game.getState()) {
            case PLAYED: return "There are still bombs";
            case BOMBED:return "You lose";
            case WINNER: return "You win";
            default: return "Welcome";
        }
    }

    private void initFrame ()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        setVisible(true);
        pack(); // Optimizing the size of the screen.
        setIconImage(getImage("icon"));
        setLocationRelativeTo(null);
    }

    private void setImages ()
    {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name)
    {
        String filename = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename)); // Connecting pictures.
        return icon.getImage();
    }
}
