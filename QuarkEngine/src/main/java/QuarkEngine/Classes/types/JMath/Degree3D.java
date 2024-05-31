package QuarkEngine.Classes.types.JMath;

/**
 * The Degree3D class is used to represent rotation and orientation in Degrees.
 * <br></br>
 * Generally better to use than Radian3D.
 *
 * @author AmDeth
 */

public class Degree3D {
    private final double convertNum = Math.PI * 0.005555555;

    /**
     * Pitch Value
     */
    protected double x;
    /**
     * Yaw Value
     */
    protected double y;
    /**
     * Roll Value
     */
    protected double z;

    /**
     * Constructs a Degree3D, taking in a Pitch, Yaw, and Roll Value.
     */
    public Degree3D(double x,double y,double z) {
        this.x = x%360;
        this.y = y%360;
        this.z = z%360;
    }

    /**
     * Adds this Degree3D with an additional Degree3D ( D1 + D2 )
     */
    public Degree3D add(Degree3D point) {
        return new Degree3D((this.x + point.x)%360, (this.y + point.y)%360, (this.z + point.z)%360);
    }

    /**
     * Subtracts this Degree3D from another Degree3D ( D1 - D2 )
     */
    public Degree3D sub(Degree3D point) {
        return new Degree3D((this.x - point.x)%360, (this.y - point.y)%360, (this.z - point.z)%360);
    }

    /**
     * Multiplies this Degree3D by another Degree3D ( D1 * D2 )
     */
    public Degree3D mul(Degree3D point) {
        return new Degree3D((this.x * point.x)%360, (this.y * point.y)%360, (this.z * point.z)%360);
    }

    /**
     * Divides this Degree3D by another Degree3D ( D1 / D2 )
     */
    public Degree3D div(Degree3D point) {
        return new Degree3D((this.x / point.x)%360, (this.y / point.y)%360, (this.z / point.z)%360);
    }

    /**
     * Converts this Degree3D to a Radian3D.
     */
    public Radian3D toRadian() {
        return new Radian3D((this.x * convertNum), (this.y * convertNum), (this.z * convertNum));
    }

    /**
     * Converts this Degree3D to a Quaternion.
     */
    public Quaternion toQuaternion() {
        Radian3D radianMeasure =  this.toRadian();
        return new Quaternion(
                (Math.cos(radianMeasure.x*0.5) * Math.cos(radianMeasure.y*0.5) * Math.cos(radianMeasure.z*0.5)) + (Math.sin(radianMeasure.x*0.5) * Math.sin(radianMeasure.y*0.5) * Math.sin(radianMeasure.z*0.5)),
                (Math.sin(radianMeasure.x*0.5) * Math.cos(radianMeasure.y*0.5) * Math.cos(radianMeasure.z*0.5)) - (Math.cos(radianMeasure.x*0.5) * Math.sin(radianMeasure.y*0.5) * Math.sin(radianMeasure.z*0.5)),
                (Math.cos(radianMeasure.x*0.5) * Math.sin(radianMeasure.y*0.5) * Math.cos(radianMeasure.z*0.5)) + (Math.sin(radianMeasure.x*0.5) * Math.cos(radianMeasure.y*0.5) * Math.sin(radianMeasure.z*0.5)),
                (Math.cos(radianMeasure.x*0.5) * Math.cos(radianMeasure.y*0.5) * Math.sin(radianMeasure.z*0.5)) + (Math.sin(radianMeasure.x*0.5) * Math.sin(radianMeasure.y*0.5) * Math.cos(radianMeasure.z*0.5))
        );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
