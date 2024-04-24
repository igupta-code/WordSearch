import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle implements MouseListener {

    private ArrayList<String> foundWords;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];
    public static final int ROW = 5,
            COL = 5;
    public static final Cell[][] BOARD = new Cell[ROW][COL];;
    private String puzzle = "";


    private PuzzleViewer window;

    public Puzzle(){
        loadPuzzle();
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                BOARD[i][j] = new Cell(puzzle.charAt(i*ROW + j), i, j);
            }
        }
        window = new PuzzleViewer(this);

        this.window.addMouseListener(this);
    }
    // Load Dictionary taken from SpellingBee source code
    public String getPuzzle(){
        return puzzle;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    // Load puzzle is a modified version of loadDictionary
    public void loadPuzzle() {
        Scanner s;
        File puzzleFile = new File("Resources/puzzle1.txt");
        try {
            s = new Scanner(puzzleFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open puzzle file.");
            return;
        }
        while(s.hasNextLine()) {
            puzzle += s.nextLine();
            System.out.println(puzzle);
        }
    }


    // Mouse Controls : taken from Mr. Blick's MouseDemo code
    @Override
    public void mouseClicked(MouseEvent e) {
        // Ask the input event the current location (x and y position on the Frame) of the mouse
        int x = e.getX();
        int y = e.getY();
        // Check if each Cell has been clicked
        for(Cell[] row: BOARD){
            for(Cell c: row){
                if (c.isClicked(x, y)) {
                    // Move the ball and repaint.
                    window.addToWord(c.getLetter());
                    window.repaint();
                    System.out.println(c.getLetter());
                }
            }
        }

    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}







    public static void main(String[] args){
        Puzzle p = new Puzzle();
        Puzzle.loadDictionary();
    }


}
