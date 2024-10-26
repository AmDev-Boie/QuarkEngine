package QuarkEngine.Classes.types.JGeometry;

import QuarkEngine.Classes.types.JMath.Vector3D;

import java.awt.geom.Point2D;

public class Face3D {
    public Vert3D vert1;
    public Vert3D vert2;
    public Vert3D vert3;

    public Face3D(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3, Point2D.Double vertTexture1, Point2D.Double vertTexture2, Point2D.Double vertTexture3, Vector3D vertNorm1, Vector3D vertNorm2, Vector3D vertNorm3) {
        this.vert1 = new Vert3D(vertex1, vertTexture1, vertNorm1);
        this.vert2 = new Vert3D(vertex2, vertTexture2, vertNorm2);
        this.vert3 = new Vert3D(vertex3, vertTexture3, vertNorm3);
    }
}
