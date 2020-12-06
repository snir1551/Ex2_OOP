package gameClient;

import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.node_data;
import com.google.gson.*;
import gameClient.util.Point3D;

import java.lang.reflect.Type;
import java.util.Map;

public class ServerJsonDeserializer implements JsonDeserializer<directed_weighted_graph>{
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray Nodes = jsonObject.get("Nodes").getAsJsonArray();
        directed_weighted_graph graph = new DWGraph_DS();
        for (JsonElement jsonValueElement : Nodes)
        {
          //  JsonElement jsonValueElement = set.getValue();
            String pos = jsonValueElement.getAsJsonObject().get("pos").getAsString();
            String[] splitPos = pos.split(",");
            double posX = Double.parseDouble(splitPos[0]);
            double posY = Double.parseDouble(splitPos[1]);
            double posZ = Double.parseDouble(splitPos[2]);
            int id = jsonValueElement.getAsJsonObject().get("id").getAsInt();
            node_data node = new NodeData(id,new Point3D(posX,posY,posZ));
            graph.addNode(node);
        }
        JsonArray Edges = jsonObject.get("Edges").getAsJsonArray();
        for (JsonElement jsonValueElement : Edges)
        {
            int src = jsonValueElement.getAsJsonObject().get("src").getAsInt();
            double weight = jsonValueElement.getAsJsonObject().get("w").getAsDouble();
            int dest = jsonValueElement.getAsJsonObject().get("dest").getAsInt();
            graph.connect(src,dest,weight);
        }
        return graph;

    }
}
