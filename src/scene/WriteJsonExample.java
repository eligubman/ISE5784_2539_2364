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
    void write() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("Objects.json")) {
            // Write object to JSON file
            Scene scene = new Scene("scene1");
            scene.setBackground(Color.BLACK);
            scene.setAmbientLight(AmbientLight.NONE);
            gson.toJson(scene, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void read() {
        Gson gson = new Gson();
        try (JsonReader reader = new JsonReader(new FileReader("Objects.json"))) {
            // Read JSON file and convert to object
            Scene scene = gson.fromJson(reader, Scene.class);

            // Print object
            System.out.println("Name: " + scene.name);
            System.out.println("Color: " + scene.background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WriteJsonExample example = new WriteJsonExample();
        example.write();
        example.read();
    }
}
