package Classes;

import Interfaces.Colors;

public class Matrix {
    private final Colors empty;
    private final int ROW;
    private final int COL;
    private int numTerm = 0;
    private final int[] vectorRow;
    private final int[] vectorCol;
    private final Colors[] vectorValue;

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

    private void displace(int lug) {
        for (int i = lug; i < numTerm-1; i++) {
            vectorRow[i] = vectorRow[i+1];
            vectorCol[i] = vectorCol[i+1];
            vectorValue[i] = vectorValue[i+1];
        }
        numTerm--;
    }
    public void insert(int x, int y, Colors value) {
        int lug = search(x, y);
        if (lug != -1){
            vectorValue[lug] = value;
            if (value == empty) displace(lug);
        } else {
            numTerm++;
            vectorValue[numTerm-1] = value;
            vectorRow[numTerm-1] = x;
            vectorCol[numTerm-1] = y;
        }
    }
    public Colors getValue(int x, int y) throws Exception {
        if (!existPos(x, y)) throw new Exception("ERROR: Indices Fuera De Rango");

        int lug = search(x, y);
        return (lug != -1) ? vectorValue[lug] : empty;
    }
    private boolean existPos(int x, int y) {
        return ((x >= 0) && (x <= ROW)) && ((y >= 0) && (y <= COL));
    }
    public void show() {
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                vectorRow
//            }
//        }
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
    public int getROW() {
        return ROW;
    }
    public Colors getEmpty() {
        return empty;
    }
}