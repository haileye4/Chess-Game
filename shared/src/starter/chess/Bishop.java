package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends Piece {
    private ChessGame.TeamColor teamColor;

    public Bishop(ChessGame.TeamColor myTeamColor) { //constructor
        super(PieceType.BISHOP);
        this.teamColor = myTeamColor;
    }

    @Override
    public void setFirstMove(boolean b) {

    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessPosition> newSpots = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int boogieRight = 7 - col; //how many columns can we boogie to the right?
        int boogieLeft = col;
        int boogieDown = 7 - row;
        int boogieUp = row;
//NO NEED FOR 2 FOR LOOPS! WRONG

        //up and right
        if (boogieUp < boogieRight) {
            for (int i = 1; i < boogieUp+1; i++) {
                newSpots.add(new Position(row - i, col + i));
            }
        } else if (boogieRight < boogieUp) {
            for (int i = 1; i < boogieRight+1; i++) {
                newSpots.add(new Position(row - i, col + i));
            }
        } else if (boogieRight == boogieUp) {
            for (int i = 1; i < boogieRight+1; i++) {
                newSpots.add(new Position(row - i, col + i));
            }
        }

        //up and left
        if (boogieUp < boogieLeft) {
            for (int i = 1; i < boogieUp+1; i++) {
                newSpots.add(new Position(row - i, col - i));
            }
        } else if (boogieLeft < boogieUp) {
            for (int i = 1; i < boogieLeft+1; i++) {
                newSpots.add(new Position(row - i, col - i));
            }
        } else {
            for (int i = 1; i < boogieLeft+1; i++) {
                newSpots.add(new Position(row - i, col - i));
            }
        }

        //down and right
        if (boogieDown < boogieRight) {
            for (int i = 1; i < boogieDown+1; i++) {
                newSpots.add(new Position(row + i, col + i));
            }
        } else if (boogieRight < boogieDown) {
            for (int i = 1; i < boogieRight+1; i++) {
                newSpots.add(new Position(row + i, col + i));
            }
        } else {
            for (int i = 1; i < boogieRight+1; i++) {
                newSpots.add(new Position(row + i, col + i));
            }
        }

        //down and left
        if (boogieDown < boogieLeft) {
            for (int i = 1; i < boogieDown+1; i++) {
                newSpots.add(new Position(row + i, col - i));
            }
        } else if (boogieLeft < boogieDown) {
            for (int i = 1; i < boogieLeft+1; i++) {
                newSpots.add(new Position(row + i, col - i));
            }
        } else {
            for (int i = 1; i < boogieLeft+1; i++) {
                newSpots.add(new Position(row + i, col - i));
            }
        }

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
        //first, find where it is
        int row = currentSpot.getRow();
        int col = currentSpot.getColumn();
        int nextRow = nextSpot.getRow();
        int nextCol = nextSpot.getColumn();

        //Up and Right
        if (nextRow < row && nextCol > col) {
            int boogieUp = row - nextRow;
            int boogieRight = nextCol - col;

            if (boogieUp < boogieRight) {
                for (int i = 1; i < boogieUp; i++) {
                    if (board.getPiece(new Position(row - i, col + i)) != null) {
                        return false;
                    }
                }
            } else if (boogieRight < boogieUp) {
                for (int i = 1; i < boogieRight; i++) {
                    if (board.getPiece(new Position(row - i, col + i)) != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < boogieRight; i++) {
                    if (board.getPiece(new Position(row - i, col + i)) != null) {
                        return false;
                    }
                }
            }

        //Up and LEFT
        } else if (nextRow < row && nextCol < col) {
            int boogieUp = row - nextRow;
            int boogieLeft = col - nextCol;

            if (boogieUp < boogieLeft) {
                for (int i = 1; i < boogieUp; i++) {
                    if (board.getPiece(new Position(row - i, col - i)) != null) {
                        return false;
                    }
                }
            } else if (boogieLeft < boogieUp) {
                for (int i = 1; i < boogieLeft; i++) {
                    if (board.getPiece(new Position(row - i, col - i)) != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < boogieLeft; i++) {
                    if (board.getPiece(new Position(row - i, col - i)) != null) {
                        return false;
                    }
                }
            }

        //DOWN and RIGHT
        } else if (nextRow > row && nextCol > col) {
            int boogieDown = nextRow - row;
            int boogieRight = nextCol - col;

            if (boogieDown < boogieRight) {
                for (int i = 1; i < boogieDown; i++) {
                    if (board.getPiece(new Position(row + i, col + i)) != null) {
                        return false;
                    }
                }
            } else if (boogieRight < boogieDown) {
                for (int i = 1; i < boogieRight; i++) {
                    if (board.getPiece(new Position(row + i, col + i)) != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < boogieRight; i++) {
                    if (board.getPiece(new Position(row + i, col + i)) != null) {
                        return false;
                    }
                }
            }

        //DOWN and LEFT
        } else if (nextRow > row && nextCol < col) {
            int boogieDown = nextRow - row;
            int boogieLeft = col - nextCol;

            if (boogieDown < boogieLeft) {
                for (int i = 1; i < boogieDown; i++) {
                    if (board.getPiece(new Position(row + i, col - i)) != null) {
                        return false;
                    }
                }
            } else if (boogieLeft < boogieDown) {
                for (int i = 1; i < boogieLeft; i++) {
                    if (board.getPiece(new Position(row + i, col - i)) != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < boogieLeft; i++) {
                    if (board.getPiece(new Position(row + i, col - i)) != null) {
                        return false;
                    }
                }
            }
        }

        return true; //never returned false. All pieces in between must be empty.
    }
}
