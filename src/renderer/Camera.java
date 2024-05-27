package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        // Pc is the center of the view plane
      Point pC = location.add(to.scale(distance));
        // ratio of the view plane-the size of the pixel
        double rY = height/nY;
        double rX = width/nX;

       double xI=(j-(nX-1)/2.0)*rX;
         double yI=(i-(nY-1)/2.0)*rY;

          Point pIJ = pC;
          if(!isZero(xI))
                pIJ=pIJ.add(right.scale(xI));
          if(!isZero(yI))
                pIJ=pIJ.add(up.scale(-yI));

          return new Ray(location,pIJ.subtract(location));

    }
    // ************************** Builder ****************************** //

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
           this.camera.right=this.camera.to.crossProduct(this.camera.up).normalize();
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

        public Camera build()  {
            final String description = "One or more value are missing";
            final String ClassName = "Builder";
            if(alignZero(this.camera.distance)<=0.0)
                throw new MissingResourceException(description,ClassName,"Missing distance");
            if(alignZero(this.camera.width)<=0.0)
                throw new MissingResourceException(description,ClassName,"Missing width");
            if(alignZero(this.camera.height)<=0.0)
                throw new MissingResourceException(description,ClassName,"Missing height");
            if(this.camera.location==null)
                throw new MissingResourceException(description,ClassName,"Missing location");
            if(this.camera.to==null)
                throw new MissingResourceException(description,ClassName,"Missing to");
            if(this.camera.up==null)
                throw new MissingResourceException(description,ClassName,"Missing up");
            if(!isZero(this.camera.to.dotProduct(this.camera.up)))
                throw new MissingResourceException(description, ClassName,"the to and up are not orthogonal");
            this.camera.right=this.camera.to.crossProduct(this.camera.up).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
