import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle {

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


    public static void main(String[] args){
        Puzzle p = new Puzzle();
        Puzzle.loadDictionary();
    }

}
