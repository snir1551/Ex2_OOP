package gameClient.Deserializer;

import com.google.gson.*;
import gameClient.Server;

import java.lang.reflect.Type;

public class ServerDeserializer implements JsonDeserializer<Server> {
    @Override
    public Server deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject GameServer = jsonObject.get("GameServer").getAsJsonObject();
        int pokemons = GameServer.getAsJsonObject().get("pokemons").getAsInt();
        boolean is_logged = GameServer.getAsJsonObject().get("is_logged_in").getAsBoolean();
        int moves = GameServer.getAsJsonObject().get("moves").getAsInt();
        int grade = GameServer.getAsJsonObject().get("grade").getAsInt();
        int game_level = GameServer.getAsJsonObject().get("game_level").getAsInt();
        int max_user_level = GameServer.getAsJsonObject().get("max_user_level").getAsInt();
        int id = GameServer.getAsJsonObject().get("id").getAsInt();
        String graph = GameServer.getAsJsonObject().get("graph").getAsString();
        int agents = GameServer.getAsJsonObject().get("agents").getAsInt();
        Server server = new Server(pokemons,is_logged,moves,grade,game_level,max_user_level,id,graph,agents);
        return server;
    }
}
