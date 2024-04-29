import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PuzzleViewer extends JFrame{
    public static final int WINDOW_WIDTH = 1000,
            WINDOW_HEIGHT = 800,
            BUFFER_X = (int)(WINDOW_WIDTH*0.5 - 2.5 * Cell.SIZE),
            BUFFER_Y = (int)(WINDOW_HEIGHT*0.35),
            SIDE_LENGTH = (int)(WINDOW_WIDTH*0.19),
            LABEL_OFFSET = (int)(WINDOW_WIDTH*0.15),
            BUTTON_WIDTH = 200,
            BUTTON_HEIGHT = 50,
            BUTTON_X = (int)(WINDOW_WIDTH*0.04),
            BUTTON_Y = (int)(WINDOW_HEIGHT*0.9);

    public final String TITLE = "Wordsearch";


    private Puzzle game;

    public PuzzleViewer(Puzzle game){
        // Allows variables / methods to be shared
        this.game = game;

        // Initialize images

        // Creates a new window
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setVisible(true);
    }


    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
            if(game.getState() == 1) {
                // Prints the board
                for (Cell[] row : Puzzle.BOARD) {
                    for (Cell cell : row) {
                        cell.draw(g);
                    }
                }

                // Prints the word being found by the user
                if (game.getCurrentWord() != null) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Serif", Font.PLAIN, (int) (Cell.SIZE * 0.8)));
                    g.drawString(game.getCurrentWord(), BUFFER_X, LABEL_OFFSET);
                }

                // Prints the enter and exitbuttons
                // Code for rounded rectangle comes from tutorials point
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(200, 205, 232));
                g2d.fillRoundRect(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, 50, 30);
                int xPos = WINDOW_WIDTH - BUTTON_X - BUTTON_WIDTH;
                g2d.fillRoundRect(xPos, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, 50, 30);


                // Prints text over the buttons
                g.setColor(Color.BLACK);
                g.setFont(new Font("Serif", Font.PLAIN, (int) (BUTTON_HEIGHT * 0.7)));
                g.drawString("Enter Word", BUTTON_X + 17, BUTTON_Y + BUTTON_HEIGHT - 14);
                g.drawString("I'm done!", xPos + 32, BUTTON_Y + BUTTON_HEIGHT - 14);
            }

            if(game.getState() == 2){
                g.setColor(new Color(200, 205, 232));
                g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
                g.setFont(new Font("Felix Banjamin", Font.PLAIN, (int) (BUTTON_HEIGHT * 0.7)));
                g.setColor(Color.BLACK);
                g.drawString("You found " + game.getFoundWords().size() +" words",
                        WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
            }
    }
}
