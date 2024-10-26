package QuarkEngine.Classes.types.JGameObjects;

import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.types.JGeometry.Shape3D;
import QuarkEngine.Classes.types.JMath.Vector3D;
import QuarkEngine.Classes.types.JMath.Quaternion;

import java.util.List;

public class PhysicalObject {
    protected Vector3D pos;
    protected Quaternion rot;
    protected Shape3D shape;

    public PhysicalObject(Shape3D Shape) {
        this.pos = new Vector3D(0,0,0);
        this.rot = new Quaternion(new Vector3D(0,0,0),0);
        this.shape = Shape;

        PhysicalObject thisObj = this;

        synchronized (GameManager.StaticInstance) {
            List<PhysicalObject> physicalObjects = GameManager.StaticInstance.ReadObjectList();
            physicalObjects.add(thisObj);
            try {
                GameManager.StaticInstance.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            GameManager.StaticInstance.WriteObjectList(physicalObjects);
        }
    }

    public Shape3D getShape() {
        return shape;
    }

    public Vector3D getPos() {
        return pos;
    }

    public Quaternion getRot() {
        return rot;
    }

    public void setPos(Vector3D value) {
        this.pos = value;
    }

    public void setRot(Quaternion value) {
        this.rot = value;
    }
}
