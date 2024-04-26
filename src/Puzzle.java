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
    public static final Cell[][] BOARD = new Cell[ROW][COL];
    private String puzzle = "";
    private String currentWord;
    private int state = 0;



    private PuzzleViewer window;

    public Puzzle(){
        currentWord = "";
        foundWords = new ArrayList<String>();
        loadPuzzle();
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                BOARD[i][j] = new Cell(puzzle.charAt(i*ROW + j), i, j);
            }
        }
        window = new PuzzleViewer(this);

        this.window.addMouseListener(this);
    }

    // Getters and setters
    public String getPuzzle(){
        return puzzle;
    }
    public int getState(){
        return state;
    }
    public void setState(int s){
        state = s;
    }
    public String getCurrentWord(){
        return currentWord;
    }


    // Load Dictionary taken from SpellingBee source code
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
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // Ask the input event the current location (x and y position on the Frame) of the mouse
        int x = e.getX();
        int y = e.getY();

        // Check if each Cell has been clicked
        for(Cell[] row: BOARD){
            for(Cell c: row){
                if (c.isClicked(x, y)) {
                    System.out.println("letter clicked");
                    // Move the ball and repaint.
                    currentWord += c.getLetter();
                    window.repaint();
                    return;
                }
            }
        }


        // If the enter Button is clicked, enter the current word into the foundWords arrayList
        boolean xPos = PuzzleViewer.BUTTON_X < x && PuzzleViewer.BUTTON_X + PuzzleViewer.BUTTON_WIDTH > x;
        boolean yPos = PuzzleViewer.BUTTON_Y < y && PuzzleViewer.BUTTON_Y + PuzzleViewer.BUTTON_HEIGHT > y;
        if(xPos && yPos){
            foundWords.add(currentWord);
            System.out.println(foundWords);
        }
        // If the I'm done button is clicked, change the state
        // WINDOW_WIDTH- BUTTON_X - BUTTON_WIDTH
        boolean xPos2 = PuzzleViewer.WINDOW_WIDTH - PuzzleViewer.BUTTON_X - PuzzleViewer.BUTTON_WIDTH < x &&
                PuzzleViewer.WINDOW_WIDTH - PuzzleViewer.BUTTON_X > x;
        if(xPos2 && yPos){
            state = 1;
            System.out.println(state);
        }



    }
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
