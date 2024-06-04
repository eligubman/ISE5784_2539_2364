package JSON;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import geometries.*;
import scene.Scene;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading and writing JSON files for Scene objects.
 */
public class Json {
    private static final String FOLDER_PATH = System.getProperty("user.dir") + "/Files/JSON";

    /**
     * Writes a Scene object to a JSON file.
     *
     * @param scene    the Scene object to write
     * @param fileName the name of the file to write to
     */
    public static void write(Scene scene, String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Geometries.class, new JSON.Json.GeometriesAdapter())
                .setPrettyPrinting()
                .create();

        // Ensure the directory exists
        File folder = new File(FOLDER_PATH);
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create directory: " + FOLDER_PATH);
            return;
        }

        try (FileWriter writer = new FileWriter(FOLDER_PATH + '/' + fileName)) {
            // Write object to JSON file
            gson.toJson(scene, writer);
            System.out.println("File written successfully to: " + FOLDER_PATH + '/' + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a Scene object from a JSON file.
     *
     * @param fileName the name of the file to read from
     * @return the Scene object read from the file, or null if an error occurs
     */
    public static Scene read(String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Geometries.class, new JSON.Json.GeometriesAdapter())
                .create();

        // Check if the file exists
        File file = new File(FOLDER_PATH + '/' + fileName);
        if (!file.exists()) {
            System.err.println("File not found: " + FOLDER_PATH + '/' + fileName);
            return null;
        }

        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            // Read JSON file and convert to object
            Scene scene = gson.fromJson(reader, Scene.class);
            System.out.println("File read successfully from: " + FOLDER_PATH + '/' + fileName);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Custom adapter for serializing and deserializing Geometries objects.
     */
    private static class GeometriesAdapter implements JsonSerializer<Geometries>, JsonDeserializer<Geometries> {

        /**
         * Serializes a Geometries object to JSON.
         *
         * @param src       the Geometries object to serialize
         * @param typeOfSrc the type of the source object
         * @param context   the JSON serialization context
         * @return the JSON element representing the Geometries object
         */
        @Override
        public JsonElement serialize(Geometries src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray jsonArray = new JsonArray();
            try {
                // Access the private field "Geometry" of the Geometries object using reflection
                Field geometryField = Geometries.class.getDeclaredField("Geometry");
                geometryField.setAccessible(true);
                List<Intersectable> geometries = (List<Intersectable>) geometryField.get(src);

                // Serialize each Intersectable object and add the type property
                for (Intersectable intersectable : geometries) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("type", intersectable.getClass().getSimpleName());
                    jsonObject.add("attributes", context.serialize(intersectable));
                    jsonArray.add(jsonObject);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return jsonArray;
        }

        /**
         * Deserializes a Geometries object from JSON.
         *
         * @param json    the JSON element to deserialize from
         * @param typeOfT the type of the target object
         * @param context the JSON deserialization context
         * @return the deserialized Geometries object
         * @throws JsonParseException if an error occurs during deserialization
         */
        @Override
        public Geometries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray jsonArray = json.getAsJsonArray();
            List<Intersectable> geometries = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String type = jsonObject.get("type").getAsString();
                JsonElement attributes = jsonObject.get("attributes");
                Intersectable intersectable = null;

                // Deserialize based on the type property
                switch (type) {
                    case "Sphere":
                        intersectable = context.deserialize(attributes, Sphere.class);
                        break;
                    case "Triangle":
                        intersectable = context.deserialize(attributes, Triangle.class);
                        break;
                    case "Cylinder":
                        intersectable = context.deserialize(attributes, Cylinder.class);
                        break;
                    case "Tube":
                        intersectable = context.deserialize(attributes, Tube.class);
                        break;
                    case "Polygon":
                        intersectable = context.deserialize(attributes, Polygon.class);
                        break;
                    case "Plane":
                        intersectable = context.deserialize(attributes, Plane.class);
                        break;
                }
                if (intersectable != null) {
                    geometries.add(intersectable);
                }
            }

            Geometries geometriesObject = new Geometries();
            try {
                // Set the private field "Geometry"
                Field geometryField = Geometries.class.getDeclaredField("Geometry");
                geometryField.setAccessible(true);
                geometryField.set(geometriesObject, geometries);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return geometriesObject;
        }
    }
}
