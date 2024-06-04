package scene;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lighting.AmbientLight;
import primitives.Color;
import scene.Scene;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteJsonExample {
    public static void write(Scene scene, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write object to JSON file
            gson.toJson(scene, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene read(String fileName) {
        Gson gson = new Gson();
        try (JsonReader reader = new JsonReader(new FileReader(fileName))) {
            // Read JSON file and convert to object
            Scene scene = gson.fromJson(reader, Scene.class);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
