package gameClient.Deserializer;

import api.directed_weighted_graph;
import com.google.gson.*;
import gameClient.Pokemon;
import gameClient.util.Point3D;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ServerPokemonJsonDeserializer implements JsonDeserializer<ArrayList<Pokemon>> {

    @Override
    public ArrayList<Pokemon> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray PokemonObject = jsonObject.get("Pokemons").getAsJsonArray();
        ArrayList<Pokemon> arrayPokemon = new ArrayList<>();
        for (JsonElement jsonValueElement : PokemonObject) {
            JsonObject Pokemon = jsonValueElement.getAsJsonObject().get("Pokemon").getAsJsonObject();
            double valuePokemon = Pokemon.getAsJsonObject().get("value").getAsDouble();
            int typePokemon = Pokemon.getAsJsonObject().get("type").getAsInt();
            String pos = Pokemon.getAsJsonObject().get("pos").getAsString();
            String[] splitPos = pos.split(",");
            double posX = Double.parseDouble(splitPos[0]);
            double posY = Double.parseDouble(splitPos[1]);
            double posZ = Double.parseDouble(splitPos[2]);
            Point3D location = new Point3D(posX,posY,posZ);
            arrayPokemon.add(new Pokemon(valuePokemon,typePokemon,location));
        }
        return arrayPokemon;
    }
}
