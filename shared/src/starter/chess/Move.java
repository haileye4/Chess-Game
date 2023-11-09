package chess;

import java.util.Objects;

public class Move implements ChessMove {
    //dp I need to make a constructor?
    ChessPosition startPosition = new Position();
    ChessPosition endPosition = new Position();
    ChessPiece.PieceType promotion = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(startPosition, move.startPosition) && Objects.equals(endPosition, move.endPosition) && promotion == move.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotion);
    }

    public Move(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece) {
        this.startPosition = start;
        this.endPosition = end;
        this.promotion = promotionPiece;
    }

    public Move(ChessPosition start, ChessPosition end) {
        this.startPosition = start;
        this.endPosition = end;
    }

    public Move() {

    }

    @Override
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    public void setStartPosition(ChessPosition newStart) {
        startPosition = newStart;
    }

    public void setEndPosition(ChessPosition newEnd) {
        endPosition = newEnd;
    }

    @Override
    public Piece.PieceType getPromotionPiece() {
        //if pawn is being promoted, then
        return promotion;
        //else return null
    }

    public void setPromotionPiece(ChessPiece.PieceType promotionPiece) {
        promotion = promotionPiece;
    }
}
