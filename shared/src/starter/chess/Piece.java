package chess;

import java.util.Collection;
import java.util.Objects;

public abstract class Piece implements ChessPiece  {
    ChessPosition position;

    private final ChessPiece.PieceType type;

    //public Piece(ChessPosition spot) {
      //  this.position = spot;
    //}

    /**
     * no args constructor
     */
    public Piece(ChessPiece.PieceType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public abstract ChessGame.TeamColor getTeamColor();

    @Override
    public PieceType getPieceType() {
        return type;
    }

    //public abstract void setPieceType();

    @Override
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
    //He said should be an abstract class...
    // create subclasses of it for each piece

    public ChessPosition getPosition() {
        return position;
    }

    public void setPosition(ChessPosition position) {
        this.position = position;
    }
}
