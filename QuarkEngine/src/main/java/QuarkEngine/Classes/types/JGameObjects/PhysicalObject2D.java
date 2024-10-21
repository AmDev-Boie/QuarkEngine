package QuarkEngine.Classes.types.JGameObjects;

import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.types.JGeometry.Shape3D;
import QuarkEngine.Classes.types.JMath.Quaternion;
import QuarkEngine.Classes.types.JMath.Vector3D;

import java.awt.image.BufferedImage;
import java.util.List;

public class PhysicalObject2D {
    protected Vector3D pos;
    protected Vector3D size;
    protected short rot;
    protected BufferedImage sprite;

    public PhysicalObject2D(BufferedImage sprite) {
        this.pos = new Vector3D(0,0, 0);
        this.size = new Vector3D(1,1, 1);
        this.rot = 0;
        this.sprite = sprite;

        PhysicalObject2D thisObj = this;

        synchronized (GameManager.StaticInstance) {
            List<PhysicalObject2D> physicalObjects = GameManager.StaticInstance.ReadObjectList2D();
            physicalObjects.add(thisObj);
            try {
                GameManager.StaticInstance.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            GameManager.StaticInstance.WriteObjectList2D(physicalObjects);
        }
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public Vector3D getPos() {
        return pos;
    }

    public Vector3D getSize() {
        return size;
    }

    public short getRot() {
        return rot;
    }

    public void setPos(Vector3D value) {
        this.pos = value;
    }

    public void setSize(Vector3D value) {
        this.size = value;
    }

    public void setRot(short value) {
        this.rot = value;
    }
}
