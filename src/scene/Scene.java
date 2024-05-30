package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Scene class represents a scene in the ray tracer
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries=new Geometries();

    public Scene(String name){
        this.name=name;
    }
    /**
     * Set the background color of the scene
     * @param background the background color of the scene
     * @return the scene
     */
    public Scene setBackground(Color background){
        this.background=background;
        return this;
    }
    /**
     * Set the ambient light of the scene
     * @param ambientLight the ambient light of the scene
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight){
        this.ambientLight=ambientLight;
        return this;
    }




}