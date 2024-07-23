package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Camera class represents a camera in 3D Cartesian coordinate system
 */
public class Camera implements Cloneable {
    private Point location;
    private Vector right;
    private Vector up;
    private Vector to;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    // threads management
    private PixelManager pixelManager;
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threadsprivate
    final int SPARE_THREADS = 2; // Spare threads if trying to use all the coresprivate
    double printInterval = 0; // printing progress percentage interval

    /**
     * Camera constructor
     */
    private Camera() {
    }

    // ***************** Getters ********************** //
    public double getDistance() {
        return distance;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    /**
     * Camera builder
     *
     * @return the builder
     */
    static public Builder getBuilder() {
        return new Builder();
    }

    /**
     * Construct Ray through a pixel
     *
     * @param nX the number of rows in the view plane
     * @param nY the number of columns in the view plane
     * @param j the x index of the pixel
     * @param i the y index of the pixel
     * @return the ray through the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Pc is the center of the view plane
        Point pC = location.add(to.scale(distance));
        // ratio of the view plane-the size of the pixel
        double rY = height / nY;
        double rX = width / nX;

        double xI = (j - (nX - 1) / 2.0) * rX;
        double yI = -(i - (nY - 1) / 2.0) * rY;

        Point pIJ = pC;
        if (!isZero(xI))
            pIJ = pIJ.add(right.scale(xI));
        if (!isZero(yI))
            pIJ = pIJ.add(up.scale(yI));

        return new Ray(location, pIJ.subtract(location));

    }

    /**
     * Render the image
     *
     * @return the camera
     */
    public Camera renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing ImageWriter", "Camera", "imageWriter");
        if (rayTracer == null)
            throw new MissingResourceException("Missing RayTracer", "Camera", "rayTracer");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        pixelManager = new PixelManager(nY, nX, printInterval);

        if (threadsCount == 0) {
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    castRay(nX, nY, i, j);
                }
            }
        } else {

            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        castRay(nX, nY, pixel.col(), pixel.row());
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }

        }

        return this;
    }

    /**
     * Cast a ray through a pixel
     *
     * @param nX the number of columns in the view plane
     * @param nY the number of rows in the view plane
     * @param column the x index of the pixel
     * @param row the y index of the pixel
     */
    private void castRay(int nX, int nY, int column, int row) {
        imageWriter.writePixel(column, row, rayTracer.traceRay(constructRay(nX, nY, column, row)));
        pixelManager.pixelDone();
    }

    /**
     * Print a grid on the image
     *
     * @param interval the interval between the lines
     * @param color the color of the grid
     * @return the camera
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("Missing ImageWriter", "Camera", "imageWriter");
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);
            }
        }
        return this;
    }

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing ImageWriter", "Camera", "imageWriter");
        imageWriter.writeToImage();
    }

    // ************************** Builder ****************************** //

    /**
     * Camera Builder
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Set the location of the camera
         *
         * @param p the location of the camera
         * @return the builder
         */
        public Builder setLocation(Point p) {
            this.camera.location = p;
            return this;
        }

        /**
         * Set the direction of the camera
         *
         * @param to the direction of the camera
         * @param up the up vector of the camera
         * @return the builder
         */
        public Builder setDirection(Vector to, Vector up) {
            if (!isZero(to.dotProduct(up)))
                throw new IllegalArgumentException("The Vectors are not orthogonal");
            this.camera.to = to.normalize();
            this.camera.up = up.normalize();
            this.camera.right = this.camera.to.crossProduct(this.camera.up).normalize();
            return this;
        }

        /**
         * Set the size of the view plane
         *
         * @param height the height of the view plane
         * @param width the width of the view plane
         * @return the builder
         */
        public Builder setVpSize(double height, double width) {
            if (width < 0) {
                throw new IllegalArgumentException("Width is negative");
            } else if (height < 0) {
                throw new IllegalArgumentException("Height is negative");
            }
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Set the distance of the view plane
         *
         * @param distance the distance of the view plane from the camera
         * @return the builder
         */
        public Builder setVpDistance(double distance) {
            if (distance < 0)
                throw new IllegalArgumentException("distance is negative");
            this.camera.distance = distance;
            return this;
        }

        /**
         * Set the ImageWriter
         *
         * @param imageWriter the ImageWriter
         * @return the builder
         */

        public Builder setImageWriter(ImageWriter imageWriter) {
            this.camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Set the RayTracer
         *
         * @param rayTracer the RayTracer
         * @return the builder
         */

        public Builder setRayTracer(RayTracerBase rayTracer) {
            this.camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Set the threads count
         *
         * @param threads the number of threads
         * @return the builder for chaining calls
         */
        public Builder setMultithreading(int threads) {
            if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");
            if (threads >= -1) this.camera.threadsCount = threads;
            else { // == -2
                int cores = Runtime.getRuntime().availableProcessors() - this.camera.SPARE_THREADS;
                this.camera.threadsCount = cores <= 2 ? 1 : cores;
            }
            return this;
        }

        /**
         * Set the debug print
         *
         * @param interval the interval between the prints
         * @return the builder for chaining calls
         */
        public Builder setDebugPrint(double interval) {
            this.camera.printInterval = interval;
            return this;
        }
        /**
         * Given double theta, rotate the camera's up and right vectors by theta degrees
         *
         * @param theta the angle of rotation in degrees clockwise
         * @return the rotated camera.
         */
        public Builder turnCamera(double theta) {
            if (theta == 0) return this; //there is nothing to turn
            this.camera.up = this.camera.up.rotateVector(this.camera.to, theta);
            this.camera.right = this.camera.right.rotateVector(this.camera.to, theta);
            return this;
        }

        /**
         * Moves the camera to a certain location to point to a single point
         *
         * @param from the camera's new location
         * @param to   the point the camera points to
         * @return the moved camera
         */
        public Builder moveCamera(Point from, Point to) {
            Vector vec;
            try {
                vec = to.subtract(from);
            } catch (IllegalArgumentException ignore) {
                throw new IllegalArgumentException("The camera cannot point at its starting location");
            }
            this.camera.location = from;
            this.camera.to = vec.normalize();
            //in order to determine Vup, we will find the intersection vector of two planes, the plane that Vto is represented
            //as its normal, and the plane that includes the Y axis and the Vto vector (as demanded in the instructions).

            //if the Vto is already on the Y axis, we will use the Z axis instead
            if (this.camera.to.equals(Vector.Y) || this.camera.to.equals(Vector.Y.scale(-1))) {
                this.camera.up = ((this.camera.to).crossProduct(Vector.Z)).crossProduct(this.camera.to).normalize();
            } else {
                this.camera.up = (this.camera.to.crossProduct(Vector.Y)).crossProduct(this.camera.to).normalize();
            }
            this.camera.right = this.camera.to.crossProduct(this.camera.up ).normalize();
            return this;
        }

        /**
         * flips the picture to left right axis
         * @return the object itself
         */
        public Builder flipCamera()
        {
            this.camera.right=this.camera.right.scale(-1);
            return this;
        }

        /**
         * Build the camera
         *
         * @return the camera
         */
        public Camera build() {
            final String description = "One or more value are missing";
            final String ClassName = "Builder";
            if (alignZero(this.camera.distance) <= 0.0)
                throw new MissingResourceException(description, ClassName, "Missing distance");

            if (alignZero(this.camera.width) <= 0.0)
                throw new MissingResourceException(description, ClassName, "Missing width");

            if (alignZero(this.camera.height) <= 0.0)
                throw new MissingResourceException(description, ClassName, "Missing height");

            if (this.camera.location == null)
                throw new MissingResourceException(description, ClassName, "Missing location");

            if (this.camera.to == null)
                throw new MissingResourceException(description, ClassName, "Missing to");

            if (this.camera.up == null)
                throw new MissingResourceException(description, ClassName, "Missing up");

            if (this.camera.imageWriter == null)
                throw new MissingResourceException(description, ClassName, "Missing ImageWriter");

            if (this.camera.rayTracer == null)
                throw new MissingResourceException(description, ClassName, "Missing RayTracer");

            if (!isZero(this.camera.to.dotProduct(this.camera.up)))
                throw new MissingResourceException(description, ClassName, "the to and up are not orthogonal");

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
