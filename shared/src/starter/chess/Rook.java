package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends Piece{
    private ChessGame.TeamColor teamColor;

    public Rook(ChessGame.TeamColor myTeamColor) { //constructor
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
        return PieceType.ROOK;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessPosition> newSpots = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        for (int i = 0; i < 8; i ++) { //column move straight line
            if (i == col) {
                continue;
            } else {
                newSpots.add(new Position(row, i));
            }
        }

        for (int i = 0; i < 8; i ++) { //row move straight line
            if (i == row) {
                continue;
            } else {
                newSpots.add(new Position(i, col));
            }
        }

        //rooks cannot jump over pieces!!!! TAKE THAT INTO ACCOUNT
        for (ChessPosition spot : newSpots) {
            if (board.getPiece(spot) == null) {
                if (noPiecesInBetween(myPosition, spot, board)) {
                    moves.add(new Move(myPosition, spot));
                }
            } else if (board.getPiece(spot).getTeamColor() != teamColor) {
                if (noPiecesInBetween(myPosition, spot, board)) {
                    moves.add(new Move(myPosition, spot)); //capture opposite color piece...
                }
            }
        }

        return moves;
    }

    public boolean noPiecesInBetween(ChessPosition currentSpot, ChessPosition nextSpot, ChessBoard board) {
        if (currentSpot.getRow() == nextSpot.getRow()) {
            int row = currentSpot.getRow();
            //check every column space in between
            int currentCol = currentSpot.getColumn();
            int nextCol = nextSpot.getColumn();

            int inBetween = Math.abs(nextCol - currentCol) - 1;
            if (inBetween == 0) {
                return true;
            }

            int nextColCheck = currentCol;

            if (nextCol - currentCol < 0) {
                //we are going left...check spots to the left
                for (int i = 0; i < inBetween; i++) {
                    nextColCheck = nextColCheck - 1;
                    if (board.getPiece(new Position(row, nextColCheck)) != null) {
                        return false;
                    }
                }
            } else if (nextCol - currentCol > 0) {
                //we are going right...check spots to the left
                for (int i = 0; i < inBetween; i++) {
                    nextColCheck = nextColCheck + 1;
                    if (board.getPiece(new Position(row, nextColCheck)) != null) {
                        return false;
                    }
                }
            }
        } else if (currentSpot.getColumn() == nextSpot.getColumn()) {
            int col = currentSpot.getColumn();
            //check every column space in between
            int currentRow = currentSpot.getRow();
            int nextRow = nextSpot.getRow();

            int inBetween = Math.abs(nextRow - currentRow) - 1;
            if (inBetween == 0) {
                return true;
            }

            int nextRowCheck = currentRow;

            if (nextRow - currentRow < 0) {
                //we are going up...check spots above
                for (int i = 0; i < inBetween; i++) {
                    nextRowCheck = nextRowCheck - 1;
                    if (board.getPiece(new Position(nextRowCheck, col)) != null) {
                        return false;
                    }
                }
            } else if (nextRow - currentRow > 0) {
                //we are going down...check spots below
                for (int i = 0; i < inBetween; i++) {
                    nextRowCheck = nextRowCheck + 1;
                    if (board.getPiece(new Position(nextRowCheck, col)) != null) {
                        return false;
                    }
                }
            }
        }

        return true; //never returned false. All pieces in between must be empty.
    }
}
