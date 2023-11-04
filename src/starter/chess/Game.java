package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Game implements ChessGame {
    ChessBoard board;
    ChessGame.TeamColor teamTurn;
    /**
     * no args constructor
     */
    public Game() {

    }
    @Override
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = this.board.getPiece(startPosition);
        ChessPiece myPiece = this.board.getPiece(startPosition);

        if (myPiece == null) {
            return null;
        }

        Collection<ChessMove> possibleMoves = myPiece.pieceMoves(this.board, startPosition);

        possibleMoves.removeIf(move -> putsKingInDanger(move, this.board, teamTurn));

        Collection<ChessMove> validMoves = new ArrayList<>();

        if (isInCheck(board.getPiece(startPosition).getTeamColor())) {
            //if move takes team out of check, it is a valid move
            for (ChessMove move : possibleMoves) {
                if (!putsKingInDanger(move, board, board.getPiece(startPosition).getTeamColor())) {
                    validMoves.add(move);
                }
            }
        }

        for (ChessMove move : possibleMoves) {
            if (!putsKingInDanger(move, board, board.getPiece(startPosition).getTeamColor())) {
                validMoves.add(move);
            }
        }

        return validMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPosition startPosition = move.getStartPosition();
        ChessPosition newPosition = move.getEndPosition();
        ChessPiece myPiece = this.board.getPiece(startPosition);
        //IMPLEMENT BELOW INTO VALID MOVES!!!!!!!!!!!!

//if not your turn
        if (myPiece.getTeamColor() != this.teamTurn) {
            throw new InvalidMoveException("Not your turn");
        }
//if not a valid move for the piece
        boolean isValidMove = false;
        for (ChessMove myMove : myPiece.pieceMoves(this.board, startPosition)) {
            if (move.equals(myMove)) {
                isValidMove = true;
            }
        }

        if (!isValidMove) {
            throw new InvalidMoveException("Is not a valid move");
        }
//if move puts the king in danger
        if (putsKingInDanger(move, this.board, teamTurn)) {
            throw new InvalidMoveException("Move puts king in danger");
        }

        if (move.getPromotionPiece() != null) {
            if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                this.board.removePiece(startPosition, myPiece);
                this.board.addPiece(newPosition, new Queen(teamTurn));
            } else if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                this.board.removePiece(startPosition, myPiece);
                this.board.addPiece(newPosition, new Bishop(teamTurn));
            } else if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                this.board.removePiece(startPosition, myPiece);
                this.board.addPiece(newPosition, new Rook(teamTurn));
            } else if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                this.board.removePiece(startPosition, myPiece);
                this.board.addPiece(newPosition, new Knight(teamTurn));
            }

        } else {
            //remove the piece from its old position, put in new position
            this.board.removePiece(startPosition, myPiece);
            this.board.addPiece(newPosition, myPiece);
        }

        if (myPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
            if (Math.abs(newPosition.getRow() - startPosition.getRow()) == 2) {
                myPiece.setFirstMove(false);
            }
        }

        //what about if a piece is removed from the new position? Should we put it in a list?
        //SWITCH TEAMS
        if (teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else if (teamTurn == TeamColor.BLACK) {
            teamTurn = TeamColor.WHITE;
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        //make a list of all black and white teams pieces,
        //make a giant collection of all moves possible on the other team and if any of those move end

        //find where our teams king is
        ChessPosition kingPosition = findKing(this.board, teamColor);
        
        //now kingPosition tells us where our king is!
        //now lets make a collection of moves from the other team and decide if any of them can end in our kingPosition
        Collection<ChessMove> otherTeamMoves = new ArrayList<>();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition spot = new Position(i, j);
                ChessPiece piece = board.getPiece(spot);

                if (piece != null) {
                    if (piece.getTeamColor() != teamColor) {
                        otherTeamMoves.addAll(piece.pieceMoves(board, spot));
                    }
                }
            }
        }

        boolean isInCheck = false;
        for (ChessMove move : otherTeamMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                isInCheck = true;
            }
        }
        //in the kings position then they are in checkmate
        return isInCheck;
    }
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        //2 conditions must be met.
        //first, are you in check?
        if (!isInCheck(teamColor)) {
            return false;
        }
        //second, no move I make can get me out of check... KING has no valid moves
        ChessPosition kingPosition = findKing(this.board, teamColor);
        ChessPiece daKing = this.board.getPiece(kingPosition);

        boolean canGetOut = false;
        for (ChessMove kingMove : daKing.pieceMoves(board, kingPosition)) {
            if (!putsKingInDanger(kingMove, this.board, teamColor)) {
                canGetOut = true;
            }
        }

        if (canGetOut) {
            return false;
        }

        //else, the king can't get out and we will return true because it IS in checkmate.
        return true;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        //moves possible = zero?

        //king is not in check and there are no possible moves
        boolean isInStalemate = false;

        Collection<ChessMove> moves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition spot = new Position(i, j);
                ChessPiece piece = board.getPiece(spot);

                if (piece != null) {
                    if (piece.getTeamColor() == teamColor) {
                        moves.addAll(piece.pieceMoves(board, spot));
                    }
                }
            }
        }

        for (ChessMove move : moves) {
            if (putsKingInDanger(move, board, teamColor)) {
                isInStalemate = true;
            }
        }

        if (moves.isEmpty()) {
            isInStalemate = true;
        }

        return isInStalemate;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return this.board;
    }

    public ChessPosition findKing(ChessBoard board, TeamColor teamColor) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition spot = new Position(i, j);
                ChessPiece piece = board.getPiece(spot);

                if (piece != null) {
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                        return new Position(i, j);
                    }
                }
            }
        }

        return null;
    }
    public boolean putsKingInDanger(ChessMove move, ChessBoard myBoard , TeamColor teamTurn) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition newPosition = move.getEndPosition();
        ChessPiece myPiece = myBoard.getPiece(startPosition);

        //make hypothetical move
        ChessPiece oldPiece = myBoard.getPiece(newPosition);

        myBoard.removePiece(newPosition, oldPiece);
        myBoard.removePiece(startPosition, myPiece);
        myBoard.addPiece(newPosition, myPiece);

        boolean danger = false;
        if (isInCheckHelper(teamTurn, myBoard)) {
            danger = true;
        }

        //reset board!
        myBoard.removePiece(newPosition, myPiece);
        myBoard.addPiece(newPosition, oldPiece);
        myBoard.addPiece(startPosition, myPiece);

        return danger;
    }

    public boolean isInCheckHelper(TeamColor teamColor, ChessBoard myBoard) {
        //make a list of all black and white teams pieces,
        //make a giant collection of all moves possible on the other team and if any of those move end

        //find where our teams king is
        ChessPosition kingPosition = findKing(myBoard, teamColor);
//this isn't working
        //now kingPosition tells us where our king is!
        //now lets make a collection of moves from the other team and decide if any of them can end in our kingPosition
        Collection<ChessMove> otherTeamMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition spot = new Position(i, j);
                ChessPiece piece = myBoard.getPiece(spot);

                if (piece != null) {
                    if (piece.getTeamColor() != teamColor) {
                        otherTeamMoves.addAll(piece.pieceMoves(myBoard, spot));
                    }
                }
            }
        }

        boolean isInCheck = false;
        for (ChessMove move : otherTeamMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                isInCheck = true;
            }
        }
        //in the kings position then they are in checkmate
        return isInCheck;
    }
}
