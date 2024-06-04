package scene;

import com.google.gson.*;
import geometries.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WriteJsonExample {

    public static void write(Scene scene, String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Geometries.class, new GeometriesAdapter())
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write object to JSON file
            gson.toJson(scene, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene read(String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Geometries.class, new GeometriesAdapter())
                .create();

        try (FileReader reader = new FileReader(fileName)) {
            // Read JSON file and convert to object
            return gson.fromJson(reader, Scene.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GeometriesAdapter implements JsonSerializer<Geometries>, JsonDeserializer<Geometries> {

        @Override
        public JsonElement serialize(Geometries src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray jsonArray = new JsonArray();
            for (Intersectable intersectable : src.getGeometries() ) {
                JsonObject jsonObject = context.serialize(intersectable).getAsJsonObject();
                jsonObject.addProperty("type", intersectable.getClass().getSimpleName());
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }

        @Override
        public Geometries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray jsonArray = json.getAsJsonArray();
            List<Intersectable> geometries = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String type = jsonObject.get("type").getAsString();
                Intersectable intersectable = null;
                switch (type) {
                    case "Sphere":
                        intersectable = context.deserialize(jsonObject, Sphere.class);
                        break;
                    case "Triangle":
                        intersectable = context.deserialize(jsonObject, Triangle.class);
                        break;
                    case "Polygon":
                        intersectable=context.deserialize(jsonObject, Polygon.class);
                       break;
                    case "Plane":
                        intersectable=context.deserialize(jsonObject, Plane.class);
                        break;
                }
                if (intersectable != null) {
                    geometries.add(intersectable);
                }
            }
            Geometries geometriesObject = new Geometries();
            geometriesObject.add(geometries.toArray(new Intersectable[0]));
            return geometriesObject;
        }
    }
}
