package typeAdapters;

import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {
    @Override
    public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String pieceType = jsonElement.getAsJsonObject().get("type").getAsString();

        //switch statement or if else statement
        //if king, call guy depending on piece
        if (pieceType == "KING") {
            return jsonDeserializationContext.deserialize(jsonElement, King.class);
        } else if (pieceType == "QUEEN") {
            return jsonDeserializationContext.deserialize(jsonElement, Queen.class);
        } else if (pieceType == "KNIGHT") {
            return jsonDeserializationContext.deserialize(jsonElement, Knight.class);
        } else if (pieceType == "PAWN") {
            return jsonDeserializationContext.deserialize(jsonElement, Pawn.class);
        } else if (pieceType == "BISHOP") {
            return jsonDeserializationContext.deserialize(jsonElement, Bishop.class);
        } else {
                return jsonDeserializationContext.deserialize(jsonElement, Rook.class);
        }
    }

}
