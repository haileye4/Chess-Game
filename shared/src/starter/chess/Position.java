package chess;

import java.util.Objects;

public class Position implements ChessPosition {
    int row = 0;
    int column = 0;

    public Position(int row, int col) {
        this.row = row;
        this.column = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public Position() {
        row = 0;
        column = 0;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public void setRow(int x) {
        row = x;
    }

    public void setColumn(int y) {
        column = y;
    }
}
