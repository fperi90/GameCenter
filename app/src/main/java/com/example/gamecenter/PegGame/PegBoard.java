package com.example.gamecenter.PegGame;

import android.content.Context;
import android.widget.Toast;

import com.example.gamecenter.DBHelper;

import java.util.Stack;

public class PegBoard {

    private int[][] cells;
    private int[] selected;

    private Stack<Movement> movementStack;

    private final int VOID = -1, PEG = 1, HOLE = 0;

    private final int STANDARD = 1, PRUEBAS = 2, SQUARE = 3, TEST = 4;

    private final int STARTED = 1, STOPPED = 2;


    private int gameStatus = 0;
    private int puntuacion = 0;


    public int getPuntuacion() {
        return puntuacion;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    private final static int[][] STANDARD_MAP = new int[][]{
            {-1, -1, 1, 1, 1, -1, -1},
            {-1, -1, 1, 1, 1, -1, -1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {-1, -1, 1, 1, 1, -1, -1},
            {-1, -1, 1, 1, 1, -1, -1}
    };

    public final static int[][] FRENCH_MAP = new int[][]{
            {-1, -1, 0, 1, 1, -1, -1},
            {-1, 1, 1, 1, 1, 1, -1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {-1, 1, 1, 1, 1, 1, -1},
            {-1, -1, 1, 1, 1, -1, -1},
    };

    public final static int[][] TEST_MAP = new int[][]{
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 0, 1, 0, -1},
            {-1, 0, 1, 1, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, -1, -1, -1, -1}
    };


    public final static int[][] SQUARE_MAP = new int[][]{
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, 1, 1, 1, 1, 1, -1},
            {-1, 1, 1, 0, 1, 1, -1},
            {-1, 1, 1, 1, 1, 1, -1},
            {-1, 1, 1, 1, 1, 1, -1},
            {-1, 1, 1, 1, 1, 1, -1},
            {-1, -1, -1, -1, -1, -1, -1},
    };


    public int[][] getCells() {
        return cells;
    }

    public PegBoard(int boardSelection) {
        this.movementStack = new Stack<>();
        this.cells = new int[7][7];
        copyToPanel(boardSelection);
    }

    public void copyToPanel(int selectedBoard) {
        int[][] toCopy = new int[7][7];
        switch (selectedBoard) {
            case STANDARD:
                toCopy = STANDARD_MAP;
                break;
            case PRUEBAS:
                toCopy = FRENCH_MAP;
                break;
            case SQUARE:
                toCopy = SQUARE_MAP;
                break;
            case TEST:
                toCopy = TEST_MAP;
                break;
        }
        for (int x = 0; x < cells.length; x++) {
            for (int i = 0; i < toCopy.length; i++) {
                for (int j = 0; j < toCopy[0].length; j++) {
                    cells[i][j] = toCopy[i][j];
                }
            }
    /*
            //este método copia un array con otro, con una sola variable
            System.arraycopy(toCopy[x], 0, cells[x], 0, toCopy.length);*/
        }
        gameStatus = STARTED;
    }


    public void select(int x, int y) {
        if (selected == null) {
            if ((cells[x][y]) == PEG) {
                selected = new int[]{x, y};
            }
        } else if (selected[0] == x && selected[1] == y) /*--> */
            selected = null;
        else {
            Movement movement = new Movement(selected, x, y);
            if (movement.isValid()) {
                movement.move();
                puntuacion = puntuacion + 10;
                System.out.println(puntuacion);
                if (checkVictory()) {
                    System.out.println("has ganado");
                    gameStatus = STOPPED;
                } else if (checkNoMoreMoves()) {
                    System.out.println("game over, no hay mas movimientos por" +
                            " realizar");
                    gameStatus = STOPPED;
                }
                selected = null;
            } else {
                selected = null;
            }
        }
    }

    private boolean checkVictory() {
        int pegCount = 0;
        for (int[] row : cells) {
            for (int cell : row) {
                if (cell == PEG) {
                    pegCount++;
                    if (pegCount >= 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkNoMoreMoves() {
        Movement movement;
        Movement movement2;
        Movement movement3;
        Movement movement4;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                movement = new Movement(x, y, x + 2, y);
                movement2 = new Movement(x, y, x, y + 2);
                movement3 = new Movement(x, y, x - 2, y);
                movement4 = new Movement(x, y, x, y - 2);
                if (movement.isValid() || movement2.isValid() || movement3.isValid() || movement4.isValid()) {
                    return false;
                }
            }
        }
        return true;
    }


    public int[] getSelected() {
        return selected;
    }

    public void undo() {
        if (!movementStack.empty()) {
            movementStack.pop().moveBack();
            puntuacion = puntuacion - 10;
        }
    }

    public class Movement {

        private final int x0;
        private final int y0;
        private final int x1;
        private final int y1;

        public Movement(int[] selected, int x1, int y1) {
            x0 = selected[0];
            y0 = selected[1];
            this.x1 = x1;
            this.y1 = y1;
        }

        public Movement(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        public boolean isValid() {
            if (x1 > 6 || x1 < 0) return false;

            if (y1 > 6 || y1 < 0) return false;

            if (cells[x0][y0] == PEG && cells[x1][y1] == HOLE) {
                int deltaX = x0 - x1;
                int deltaY = y0 - y1;
                if (deltaX == 0 || deltaY == 0) {
                    if (Math.abs(deltaX + deltaY) == 2) {
                        return cells[x0 - deltaX / 2][y0 - deltaY / 2] == PEG;
                    }
                }
            }
            return false;
        }

        public void move() {
            int deltaX = x1 - x0;
            int deltaY = y1 - y0;
            cells[x0][y0] = HOLE;
            cells[x0 + deltaX / 2][y0 + deltaY / 2] = HOLE;
            cells[x1][y1] = PEG;
            movementStack.push(this);
        }

        public void moveBack() {
            int deltaX = x1 - x0;
            int deltaY = y1 - y0;
            cells[x0][y0] = PEG;
            cells[x0 + deltaX / 2][y0 + deltaY / 2] = PEG;
            cells[x1][y1] = HOLE;
        }


    }
}
