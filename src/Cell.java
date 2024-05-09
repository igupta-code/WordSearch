import java.awt.*;

// Isha Gupta
public class Cell {
    private char letter;
    private int row;
    private int col;
    public static final int SIZE = 100;


    public Cell(char letter, int row, int col) {
        this.letter = letter;
        this.row = row;
        this.col = col;
    }

    // Getter and Setters
    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isClicked(int x, int y) {
        int topX = PuzzleViewer.BUFFER_X + col * SIZE;
        int bottomY = PuzzleViewer.BUFFER_Y + row * SIZE;
        // The clicked x / y values need to be greater than the top of the box
        // But not greater than the bottom / right side of the box

        return (x > topX && x < topX + SIZE) && (y < bottomY && y > bottomY - SIZE);
    }


    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, (int) (SIZE * 0.8)));
        // FIX THIS --- the 100s need to change
        int x = PuzzleViewer.BUFFER_X + col * SIZE;
        int y = PuzzleViewer.BUFFER_Y + row * SIZE;
        g.drawString("" + letter, x, y);
    }
}

