import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle implements MouseListener {
    public static final int DICTIONARY_SIZE = 4868;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];
    public static final int ROW = 5,
            COL = 5;
    public static final Cell[][] BOARD = new Cell[ROW][COL];
    private ArrayList<String> foundWords;
    private ArrayList<String> allWords;
    private String puzzle = "";
    private String currentWord;
    private int state;
    private Cell last;
    // North = 0, East = 1, South = 2, West = 3
    // Northeast = 4, Southeast = 5, Southwest = 6, Northwest = 7
    private int direction;



    private PuzzleViewer window;

    public Puzzle(){
        direction = -1;
        last = null;
        currentWord = "";
        state = 0;
        foundWords = new ArrayList<String>();
        allWords = new ArrayList<String>();

        // Loads puzzle into a puzzle String
        // loadPuzzle();
        /*// Takes the puzzle String and maps it to the board
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                Cell c = new Cell(puzzle.charAt(i*ROW + j), i, j);
                BOARD[i][j] = c;
                c.setRow(i);
                c.setCol(j);

            }
        }
        findSoltutions();*/


        window = new PuzzleViewer(this);
        this.window.addMouseListener(this);
    }


    // Getters and setters
    public String getPuzzle(){return puzzle;}
    public int getState(){return state;}
    public void setState(int s){state = s;}
    public String getCurrentWord(){return currentWord;}
    public ArrayList<String> getFoundWords(){return foundWords;}
    public ArrayList<String> getAllWords(){return allWords;}


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
            if(s.nextLine().length() >= 3) {
                DICTIONARY[i++] = s.nextLine();
            }
        }
        System.out.println("dictionary size = " + i);
    }

    // Load puzzle is a modified version of loadDictionary, which was taken from SpellingBee
    // Loads puzzle from text file into the array
    public void loadPuzzle(File puzzleFile) {
        Scanner s;
        try {
            s = new Scanner(puzzleFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open puzzle file.");
            return;
        }
        while(s.hasNextLine()) {
            puzzle += s.nextLine();
        }

        // Takes the puzzle String and maps it to the board
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                Cell c = new Cell(puzzle.charAt(i*ROW + j), i, j);
                BOARD[i][j] = c;
                c.setRow(i);
                c.setCol(j);

            }
        }
        // After puzzle is set, find Solutions is called
        findSolutions();
    }

    // Find Solutions find all possible combinations of letters in the board
    // Go through every Cell in the board and use that letter as the starting point for the word
    public void findSolutions(){
        // Seek in every direction for each letter
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                this.seek(BOARD[i][j]);
            }
        }
        last = null;
    }

    // Seek finds all words greater than length three surrounding the letter
    public void seek(Cell c){
        currentWord += c.getLetter();
        int row = c.getRow();
        int col = c.getCol();

        // Go in all 8 directions around current cell
        // Looks down
        while(row+1 < 5 && isValidCell(BOARD[row + 1][col])){
            currentWord += BOARD[row + 1][col].getLetter();
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            row++;
        }
        // Reset variables to look in next direction
        currentWord = "";
        currentWord += c.getLetter();
        row = c.getRow();

        // Looks up
        while(row-1 >= 0 && isValidCell(BOARD[row - 1][col])){
            currentWord += BOARD[row - 1][col].getLetter();
            // Word must be greater than length 3
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            row--;
        }
        // Resets variables to look in next direction
        currentWord = "";
        currentWord += c.getLetter();
        row = c.getRow();


        // Looks right
        while(col+1 < 5 && isValidCell(BOARD[row][col+1])){
            currentWord += BOARD[row][col+1].getLetter();
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            col++;
        }
        currentWord = "";
        currentWord += c.getLetter();
        col = c.getCol();

        // Looks left
        while(col-1 > -1 && isValidCell(BOARD[row][col-1])){
            currentWord += BOARD[row][col-1].getLetter();
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            col--;
        }
        currentWord = "";
        currentWord += c.getLetter();
        col = c.getCol();


        // Looks at diagonal : right and down
        while(col+1 < 5 && row+1 < 5 && isValidCell(BOARD[row+1][col+1])){
            currentWord += BOARD[row+1][col+1].getLetter();
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            col++;
            row++;
        }
        currentWord = "";
        currentWord += c.getLetter();
        col = c.getCol();
        row = c.getRow();

        // Looks at diagonal : left and up
        while(col-1 > -1 && row-1 > -1 && isValidCell(BOARD[row-1][col-1])){
            currentWord += BOARD[row-1][col-1].getLetter();
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            col--;
            row--;
        }
        currentWord = "";
        currentWord += c.getLetter();
        col = c.getCol();
        row = c.getRow();

        // Looks at diagonal : right and up
        while(row-1 > -1 && col+1 < 5 && isValidCell(BOARD[row-1][col+1])){
            currentWord += BOARD[row-1][col+1].getLetter();
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            col++;
            row--;
        }
        currentWord = "";
        currentWord += c.getLetter();
        col = c.getCol();
        row = c.getRow();

        // Looks at diagonal : left and down
        while(col-1 > -1 && row+1 > 5 && isValidCell(BOARD[row+1][col-1])){
            currentWord += BOARD[row+1][col-1].getLetter();
            if(currentWord.length() >= 3){
                allWords.add(currentWord);
            }
            col--;
            row++;
        }
        currentWord = "";
    }


    // Some code taken from Spelling Bee
    // CheckWords iterates through allWords and found words to remove words that aren't in the dictionary
    public void checkWords(){
        // Goes through every word in foundWords and removes it if it's not a word
        for(int i = 0; i < foundWords.size(); i++){
            // The toLowerCase() method was found on w3schools.com
            if(!isWord(foundWords.get(i).toLowerCase(), 0, DICTIONARY_SIZE - 1)){
                foundWords.remove(i);
                i--;
            }
        }
        for(int i = 0; i < allWords.size(); i++){
            // The toLowerCase() method was found on w3schools.com
            if(!isWord(allWords.get(i).toLowerCase(), 0, DICTIONARY_SIZE - 1)){
                allWords.remove(i);
                i--;
            }
        }
    }

    // Uses Binary search to recursively find if the word is in the dictionary
    public boolean isWord(String word, int low, int high){
        // If low > high we have searched the whole dictionary and the word isn;t there
        if(low > high){
            return false;
        }

        int mid = (high + low) / 2;
        int compare = word.compareTo(DICTIONARY[mid]);

        // If it's found return true
        if(compare == 0){
            return true;
        }
        // Look to the right
        else if(compare > 0 ){
            return isWord(word, mid+1, high);
        }
        // Look to the left
        else{
            return isWord(word, low, mid-1);
        }
    }
    public boolean isValidCell(Cell c){
        if(c == null){
            return false;
        }
        if(last == null || currentWord.isEmpty()){
            return true;
        }

        int row = last.getRow() - c.getRow();
        int col = last.getCol() - c.getCol();
        // If the row is not between -1 and 1 then you are skipping rows
        if(row > 1 || row < -1 || col > 1 || col < -1 || (col == 0 && row == 0)) return false;

        if(currentWord.length() == 1){
            // North = 0, East = 1, South = 2, West = 3
            // Northeast = 4, Southeast = 5, Southwest = 6, Northwest = 7
            if(row == -1){
                if(col == -1) direction = 5;
                else if(col == 1) direction = 6;
                else direction = 2;
            }
            else if(row == 1){
                if(col == -1) direction = 4;
                else if(col == 1) direction = 7;
                else direction = 0;
            }
            else if(col == -1) direction = 1;
            else direction = 3;
        }
        else{
            // North = 0, East = 1, South = 2, West = 3
            // Northeast = 4, Southeast = 5, Southwest = 6, Northwest = 7
            if(direction == 0 || direction == 7 || direction == 4)
                if (row != 1) return false;
            if(direction == 6 || direction == 2 || direction == 5)
                if(row != -1) return false;
            if(direction == 0 || direction == 2)
                if(col != 0) return false;
            if(direction == 4 || direction == 1 || direction == 5)
                if(col != -1) return false;
            if(direction == 7 || direction == 3 || direction == 6)
                if(col != 1) return false;
            if(direction == 3 || direction == 1){
                return row == 0;
            }
        }
        return true;
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

        // Check if each Cell has been clicked if it has:
        // add the Cell's letter to the currentWord and repaint to show the word
        if(state == 1) {
            for (Cell[] row : BOARD) {
                for (Cell c : row) {
                    if (c.isClicked(x, y) && this.isValidCell(c)) {
                        last = c;
                        currentWord += c.getLetter();
                        window.repaint();
                        return;
                    }
                }
            }
        }

        // If the left Button is clicked, enter the current word into the foundWords arrayList
        boolean xPos = PuzzleViewer.BUTTON_X < x && PuzzleViewer.BUTTON_X + PuzzleViewer.BUTTON_WIDTH > x;
        boolean yPos = PuzzleViewer.BUTTON_Y < y && PuzzleViewer.BUTTON_Y + PuzzleViewer.BUTTON_HEIGHT > y;
        if(xPos && yPos){
            if(state == 0){
                loadPuzzle(new File("Resources/puzzle2.txt"));
                state = 1;
            }
            else if (state == 1) {
                foundWords.add(currentWord);
                currentWord = "";
                window.repaint();
            }
        }

        // If the right button is clicked, change the state
        boolean xPos2 = PuzzleViewer.WINDOW_WIDTH - PuzzleViewer.BUTTON_X - PuzzleViewer.BUTTON_WIDTH < x &&
                PuzzleViewer.WINDOW_WIDTH - PuzzleViewer.BUTTON_X > x;
        if(xPos2 && yPos){
            // If you're on state zero, load in the board and start the game
            if(state == 0){
                loadPuzzle(new File("Resources/puzzle1.txt"));
                state = 1;
            }
            else if (state == 1) {
                state = 2;
                // Check words is called so that the correct number of words is printed out on the front end
                checkWords();
                System.out.println("the user's words: \n" + foundWords + "\n");
                System.out.println("all words: \n" + allWords);
            }

            window.repaint();
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
