package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King extends Piece implements Cloneable {
    private ChessGame.TeamColor teamColor;

    public King(ChessGame.TeamColor myTeamColor) { //constructor
        super(PieceType.KING);
        this.teamColor = myTeamColor;
    }

    @Override
    public void setFirstMove(boolean b) {

    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }
    public void setTeamColor(ChessGame.TeamColor myTeamColor) {
        this.teamColor = myTeamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessPosition> newSpots = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        //up & down moves
        if (row < 7 && row > 0) {
            int newRow = row + 1;
            int newRow2 = row - 1;
            Position newPos = new Position(newRow, col);
            Position newPos2 = new Position(newRow2, col);
            newSpots.add(newPos);
            newSpots.add(newPos2);
        } else if (row == 0) {
            int newRow = row + 1;
            Position newPos = new Position(newRow, col);
            newSpots.add(newPos);
        } else if (row == 7) {
            int newRow = row - 1;
            Position newPos = new Position(newRow, col);
            newSpots.add(newPos);
        }

        //side to side moves
        if (col < 7 && col > 0) {
            int newCol = col + 1;
            int newCol2 = col - 1;
            Position newPos = new Position(row, newCol);
            Position newPos2 = new Position(row, newCol2);
            newSpots.add(newPos);
            newSpots.add(newPos2);
        } else if (col == 0) {
            int newCol = col + 1;
            Position newPos = new Position(row, newCol);
            newSpots.add(newPos);
        } else if (col == 7) {
            int newCol = col - 1;
            Position newPos = new Position(row, newCol);
            newSpots.add(newPos);
        }

        //diagonal moves: weird if they are on the edge or in corners...
        if (row < 7 && row > 0 && col < 7 && col > 0) { //in middle of board, can go diagonal any way
            newSpots.add(new Position(row + 1, col + 1));
            newSpots.add(new Position(row + 1, col - 1));
            newSpots.add(new Position(row - 1, col + 1));
            newSpots.add(new Position(row - 1, col - 1));
        }

        if (row == 0) {
            if (col == 0) {
                newSpots.add(new Position(row + 1, col + 1));
            } else if (col == 7) {
                newSpots.add(new Position(row + 1, col - 1));
            } else {
                newSpots.add(new Position(row + 1, col + 1));
                newSpots.add(new Position(row + 1, col - 1));
            }
        } else if (row == 7) {
            if (col == 0) {
                newSpots.add(new Position(row - 1, col + 1));
            } else if (col == 7) {
                newSpots.add(new Position(row - 1, col - 1));
            } else {
                newSpots.add(new Position(row - 1, col + 1));
                newSpots.add(new Position(row - 1, col - 1));
            }
        }

        if (col == 0 && row > 0 && row < 7) {
            newSpots.add(new Position(row + 1, col + 1));
            newSpots.add(new Position(row - 1, col + 1));
        } else if (col == 7 && row > 0 && row < 7) {
            newSpots.add(new Position(row + 1, col - 1));
            newSpots.add(new Position(row - 1, col - 1));
        }


        //add valid moves to array...
        for (ChessPosition spot : newSpots) {
            if (board.getPiece(spot) == null) {
                moves.add(new Move(myPosition, spot));
            } else if (board.getPiece(spot).getTeamColor() != teamColor) {
                moves.add(new Move(myPosition, spot)); //capture opposite color piece...
            }
        }

        return moves;
    }
}
