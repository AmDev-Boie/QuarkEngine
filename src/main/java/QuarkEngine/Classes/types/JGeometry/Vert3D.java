package QuarkEngine.Classes.types.JGeometry;

import QuarkEngine.Classes.types.JMath.Vector3D;

import java.awt.geom.Point2D;

public class Vert3D {
    public Vector3D vertex;
    public Point2D.Double textureVertex;
    public Vector3D vertexNorm;

    public Vert3D(Vector3D vertex, Point2D.Double textureVertex, Vector3D vertexNorm) {
        this.vertex = vertex;
        this.textureVertex = textureVertex;
        this.vertexNorm = vertexNorm;
    }
}
