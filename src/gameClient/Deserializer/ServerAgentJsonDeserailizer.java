package gameClient.Deserializer;

import com.google.gson.*;
import gameClient.Agent;
import gameClient.util.Point3D;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ServerAgentJsonDeserailizer implements JsonDeserializer<ArrayList<Agent>> {
    @Override
    public ArrayList<Agent> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        System.out.println("a");
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray Agents = jsonObject.get("Agents").getAsJsonArray();
        ArrayList<Agent> arrayAgent = new ArrayList<>();
        for (JsonElement jsonValueElement : Agents)
        {
            JsonObject Agent = jsonValueElement.getAsJsonObject().get("Agent").getAsJsonObject();
            int id = Agent.getAsJsonObject().get("id").getAsInt();
            double value = Agent.getAsJsonObject().get("value").getAsDouble();
            int src = Agent.getAsJsonObject().get("src").getAsInt();
            int dest = Agent.getAsJsonObject().get("dest").getAsInt();
            double speed = Agent.getAsJsonObject().get("speed").getAsDouble();
            String pos = Agent.getAsJsonObject().get("pos").getAsString();
            String[] splitPos = pos.split(",");
            double posX = Double.parseDouble(splitPos[0]);
            double posY = Double.parseDouble(splitPos[1]);
            double posZ = Double.parseDouble(splitPos[2]);
            Point3D location = new Point3D(posX,posY,posZ);
            arrayAgent.add(new Agent(id,value,src,dest,speed,location));
        }
        return arrayAgent;
    }
}
