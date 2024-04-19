import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PuzzleViewer extends JFrame{
    public static final int WINDOW_WIDTH = 1000,
            WINDOW_HEIGHT = 800,
            BUFFER_X = (int)(WINDOW_WIDTH*0.2),
            BUFFER_Y = (int)(WINDOW_HEIGHT*0.15),
            SIDE_LENGTH = (int)(WINDOW_WIDTH*0.19),
            LABEL_OFFSET = (int)(WINDOW_WIDTH*0.05);
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

    }
}
