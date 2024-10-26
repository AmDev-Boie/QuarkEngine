package QuarkEngine.Classes.types.JMath;

import java.awt.*;
import java.awt.geom.Point2D;

public class HmgPoint2D extends Point2D.Double {
    public double w;

    public HmgPoint2D(double x, double y, double w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public HmgPoint2D(Point pt, double w) {
        this.x = pt.x;
        this.y = pt.y;
        this.w = w;
    }

    public HmgPoint2D add(HmgPoint2D point) {
        return new HmgPoint2D(this.x + point.x, this.y + point.y, this.w + point.w);
    }

    public HmgPoint2D sub(HmgPoint2D point) {
        return new HmgPoint2D(this.x - point.x, this.y - point.y, this.w - point.w);
    }

    public HmgPoint2D mul(HmgPoint2D point) {
        return new HmgPoint2D(this.x * point.x, this.y * point.y, this.w * point.w);
    }

    public HmgPoint2D div(HmgPoint2D point) {
        return new HmgPoint2D(this.x / point.x, this.y / point.y, this.w / point.w);
    }

    public int[] ToArray() {
        return new int[]{(int) this.x, (int) this.y, (int) this.w};
    }
}
