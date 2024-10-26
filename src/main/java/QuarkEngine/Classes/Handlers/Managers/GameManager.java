package QuarkEngine.Classes.Handlers.Managers;

import QuarkEngine.Classes.types.JGameObjects.PhysicalObject2D;
import QuarkEngine.Classes.types.JMath.Degree3D;
import QuarkEngine.Classes.types.JMath.Quaternion;
import QuarkEngine.Classes.types.JMath.Vector3D;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    protected static Vector3D cameraPos = new Vector3D(0.0, 0.0, 0.0);
    protected static Quaternion cameraRot = new Degree3D(0,0,0).toQuaternion();

    protected static List<PhysicalObject> physicalObjects = new ArrayList<PhysicalObject>();
    protected static List<PhysicalObject2D> physicalObjects2D = new ArrayList<PhysicalObject2D>();
    public static final GameManager StaticInstance = new GameManager();

    public GameManager() {

    }

    public synchronized List<PhysicalObject> ReadObjectList() {
        return physicalObjects;
    }

    public synchronized void WriteObjectList(List<PhysicalObject> list) {
        physicalObjects = list;
    }

    public synchronized List<PhysicalObject2D> ReadObjectList2D() {
        return physicalObjects2D;
    }

    public synchronized void WriteObjectList2D(List<PhysicalObject2D> list) {
        physicalObjects2D = list;
    }

    public static Vector3D getCameraPos() {
        return cameraPos;
    }

    public static void setCameraPos(Vector3D value) {
        cameraPos = value;
    }

    public static Quaternion getCameraRot() {
        return cameraRot;
    }

    public static void setCameraRoT(Quaternion value) {
        cameraRot = value;
    }
}
