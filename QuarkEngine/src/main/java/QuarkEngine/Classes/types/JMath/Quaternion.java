package QuarkEngine.Classes.types.JMath;

// CODE NOT BY ME.

// the code for this class was sourced from this GitHub post: https://gist.github.com/halfninja/572585
// credit to the Poster, and whoever they sourced their code from.

public final class Quaternion {
    private double x;
    private double y;
    private double z;
    private double w;
    //private float[] matrixs;

    public Quaternion(final Quaternion q) {
        this(q.x, q.y, q.z, q.w);
    }

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(final Quaternion q) {
        //matrixs = null;
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
    }

    public Quaternion(Vector3D axis, double angle) {
        set(axis, angle);
    }

    public double norm() {
        return Math.sqrt(dot(this));
    }

    public double getW() {
        return w;
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

    /**
     * @param axis
     *            rotation axis, unit vector
     * @param angle
     *            the rotation angle
     * @return this
     */
    public Quaternion set(Vector3D axis, double angle) {
        //matrixs = null;
        double s = (double) Math.sin(angle / 2);
        w = (double) Math.cos(angle / 2);
        x = axis.x * s;
        y = axis.y * s;
        z = axis.z * s;
        return this;
    }

    public Quaternion mulThis(Quaternion q) {
        //matrixs = null;
        double nw = (w * q.w) - (x * q.x) - (y * q.y) - (z * q.z);
        double nx = (w * q.x) + (x * q.w) + (y * q.z) - (z * q.y);
        double ny = (w * q.y) + (y * q.w) + (z * q.x) - (x * q.z);
        z = (w * q.z) + (z * q.w) + (x * q.y) - (y * q.x);
        w = nw;
        x = nx;
        y = ny;
        return this;
    }

    public Quaternion scaleThis(double scale) {
        if (scale != 1) {
            //matrixs = null;
            w *= scale;
            x *= scale;
            y *= scale;
            z *= scale;
        }
        return this;
    }

    public Quaternion divThis(double scale) {
        if (scale != 1) {
            //matrixs = null;
            w /= scale;
            x /= scale;
            y /= scale;
            z /= scale;
        }
        return this;
    }

    public double dot(Quaternion q) {
        return x * q.x + y * q.y + z * q.z + w * q.w;
    }

    public boolean equals(Quaternion q) {
        return x == q.x && y == q.y && z == q.z && w == q.w;
    }

    public Quaternion interpolateThis(Quaternion q, double t) {
        if (!equals(q)) {
            double d = dot(q);
            double qx, qy, qz, qw;

            if (d < 0f) {
                qx = -q.x;
                qy = -q.y;
                qz = -q.z;
                qw = -q.w;
                d = -d;
            } else {
                qx = q.x;
                qy = q.y;
                qz = q.z;
                qw = q.w;
            }

            double f0, f1;

            if ((1 - d) > 0.1f) {
                double angle = (double) Math.acos(d);
                double s = (double) Math.sin(angle);
                double tAngle = t * angle;
                f0 = (double) Math.sin(angle - tAngle) / s;
                f1 = (double) Math.sin(tAngle) / s;
            } else {
                f0 = 1 - t;
                f1 = t;
            }

            x = f0 * x + f1 * qx;
            y = f0 * y + f1 * qy;
            z = f0 * z + f1 * qz;
            w = f0 * w + f1 * qw;
        }

        return this;
    }

    public Quaternion multiply(final Quaternion q2) {
        // Components of the first quaternion.
        final double q1a = w;
        final double q1b = x;
        final double q1c = y;
        final double q1d = z;

        // Components of the second quaternion.
        final double q2a = q2.w;
        final double q2b = q2.x;
        final double q2c = q2.y;
        final double q2d = q2.z;

        // Components of the product.
        final double w = q1a * q2a - q1b * q2b - q1c * q2c - q1d * q2d;
        final double x = q1a * q2b + q1b * q2a + q1c * q2d - q1d * q2c;
        final double y = q1a * q2c - q1b * q2d + q1c * q2a + q1d * q2b;
        final double z = q1a * q2d + q1b * q2c - q1c * q2b + q1d * q2a;

        return new Quaternion(w, x, y, z);
    }

    public Quaternion normalizeThis() {
        return divThis(norm());
    }

    public Quaternion interpolate(Quaternion q, double t) {
        return new Quaternion(this).interpolateThis(q, t);
    }

    public Quaternion Invert() {
        return new Quaternion(-x,-y,-z,w);
    }
}