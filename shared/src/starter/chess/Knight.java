package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends Piece{
    private ChessGame.TeamColor teamColor;

    public Knight(ChessGame.TeamColor myTeamColor) { //constructor
        super(PieceType.KNIGHT);
        this.teamColor = myTeamColor;
    }

    @Override
    public ChessPosition getPosition() {
            return position;
    }

    public void setPosition(ChessPosition position) {
        this.position = position;
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
        return PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessPosition> newSpots = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row > 1 && row < 6 && col > 1 && col < 6) {
            //only square they can jump all directions
            //UP AND DOWN L
            newSpots.add(new Position(row + 2, col + 1));
            newSpots.add(new Position(row + 2, col - 1));
            newSpots.add(new Position(row - 2, col + 1));
            newSpots.add(new Position(row - 2, col - 1));
            //RIGHT AND LEFT L
            newSpots.add(new Position(row + 1, col + 2));
            newSpots.add(new Position(row - 1, col + 2));
            newSpots.add(new Position(row + 1, col - 2));
            newSpots.add(new Position(row - 1, col - 2));
        } else if (row <= 7 && row > 5) {
            if (col == 0) {
                newSpots.add(new Position(row - 2, col + 1));
                newSpots.add(new Position(row - 1, col + 2));
            } else if (col == 7) {
                newSpots.add(new Position(row - 2, col - 1));
                newSpots.add(new Position(row - 1, col - 2));
            } else if (col == 6) {
                newSpots.add(new Position(row - 2, col - 1));
                newSpots.add(new Position(row - 2, col + 1));
                newSpots.add(new Position(row - 1, col - 2));
            } else if (col == 1) {
                newSpots.add(new Position(row - 2, col + 1));
                newSpots.add(new Position(row - 2, col - 1));
                newSpots.add(new Position(row - 1, col + 2));
            } else { // columns 2-5
                newSpots.add(new Position(row - 2, col + 1));
                newSpots.add(new Position(row - 2, col - 1));
                newSpots.add(new Position(row - 1, col + 2));
                newSpots.add(new Position(row - 1, col - 2));
            }
        } else if (row >= 0 && row < 2) {
            if (col == 0) {
                newSpots.add(new Position(row + 2, col + 1));
                newSpots.add(new Position(row + 1, col + 2));
            } else if (col == 7) {
                newSpots.add(new Position(row + 2, col - 1));
                newSpots.add(new Position(row + 1, col - 2));
            } else if (col == 6) {
                newSpots.add(new Position(row + 2, col - 1));
                newSpots.add(new Position(row + 2, col + 1));
                newSpots.add(new Position(row + 1, col - 2));
            } else if (col == 1) {
                newSpots.add(new Position(row + 2, col + 1));
                newSpots.add(new Position(row + 2, col - 1));
                newSpots.add(new Position(row + 1, col + 2));
            } else { // columns 2-5
                newSpots.add(new Position(row + 2, col + 1));
                newSpots.add(new Position(row + 2, col - 1));
                newSpots.add(new Position(row + 1, col + 2));
                newSpots.add(new Position(row + 1, col - 2));
            }
        } else if (row > 1 && row < 6) {
            if (col == 0) {
                newSpots.add(new Position(row + 2, col + 1));
                newSpots.add(new Position(row - 2, col + 1));
                newSpots.add(new Position(row + 1, col + 2));
                newSpots.add(new Position(row - 1, col + 2));
            } else if (col == 1) {
                newSpots.add(new Position(row + 2, col + 1));
                newSpots.add(new Position(row - 2, col + 1));
                newSpots.add(new Position(row + 2, col - 1));
                newSpots.add(new Position(row - 2, col - 1));

                newSpots.add(new Position(row + 1, col + 2));
                newSpots.add(new Position(row - 1, col + 2));
            } else if (col == 6) {
                newSpots.add(new Position(row + 2, col + 1));
                newSpots.add(new Position(row - 2, col + 1));
                newSpots.add(new Position(row + 2, col - 1));
                newSpots.add(new Position(row - 2, col - 1));

                newSpots.add(new Position(row + 1, col - 2));
                newSpots.add(new Position(row - 1, col - 2));
            } else if (col == 7) {
                newSpots.add(new Position(row + 2, col - 1));
                newSpots.add(new Position(row - 2, col - 1));
                newSpots.add(new Position(row + 1, col - 2));
                newSpots.add(new Position(row - 1, col - 2));
            }
        }

        //knights are the only pieces that can jump over other pieces!
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
