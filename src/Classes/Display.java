package Classes;

public class Display {
    private Piece currentPiece;
    private Piece nextPiece = null;
    private int score = 0;
    private  int level = 1;
    private int cantLines = 0;

    public void changePiece() {
        currentPiece = (nextPiece != null) ? nextPiece : Piece.createRandomPiece();
        nextPiece = Piece.createRandomPiece();
    }

    public void addLines( int cantLines ) {
        this.cantLines += cantLines;
    }
    public int getLines() { return cantLines; }

    public Piece getCurrentPiece() { return currentPiece; }
    public Piece getNextPiece() { return nextPiece; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
