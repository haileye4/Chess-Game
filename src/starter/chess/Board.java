package chess;

public class Board implements ChessBoard{
    private ChessPiece[][] board = new ChessPiece[8][8]; //store the pieces in a 2D Array!

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }
    public void removePiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = null;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        if (board[position.getRow()][position.getColumn()] == null) {
            return null;
        }
        //else,there is a piece there
        return board[position.getRow()][position.getColumn()];
    }

    @Override
    public String toString() {

        StringBuilder board = new StringBuilder();

        for (int i = 0; i < 8; i++) { //row
            board.append("|");
            for (int j = 0; j < 8; j++) { //col
                if (this.board[i][j] == null) {
                    board.append(" ");
                    board.append("|");
                    continue;
                }
                ChessPiece.PieceType type = this.board[i][j].getPieceType();

                if (this.board[i][j].getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if (type == ChessPiece.PieceType.KING) {
                        board.append("K");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.KNIGHT) {
                        board.append("N");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.PAWN) {
                        board.append("P");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.ROOK) {
                        board.append("R");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.BISHOP) {
                        board.append("B");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.QUEEN) {
                        board.append("Q");
                        board.append("|");
                    }
                } else if (this.board[i][j].getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if (type == ChessPiece.PieceType.KING) {
                        board.append("k");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.KNIGHT) {
                        board.append("n");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.PAWN) {
                        board.append("p");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.ROOK) {
                        board.append("r");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.BISHOP) {
                        board.append("b");
                        board.append("|");
                    } else if (type == ChessPiece.PieceType.QUEEN) {
                        board.append("q");
                        board.append("|");
                    }
                }

            }
            board.append("\n");
        }

        return board.toString();
    }
    @Override
    public void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        //then add the pieces back in...
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1) { //the entire row will be pawns
                    ChessPiece pieceToAdd = new Pawn(ChessGame.TeamColor.WHITE);
                    board[i][j] = pieceToAdd;
                } else if (i == 6) {
                    ChessPiece pieceToAdd = new Pawn(ChessGame.TeamColor.BLACK);
                    board[i][j] = pieceToAdd;
                }
                //added pawns
                //add black pieces

                if (i == 0) {
                    if (j == 0 || j == 7) {
                        ChessPiece pieceToAdd = new Rook(ChessGame.TeamColor.WHITE);
                        board[i][j] = pieceToAdd;
                    } else if (j == 1 || j == 6) {
                        ChessPiece pieceToAdd = new Knight(ChessGame.TeamColor.WHITE);
                        board[i][j] = pieceToAdd;
                    } else if (j == 2 || j == 5) {
                        ChessPiece pieceToAdd = new Bishop(ChessGame.TeamColor.WHITE);
                        board[i][j] = pieceToAdd;
                    } else if (j == 3) {
                        ChessPiece pieceToAdd = new Queen(ChessGame.TeamColor.WHITE);
                        board[i][j] = pieceToAdd;
                    } else if (j == 4) {
                        ChessPiece pieceToAdd = new King(ChessGame.TeamColor.WHITE);
                        board[i][j] = pieceToAdd;
                    }
                } else if (i == 7) {
                    //add white pieces
                    if (j == 0 || j == 7) {
                        ChessPiece pieceToAdd = new Rook(ChessGame.TeamColor.BLACK);
                        board[i][j] = pieceToAdd;
                    } else if (j == 1 || j == 6) {
                        ChessPiece pieceToAdd = new Knight(ChessGame.TeamColor.BLACK);
                        board[i][j] = pieceToAdd;
                    } else if (j == 2 || j == 5) {
                        ChessPiece pieceToAdd = new Bishop(ChessGame.TeamColor.BLACK);
                        board[i][j] = pieceToAdd;
                    } else if (j == 3) {
                        ChessPiece pieceToAdd = new Queen(ChessGame.TeamColor.BLACK);
                        board[i][j] = pieceToAdd;
                    } else if (j == 4) {
                        ChessPiece pieceToAdd = new King(ChessGame.TeamColor.BLACK);
                        board[i][j] = pieceToAdd;
                    }

                }

            }
        }
    }
}
