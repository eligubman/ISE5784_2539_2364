package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

public class Camera implements Cloneable {
    private Point location;
    private Vector right,up,to;
    private double height=0.0, width=0.0,distance=0.0;

    private Camera(){}

    public double getDistance() {
        return distance;
    }
    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }

    static public Builder getBuilder(){
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

    public static class Builder{
        private final Camera camera = new Camera();

        public Builder setLocation(Point p){
            this.camera.location=p;
            return this;
        }

        public Builder setDirection(Vector to,Vector up){
            if(to.dotProduct(up)!=0)
                throw new IllegalArgumentException("The Vectors are not orthogonal");
            this.camera.to=to.normalize();
            this.camera.up=up.normalize();
            return this;
        }

        public Builder setVpSize(double height,double width){
            if(width<0){
                throw new IllegalArgumentException("Width are negative");
            } else if (height<0) {
                throw new IllegalArgumentException("Height are negative");
            }
            this.camera.width=width;
            this.camera.height=height;
            return this;
        }

        public Builder setVpDistance(double distance){
            if (distance<0)
                throw new IllegalArgumentException("distance are negative");
            this.camera.distance=distance;
            return this;
        }

        public Camera build() throws CloneNotSupportedException {
            final String description = "One or more value are missing";
            final String ClassName = "Builder";
            if(this.camera.distance==0.0)
                throw new MissingResourceException(description,ClassName,"Missing distance");
            if(this.camera.width==0.0)
                throw new MissingResourceException(description,ClassName,"Missing width");
            if(this.camera.height==0.0)
                throw new MissingResourceException(description,ClassName,"Missing height");
            this.camera.right=this.camera.up.crossProduct(this.camera.to);

            return (Camera) camera.clone();
        }
    }
}
