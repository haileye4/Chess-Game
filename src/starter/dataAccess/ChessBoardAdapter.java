package dataAccess;

import chess.Board;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {
    @Override
    public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Gson().fromJson(jsonElement, Board.class);
    }
}
