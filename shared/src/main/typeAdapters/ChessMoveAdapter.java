package typeAdapters;

import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessMoveAdapter implements JsonDeserializer<ChessMove>{
    @Override
    public ChessMove deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, Move.class);
    }
}
