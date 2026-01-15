package Classes;

import Interfaces.Colors;
import Interfaces.Cords;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Matrix {
    private final Colors empty;
    private static final int CELL_SIZE = 30;
    private final int ROW;
    private final int COL;
    private int numTerm = 0;
    private int numTermDistinctEmpty = 0;
    private final int[] vectorRow;
    private final int[] vectorCol;
    private final Colors[] vectorValue;
    private int heightValues = 100;

    public Matrix(int rows, int columns, Colors empty) {
        this.ROW = rows;
        this.COL = columns;
        this.empty = empty;

        vectorRow = new int[ROW * COL];
        vectorCol = new int[ROW * COL];
        vectorValue = new Colors[ROW * COL];

        initializeMatrix();
    }
    private int search( int x, int y ) {
        if (!existPos(x, y)) return -1;
        if (numTerm != 0) {
            for (int i = 0; i < numTerm; i++) {
                if ((vectorRow[i] == x) && (vectorCol[i] == y)) {
                    return i;
                }
            }
        }

        return -1;
    }
    public void insert(int x, int y, Colors value) {
        if (value != empty) {
            numTermDistinctEmpty++;
        }
        int lug = search(x, y);
        if (lug != -1) vectorValue[lug] = value;
        else {
            numTerm++;
            vectorValue[numTerm-1] = value;
            vectorRow[numTerm-1] = x;
            vectorCol[numTerm-1] = y;
        }
    }
    private int getCantLines() throws Exception{
        int cantLines = 0;
        for (int i = heightValues; i < ROW; i++) {
            var cantValues = 0;
            for (int j = 0; j < COL; j++) {
                if (getValue(i, j) == empty) break;
                cantValues++;
            }

            if ( cantValues == COL ) {
                destroyLines(i);
                cantLines++;
            };
        }

        return cantLines;
    }

    public Colors getValue(int x, int y) throws Exception {
        if (!existPos(x, y)) throw new Exception("ERROR: Indices Fuera De Rango");

        int lug = search(x, y);
        return (lug != -1) ? vectorValue[lug] : empty;
    }
    private boolean existPos(int x, int y) {
        return ((x >= 0) && (x <= ROW)) && ((y >= 0) && (y <= COL));
    }

    private void initializeMatrix() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                insert(i, j, Colors.BLACK);
            }
        }
    }

    public int getCOL() {
        return COL;
    }
    public int getROW() { return ROW; }

    private void drawBlock(Graphics2D g2d, int row, int col, Color color) {
        int x = row * CELL_SIZE;
        int y = col * CELL_SIZE;

        GradientPaint gradient = new GradientPaint(
                x, y, color.brighter(),
                x + CELL_SIZE, y + CELL_SIZE, color.darker()
        );
        g2d.setPaint(gradient);
        g2d.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);

        g2d.setColor(color.brighter().brighter());
        g2d.drawRect(x + 1, y + 1, CELL_SIZE - 3, CELL_SIZE - 3);

        g2d.setColor(color.brighter());
        g2d.drawLine(x + 1, y + 1, x + CELL_SIZE - 2, y + 1);
        g2d.drawLine(x + 1, y + 1, x + 1, y + CELL_SIZE - 2);

        g2d.setColor(color.darker().darker());
        g2d.drawLine(x + CELL_SIZE - 2, y + 1, x + CELL_SIZE - 2, y + CELL_SIZE - 2);
        g2d.drawLine(x + 1, y + CELL_SIZE - 2, x + CELL_SIZE - 2, y + CELL_SIZE - 2);
    }
    private void drawShadow(Graphics2D g2d, int row, int col) {
        int x = row * CELL_SIZE;
        int y = col * CELL_SIZE;
        int arc = 3; // Ratio

        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, CELL_SIZE - 1, CELL_SIZE - 1, arc, arc);
        g2d.setStroke(new BasicStroke(1));
    }
    public void drawCurrentPiece(Graphics2D g2d, Display display) throws Exception {
        var currentPiece = display.getCurrentPiece();
        if ( hasCollision(currentPiece) ) {
            insertPiece(display);
        }
        var pieceColor = currentPiece.getColor();
        currentPiece.getCords().forEach( c -> {
            drawBlock(g2d, c.x, c.y, Colors.getColor(pieceColor));
        });
        if ( currentPiece.getCanDrawShadow() ) {
            currentPiece.setCordsShadow(getCordsShadowPiece(currentPiece));

            var cordsShadow = currentPiece.getCordsShadow();
            if (cordsShadow != null) {
                cordsShadow.forEach( c -> {
                    drawShadow(g2d, c.x, c.y);
                });
            }
        }
    }
    public void insertPiece(Display display) throws Exception{
        var currentPiece = display.getCurrentPiece();
        currentPiece.canMove = false;
        currentPiece.getCords().forEach( c -> {
            if (c.y < currentPiece.getCordYMen()) {
                currentPiece.setCordYMen(c.y);
            }
            insert(c.y, c.x, currentPiece.getColor());
        });
        if (currentPiece.getCordYMen() < heightValues) heightValues = currentPiece.getCordYMen();

        var cantLines = getCantLines();
        if (cantLines > 0) {
            display.addLines(cantLines);
        }
    }
    private boolean hasCollision( Piece piece ) throws Exception{
        if ( ROW == piece.getCordYMay() + 1 ) return true;
        if ( numTermDistinctEmpty == 0 ) return false;

        return hasCollisionByCords(piece.getCords());
    }
    private boolean hasCollisionByCords(List<Cords> cords) throws Exception{
        for(Cords cord : cords) {
            if (cord.y + 1 >= ROW) return  true;
            var valueInDirection = getValue(cord.y + 1, cord.x);
            if (valueInDirection != empty) return true;
        }

        return false;
    }
    private List<Cords> getCordsShadowPiece(Piece piece) throws Exception{
        List<Cords> cordsAux = new ArrayList<>();
        AtomicBoolean isOver = new AtomicBoolean(false);

        for (Cords cord : piece.getCords())
            cordsAux.add(new Cords(cord.x, cord.y));
        do {
            cordsAux.forEach( c -> {
                c.y += 1;
                if (c.y >= ROW - 1) isOver.set(true);
            });
        } while (!hasCollisionByCords(cordsAux) && !isOver.get());

        return cordsAux;
    }
    public void moveToShadow(Display display) throws Exception {
        var piece = display.getCurrentPiece();
        piece.moveToShadow();
        insertPiece(display);
        piece.setCordsShadow(null);
    }
    private void destroyLines(int row) throws Exception{
        for (int j = 0; j < COL; j++) {
            insert(row, j, empty);
        }

        for (int i = row - 1; i >= heightValues; i--) {
            for (int j = 0; j < COL; j++) {
                var color = getValue(i, j);
                if (color != empty) {
                    insert(i + 1, j, color);
                    insert(i, j, empty);
                }
            }
        }
    }

    public boolean canMoveX( Piece piece, boolean direction ) {
        try {
            if ( piece.getCordYMay() + 4 >= heightValues ) {
                for(Cords cord : piece.getCords()) {
                    var cordX = direction ? 1 : -1;
                    var valueInDirection = getValue(cord.y, cord.x + cordX);
                    if ( valueInDirection != empty ) return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if ( !direction && piece.getCordXLeft() - 1 >= 0 ) return true;
        return direction && piece.getCordXRight() + 1 < COL;
    }
    public void drawGrid(Graphics2D g2d) {
        g2d.setColor(new Color(40, 40, 40));
        // Líneas verticales
        for (int i = 0; i <= COL; i++) {
            int x = i * CELL_SIZE;
            g2d.drawLine(x, 0, x, ROW * CELL_SIZE);
        }

        // Líneas horizontales
        for (int i = 0; i <= ROW; i++) {
            int y = i * CELL_SIZE;
            g2d.drawLine(0, y, COL * CELL_SIZE, y);
        }
    }

    public void drawAllPieces(Graphics2D g2d) throws Exception {
        if ( numTermDistinctEmpty > 0 ) {
            traverseValueMatrix(heightValues, 0, ROW, COL, (value, row, col)-> {
                if (value != empty) drawBlock( g2d, col, row, Colors.getColor( value ) );
            });
        }
    }

    @FunctionalInterface
    interface GetValueMatrixAndCords { void setValueAndCords(Colors color, int row, int col); }
    private void traverseValueMatrix(int initRow, int initCol, int toRow, int toCol, GetValueMatrixAndCords cb) {
        try {
            for (int i = initRow; i < toRow; i++) {
                for (int j = initCol; j < toCol; j++) {
                    var value = getValue(i, j);
                    cb.setValueAndCords(value, i, j);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getHeightValues() { return heightValues; }
}