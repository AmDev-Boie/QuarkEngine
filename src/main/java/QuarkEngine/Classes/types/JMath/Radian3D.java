package QuarkEngine.Classes.types.JMath;

/**
 * The Radian3D class is used to represent rotation and orientation in Radians.
 * <br></br>
 * if possible try to use Degree3D instead.
 * @author AmDeth
 */

public class Radian3D {
    private final double twoPI = 2 * Math.PI;
    private final double convertNum = 180 / Math.PI;

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
     * Constructs a Radian3D, taking in a Pitch, Yaw, and Roll Value.
     */
    public Radian3D(double x, double y, double z) {
        this.x = x%twoPI;
        this.y = y%twoPI;
        this.z = z%twoPI;
    }

    /**
     * Adds this Radian3D with an additional Radian3D ( R1 + R2 )
     */
    public Radian3D add(Radian3D point) {
        return new Radian3D((this.x + point.x)%twoPI, (this.y + point.y)%twoPI, (this.z + point.z)%twoPI);
    }

    /**
     * Subtracts this Radian3D by another Radian3D ( R1 - R2 )
     */
    public Radian3D sub(Radian3D point) {
        return new Radian3D((this.x - point.x)%twoPI, (this.y - point.y)%twoPI, (this.z - point.z)%twoPI);
    }

    /**
     * Multiplies this Radian3D by another Radian3D ( R1 * R2 )
     */
    public Radian3D mul(Radian3D point) {
        return new Radian3D((this.x * point.x)%twoPI, (this.y * point.y)%twoPI, (this.z * point.z)%twoPI);
    }

    /**
     * Divides this Radian3D by another Radian3D ( R1 / R2 )
     */
    public Radian3D div(Radian3D point) {
        return new Radian3D((this.x / point.x)%twoPI, (this.y / point.y)%twoPI, (this.z / point.z)%twoPI);
    }

    /**
     * returns this Vector3D as a Degree3D.
     */
    public Degree3D toDegree() {
        return new Degree3D((this.x * convertNum), (this.y * convertNum), (this.z * convertNum));
    }

    /**
     * Converts this Radian3D to a Quaternion.
     */
    public Quaternion toQuaternion() {
        return new Quaternion(
                (Math.cos(x*0.5) * Math.cos(y*0.5) * Math.cos(z*0.5)) + (Math.sin(x*0.5) * Math.sin(y*0.5) * Math.sin(z*0.5)),
                (Math.sin(x*0.5) * Math.cos(y*0.5) * Math.cos(z*0.5)) - (Math.cos(x*0.5) * Math.sin(y*0.5) * Math.sin(z*0.5)),
                (Math.cos(x*0.5) * Math.sin(y*0.5) * Math.cos(z*0.5)) + (Math.sin(x*0.5) * Math.cos(y*0.5) * Math.sin(z*0.5)),
                (Math.cos(x*0.5) * Math.cos(y*0.5) * Math.sin(z*0.5)) + (Math.sin(x*0.5) * Math.sin(y*0.5) * Math.cos(z*0.5))
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
