package scene;

import geometries.Geometries;
import geometries.Polygon;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Point;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents a scene in the ray tracer
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<>();
    //faces of the regular grid
    public Polygon[] faces;

    //scene geometric attributes
    /**
     * the size of the edge of the scene boundary on the X axis
     */
    private int xEdgeScene;
    /**
     * the size of the edge of the scene boundary on the Y axis
     */
    private int yEdgeScene;
    /**
     * the size of the edge of the scene boundary on the Z axis
     */
    private int zEdgeScene;
    /**
     * the resolution of the voxels that divide the scene
     */
    public double resolution = 10;
    /**
     * array of resolution to each axis
     */
    public double[] resolutions;

    //voxel attributes
    /**
     * hash map of all voxels in the scene- their index as the key and the list of geometric entities that intersects
     * with the voxel as the value.
     */
    public HashMap<Double3, Geometries> voxels = new HashMap<Double3, Geometries>();
    /**
     * the size of the edge of the voxel on the X axis
     */
    private double xEdgeVoxel;
    /**
     * the size of the edge of the voxel on the Y axis
     */
    private double yEdgeVoxel;
    /**
     * the size of the edge of the voxel on the Z axis
     */
    private double zEdgeVoxel;


    /**
     * Scene constructor
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Set the background color of the scene
     *
     * @param background the background color of the scene
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set the ambient light of the scene
     *
     * @param ambientLight the ambient light of the scene
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Add a light source to the scene
     *
     * @param lights the light source to change to the scene
     * @return the scene
     */
    public Scene setLight(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
    /**
     * xEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the X axis
     */
    public double getXEdgeVoxel() {
        return xEdgeVoxel;
    }



    /**
     * yEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the Y axis
     */
    public double getYEdgeVoxel() {
        return yEdgeVoxel;
    }

    /**
     * zEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the Z axis
     */
    public double getZEdgeVoxel() {
        return zEdgeVoxel;
    }

    /**
     * sets the resolution of the voxel grid from the tests
     *
     * @param resolution teh resolution of the grid
     * @return the object itself
     */
    public Scene setResolution(int resolution) {
        this.resolution = resolution;
        return this;
    }

    /**
     * calculates what voxels the scene has and the attributes of the voxels
     */
    public void calcVoxels() {
        this.setBoundary();
        this.setSceneEdges();
        this.setResolution();
        this.setVoxelsEdges();
        this.setVoxelsGeometries();
        this.setFaces();
    }

    /**
     * sets the boundary of the geometries in the scene
     */
    private void setBoundary() {
        this.geometries.boundary = this.geometries.calcBoundary();
    }

    /**
     * sets the edges of the scene
     */
    private void setSceneEdges() {
        this.xEdgeScene = this.geometries.boundary[0][1] - this.geometries.boundary[0][0];
        this.yEdgeScene = this.geometries.boundary[1][1] - this.geometries.boundary[1][0];
        this.zEdgeScene = this.geometries.boundary[2][1] - this.geometries.boundary[2][0];
    }

    /**
     * sets the resolution of the scene to divide to voxels
     */
    private void setResolution() {
        resolutions = new double[]{resolution, resolution, resolution};
    }

    /**
     * sets the attributes of the voxels
     */
    private void setVoxelsEdges() {
        this.xEdgeVoxel = ((double) this.xEdgeScene) / resolution;
        this.yEdgeVoxel = ((double) this.yEdgeScene) / resolution;
        this.zEdgeVoxel = ((double) this.zEdgeScene) / resolution;
    }

    /**
     * attaches the voxels to each geometric entity in the scene
     */
    private void setVoxelsGeometries() {
        this.voxels = this.geometries.attachVoxel(this);
    }

    /**
     * sets the regular grid faces field
     */
    private void setFaces(){
        //points of the scene regular grid
        int[][] gridBoundary = geometries.getBoundary();
        Point p1 = new Point(gridBoundary[0][0],gridBoundary[1][0], gridBoundary[2][0]);//(0,0,0)
        Point p2 = new Point(gridBoundary[0][1], gridBoundary[1][0], gridBoundary[2][0]);//(1,0,0)
        Point p3 = new Point(gridBoundary[0][0], gridBoundary[1][1], gridBoundary[2][0]);//(0,1,0)
        Point p4 = new Point(gridBoundary[0][0], gridBoundary[1][0], gridBoundary[2][1]);//(0,0,1)
        Point p5 = new Point(gridBoundary[0][1], gridBoundary[1][1], gridBoundary[2][0]);//(1,1,0)
        Point p6 = new Point(gridBoundary[0][1], gridBoundary[1][0], gridBoundary[2][1]);//(1,0,1)
        Point p7 = new Point(gridBoundary[0][0], gridBoundary[1][1], gridBoundary[2][1]);//(0,1,1)
        Point p8 = new Point(gridBoundary[0][1], gridBoundary[1][1], gridBoundary[2][1]);//(1,1,1)

        //faces of the regular grid
        Polygon bottom = new Polygon(p1, p2, p5, p3);//bottom
        Polygon front = new Polygon(p1, p2, p6, p4);//front
        Polygon left = new Polygon(p1, p3, p7, p4);//left
        Polygon up = new Polygon(p4, p6, p8, p7);//up
        Polygon behind = new Polygon(p3, p5, p8, p7);//behind
        Polygon right = new Polygon(p2, p5, p8, p6);//right

        //Memory wise, we won't use the ray function of findClosestPoint, since we will be in need to create a list of points
        this.faces = new Polygon[]{bottom, front, left, up, behind, right};
    }

}