package QuarkEngine.Classes.types.JGeometry;

import QuarkEngine.Classes.types.JMath.Vector3D;

import java.awt.geom.Point2D;

public class Shape3D {
    public Vector3D[] vertexes;
    public Point2D.Double[] vertexTextures;
    public Vector3D[] vertexNorms;
    public Face3D[] faces;

    public Shape3D(Vector3D[] vertexes, Point2D.Double[] vertexTextures, Vector3D[] vertexNorms, Face3D[] faces) {
        this.vertexes = vertexes;
        this.vertexTextures = vertexTextures;
        this.vertexNorms = vertexNorms;
        this.faces = faces;
    }
}
