package QuarkEngine.Classes.types.JMath;

/**
 * The Vector3D class is used for anything involving 3d position.
 * @author AmDeth
 */

public class Vector3D {
    /**
     * Horizontal Coordinate
     */
    public double x;
    /**
     * Vertical Coordinate
     */
    public double y;
    /**
     * Depth Coordinate
     */
    public double z;

    /**
     * Constructs a Vector3D, taking in a horizontal, vertical, and depth coordinate.
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Adds this Vector3D with an additional Vector3D ( V1 + V2 )
     */
    public Vector3D add(Vector3D point) {
        return new Vector3D(this.x + point.x, this.y + point.y, this.z + point.z);
    }

    /**
     * Subtracts this Vector3D by another Vector3D ( V1 - V2 )
     */
    public Vector3D sub(Vector3D point) {
        return new Vector3D(this.x - point.x, this.y - point.y, this.z - point.z);
    }

    /**
     * Multiplies this Vector3D by another Vector3D ( V1 * V2 )
     */
    public Vector3D mul(Vector3D point) {
        return new Vector3D(this.x * point.x, this.y * point.y, this.z * point.z);
    }

    /**
     * Divides this Vector3D by another Vector3D ( V1 / V2 )
     */
    public Vector3D div(Vector3D point) {
        return new Vector3D(this.x / point.x, this.y / point.y, this.z / point.z);
    }

    /**
     * Returns the Length of this Vector3D from origin (0,0,0)
     */
    public double magnitude() {
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
    }

    /**
     * Returns a Normalized version of this Vector3D.
     * <br></br>
     * A normalized Vector has all its coordinate values between -1, and 1.
     */
    public Vector3D normalize() {
        return new Vector3D(
                this.x / this.magnitude(),
                this.y  / this.magnitude(),
                this.z  / this.magnitude()
        );
    }
}
