package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends Piece{
    private ChessGame.TeamColor teamColor;
    private boolean firstMove = true;
    public Pawn(ChessGame.TeamColor myTeamColor) { //constructor
        super(PieceType.PAWN);
        this.teamColor = myTeamColor;
        firstMove = true;
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
        return PieceType.PAWN;
    }

    public void setFirstMove(boolean choice) {
        this.firstMove = choice;
    }

    public boolean getFirstMove() {
        return firstMove;
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessPosition> newSpots = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (firstMove) {
            //white goes down, black goes up on my board.
            //CHECK to see if both spots ahead are unoccupied. Add to Moves.
            if (this.teamColor == ChessGame.TeamColor.WHITE && myPosition.getRow() == 1) {
                Position spot1 = new Position(row + 1, col);
                Position spot2 = new Position(row + 2, col);

                if (board.getPiece(spot1) == null && board.getPiece(spot2) == null) {
                    moves.add(new Move(myPosition, spot2));
                }

            } else if (this.teamColor == ChessGame.TeamColor.BLACK && myPosition.getRow() == 6) {
                Position spot1 = new Position(row - 1, col);
                Position spot2 = new Position(row - 2, col);

                if (board.getPiece(spot1) == null && board.getPiece(spot2) == null) {
                    moves.add(new Move(myPosition, spot2));
                }
            }

            //this.firstMove = false;
        }

        //basic move
        if (this.teamColor == ChessGame.TeamColor.WHITE && row != 7) {
            newSpots.add(new Position(row + 1, col));
        } else if (this.teamColor == ChessGame.TeamColor.BLACK && row != 0) {
            newSpots.add(new Position(row - 1, col));
        }

        //if forward diagonal contains an enemy piece, add to MOVES, not just potential moves...
        if (this.teamColor == ChessGame.TeamColor.WHITE) {
            //acknowledge the edge pawns and their enemies
            if (col == 0) {
                ChessPiece possibleEnemy1 = board.getPiece(new Position(row+1,col+1));
                if (possibleEnemy1 != null && possibleEnemy1.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    //if an enemy is there... consider null spots as well.
                    moves.add(new Move(myPosition, new Position(row+1,col+1)));
                }
            } else if (col == 7) {
                ChessPiece possibleEnemy1 = board.getPiece(new Position(row+1,col-1));
                if (possibleEnemy1 != null && possibleEnemy1.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    //if an enemy is there... consider null spots as well.
                    moves.add(new Move(myPosition, new Position(row+1,col-1)));
                }
            } else {
                ChessPiece possibleEnemy1 = board.getPiece(new Position(row+1,col+1));
                ChessPiece possibleEnemy2 = board.getPiece(new Position(row+1,col-1));

                if (possibleEnemy1 != null && possibleEnemy1.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    //if an enemy is there... consider null spots as well.
                    moves.add(new Move(myPosition, new Position(row+1,col+1)));
                } else if (possibleEnemy2 != null && possibleEnemy2.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    moves.add(new Move(myPosition, new Position(row+1,col-1)));
                }
            }


        } else if (this.teamColor == ChessGame.TeamColor.BLACK) {

            if (col == 0) {
                ChessPiece possibleEnemy1 = board.getPiece(new Position(row-1,col+1));
                if (possibleEnemy1 != null && possibleEnemy1.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    //if an enemy is there... consider null spots as well.
                    moves.add(new Move(myPosition, new Position(row-1,col+1)));
                }
            } else if (col == 7) {
                ChessPiece possibleEnemy1 = board.getPiece(new Position(row-1,col-1));
                if (possibleEnemy1 != null && possibleEnemy1.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    //if an enemy is there... consider null spots as well.
                    moves.add(new Move(myPosition, new Position(row-1,col-1)));
                }
            } else if (row != 0) {
                ChessPiece possibleEnemy1 = board.getPiece(new Position(row-1,col+1));
                ChessPiece possibleEnemy2 = board.getPiece(new Position(row-1,col-1));

                if (possibleEnemy1 != null && possibleEnemy1.getTeamColor() != ChessGame.TeamColor.BLACK) {
                    moves.add(new Move(myPosition, new Position(row-1,col+1)));
                } else if (possibleEnemy2 != null && possibleEnemy2.getTeamColor() != ChessGame.TeamColor.BLACK) {
                    moves.add(new Move(myPosition, new Position(row-1,col-1)));
                }
            }

        }

        //go through newSpots and decide which ones are valid moves.
        for (ChessPosition spot : newSpots) {
            if (board.getPiece(spot) == null) {
                moves.add(new Move(myPosition, spot));
            }
        }

        Collection<ChessMove> finalMoves = new ArrayList<>(); //the one we will return
        //decide promotion pieces
        for (ChessMove myMove : moves) {
            ChessPosition start = myMove.getStartPosition();
            ChessPosition end = myMove.getEndPosition();

            if (this.teamColor == ChessGame.TeamColor.WHITE) {
                if (end.getRow() == 7) {
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.QUEEN));
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.BISHOP));
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.ROOK));
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.KNIGHT));
                    continue;
                }
            } else if (this.teamColor == ChessGame.TeamColor.BLACK) {
                if (end.getRow() == 0) {
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.QUEEN));
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.BISHOP));
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.ROOK));
                    finalMoves.add(new Move(start, end, ChessPiece.PieceType.KNIGHT));
                    continue;
                }
            }

            finalMoves.add(myMove);
        }

        return finalMoves;

    }
}
