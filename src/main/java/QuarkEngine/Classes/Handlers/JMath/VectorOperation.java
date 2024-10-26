package QuarkEngine.Classes.Handlers.JMath;

import QuarkEngine.Classes.types.JMath.Vector3D;

public class VectorOperation {
    public static double DotProduct3D(Vector3D q, Vector3D p){

        double scalarproduct = 0.0;
        scalarproduct = scalarproduct + q.x * p.x;
        scalarproduct = scalarproduct + q.y * p.y;
        scalarproduct = scalarproduct + q.z * p.z;
        return scalarproduct;
    }

    public static Vector3D CrossProduct3D(Vector3D vector1, Vector3D vector2) {
        double crossX = (vector1.y * vector2.z) - (vector1.z * vector2.y);
        double crossY = (vector1.z * vector2.x) - (vector1.x * vector2.z);
        double crossZ = (vector1.x * vector2.y) - (vector1.y * vector2.x);

        return new Vector3D(crossX, crossY, crossZ);
    }
}
