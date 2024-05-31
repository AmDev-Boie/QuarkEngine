package QuarkEngine.Classes.Handlers.JGeometryHandlers;

import QuarkEngine.Classes.types.JMath.Quaternion;
import QuarkEngine.Classes.types.JMath.HmgVector3D;
import QuarkEngine.Classes.types.JMath.Vector3D;
import org.ejml.simple.SimpleMatrix;

import java.awt.geom.Point2D;

public class JPerspective {
    public static Point2D.Double Project3D(HmgVector3D pt, double aspectRatio, int fov, double nearZ, double farZ) {
        // OUT WITH THE PARTIAL PROJECTION, IN WITH THE MODERN PROJECTION MATRIX!
        // int ptX = (int) ( (( (fovLine / (fovLine + ((pt.z)*50))) * ((pt.x)*50) )));
        // int ptY = (int) ( (( (fovLine / (fovLine + ((pt.z)*50))) * ((pt.y)*50) )));

        SimpleMatrix projMatrix = GenerateProjectionMatrix(aspectRatio, fov, nearZ, farZ);

        SimpleMatrix matrixPt = new SimpleMatrix(new double[][]{
                {pt.x, 0, 0, 0},
                {pt.y, 0, 0, 0},
                {pt.z, 0, 0, 0},
                {pt.w, 0, 0, 0}
        });

        SimpleMatrix newMatrix = matrixPt.mult(projMatrix);

        Vector3D newPt = new Vector3D(newMatrix.get(0,0)/newMatrix.get(3,0), newMatrix.get(1,0)/newMatrix.get(3,0), newMatrix.get(2,0)/newMatrix.get(3,0));

        // System.out.println(newPt.x/newPt.z + ", " + newPt.y/newPt.z);
        return new Point2D.Double((newPt.x/newPt.z), (newPt.y/newPt.z));
    }

    public static Vector3D Project3D_ImgSpace(HmgVector3D pt, double aspectRatio, int fov, double nearZ, double farZ) {
        SimpleMatrix projMatrix = GenerateProjectionMatrix(aspectRatio, fov, nearZ, farZ);

        SimpleMatrix matrixPt = new SimpleMatrix(new double[][]{
                {pt.x, 0, 0, 0},
                {pt.y, 0, 0, 0},
                {pt.z, 0, 0, 0},
                {pt.w, 0, 0, 0}
        });

        SimpleMatrix newMatrix = matrixPt.mult(projMatrix);

        // System.out.println(newPt.x/newPt.z + ", " + newPt.y/newPt.z);
        return new Vector3D(newMatrix.get(0,0)/newMatrix.get(3,0), newMatrix.get(1,0)/newMatrix.get(3,0), newMatrix.get(2,0)/newMatrix.get(3,0));
    }

    public static SimpleMatrix GenerateProjectionMatrix(double aspectRatio, double fov, double nearZ, double farZ) {
        // double[] row1 = {aspectRatio*(1/Math.tan(fov/2)),0,0,0};
        // double[] row2 = {0,(1/Math.tan(fov/2)),0,0};
        // double[] row3 = {0,0,(farZ/(farZ-nearZ)),-(farZ/(farZ-nearZ))*nearZ};
        // double[] row4 = {0,0,1,0};

        double[] row1 = {1/(aspectRatio*Math.tan(fov*0.5)),0,0,0};
        double[] row2 = {0,1/(Math.tan(fov*0.5)),0,0};
        double[] row3 = {0,0,-((farZ+nearZ)/(farZ-nearZ)),-((2*farZ*nearZ)/(farZ-nearZ))};
        double[] row4 = {0,0,-1,0};

        return new SimpleMatrix(new double[][]{row1, row2, row3, row4});
    }

    public static HmgVector3D RotatePoint3D(HmgVector3D pos, Quaternion rot) {
        Quaternion normRot = rot.normalizeThis();
        SimpleMatrix rotMatrix = new SimpleMatrix(new double[][]{
                {1-(2*(Math.pow(normRot.getY(),2) + Math.pow(normRot.getZ(),2))), 2*((normRot.getX()*normRot.getY()) - (normRot.getZ()*normRot.getW())), 2*((normRot.getX()*normRot.getZ()) + (normRot.getY()*normRot.getW()))},
                {2*((normRot.getX()*normRot.getY()) + (normRot.getZ()*normRot.getW())), 1-(2*(Math.pow(normRot.getX(),2) + Math.pow(normRot.getZ(),2))), 2*((normRot.getY()*normRot.getZ()) - (normRot.getX()*normRot.getW()))},
                {2*((normRot.getX()*normRot.getZ()) - (normRot.getY()*normRot.getW())), 2*((normRot.getY()*normRot.getZ()) + (normRot.getX()*normRot.getW())), 1-(2*(Math.pow(normRot.getX(),2) + Math.pow(normRot.getY(),2)))}
        });

        SimpleMatrix posMatrix = new SimpleMatrix(new double[]{pos.x, pos.y, pos.z});

        SimpleMatrix ResultMatrix = rotMatrix.mult(posMatrix);

        return new HmgVector3D(ResultMatrix.get(0,0),ResultMatrix.get(1,0),ResultMatrix.get(2,0), 1);
    }
}
