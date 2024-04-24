import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PuzzleViewer extends JFrame{
    public static final int WINDOW_WIDTH = 1000,
            WINDOW_HEIGHT = 800,
            BUFFER_X = (int)(WINDOW_WIDTH*0.5 - 2.5 * Cell.SIZE),
            BUFFER_Y = (int)(WINDOW_HEIGHT*0.35),
            SIDE_LENGTH = (int)(WINDOW_WIDTH*0.19),
            LABEL_OFFSET = (int)(WINDOW_WIDTH*0.15);
    private String currentWord;

    public final String TITLE = "Wordsearch";


    private Puzzle game;

    public PuzzleViewer(Puzzle game){
        // Allows variables / methods to be shared
        this.game = game;
        currentWord = "";

        // Initialize images

        // Creates a new window
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setVisible(true);
    }

    public String getCurrentWord(){
        return currentWord;
    }

    public void addToWord(char c){
        currentWord += c;
    }

    public void paint(Graphics g){
        // Prints the board
        for(Cell[] row: Puzzle.BOARD){
            for(Cell cell: row){
                cell.draw(g);
            }
        }

        // Prints the word being found by the user
        if(currentWord != null) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.PLAIN, (int) (Cell.SIZE * 0.8)));
            g.drawString(currentWord, BUFFER_X, LABEL_OFFSET);
        }
    }
}
