package QuarkEngine.Classes.types.JMath;

public class HmgVector3D extends Vector3D {
    public double w;

    public HmgVector3D(double x, double y, double z, double w) {
        super(x,y,z);
        this.w = w;
    }

    public HmgVector3D(Vector3D pt, double w) {
        super(pt.x, pt.y, pt.z);
        this.w = w;
    }

    public HmgVector3D add(HmgVector3D point) {
        return new HmgVector3D(this.x + point.x, this.y + point.y, this.z + point.z, this.w + point.w);
    }

    public HmgVector3D sub(HmgVector3D point) {
        return new HmgVector3D(this.x - point.x, this.y - point.y, this.z - point.z, this.w - point.w);
    }

    public HmgVector3D mul(HmgVector3D point) {
        return new HmgVector3D(this.x * point.x, this.y * point.y, this.z * point.z, this.w * point.w);
    }

    public HmgVector3D div(HmgVector3D point) {
        return new HmgVector3D(this.x / point.x, this.y / point.y, this.z / point.z, this.w / point.w);
    }

    public double[] ToArray() {
        return new double[]{this.x, this.y, this.z, this.w};
    }
}
