package QuarkEngine.Classes.Handlers.Drawing;

import QuarkEngine.Classes.Handlers.JGeometryHandlers.JPerspective;
import QuarkEngine.Classes.Handlers.JMath.VectorOperation;
import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.Handlers.Managers.ProgramRuntimeManager;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject;
import QuarkEngine.Classes.types.JGeometry.Face3D;
import QuarkEngine.Classes.types.JGeometry.Shape3D;
import QuarkEngine.Classes.types.JMath.Quaternion;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;
import QuarkEngine.Classes.types.JMath.Degree3D;
import QuarkEngine.Classes.types.JMath.HmgVector3D;
import QuarkEngine.Classes.types.JMath.Vector3D;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class GameDrawer implements Function{
    // Render variables
    public static int fov = 100;
    public static double nearZ = 0.5;
    public static double farZ = 15;
    public static int vertexSize = 4;
    public static int[] Z_Buffer;

    // Render Flags
    public static boolean renderGeometry = true;
    public static boolean drawTriangles = true;
    public static boolean drawVertecies = false;

    public static boolean renderUI = true;
    public static boolean renderDebug = true;

    // Optimization variables
    private static Dimension LastRecordedWindowSize;
    private static int aspectRatio;

    private static BufferedImage frame1;
    private static BufferedImage frame2;
    private static Graphics2D frameG;
    private static boolean offFrame = false;

    private static int crosshairXPos;
    private static int crosshairYPos;

    public static void run(GameWindow window) {
        if (frame1 == null && frame2 == null) {
            frame1 = new BufferedImage(window.getContentPane().getSize().width, window.getContentPane().getSize().height, BufferedImage.TYPE_INT_RGB);
            frame2 = new BufferedImage(window.getContentPane().getSize().width, window.getContentPane().getSize().height, BufferedImage.TYPE_INT_RGB);
            Z_Buffer = new int[window.getContentPane().getSize().width * window.getContentPane().getSize().height];
            Arrays.fill(Z_Buffer,9999999);
        }

        // save aspect ratio updates for only when the size of the window actually changed.
        Dimension windowSize = window.getContentPane().getSize();
        if (windowSize != LastRecordedWindowSize) {
            aspectRatio = window.getContentPane().getSize().width/window.getContentPane().getSize().height;
            LastRecordedWindowSize = windowSize;

            frame1 = new BufferedImage(window.getContentPane().getSize().width, window.getContentPane().getSize().height, BufferedImage.TYPE_INT_RGB);
            frame2 = new BufferedImage(window.getContentPane().getSize().width, window.getContentPane().getSize().height, BufferedImage.TYPE_INT_RGB);

            crosshairXPos = (int) (window.getContentPane().getSize().width*0.5);
            crosshairYPos = (int) (window.getContentPane().getSize().height*0.5);
        }

        // Back buffering
        if (offFrame) {
            frameG = frame1.createGraphics();
        } else {
            frameG = frame2.createGraphics();
        }

        // ------------------------------------------------------------------------------------------------- //
        //  Geometry
        // ------------------------------------------------------------------------------------------------- //

        if (renderGeometry) {
            GameManager.StaticInstance.ReadObjectList().sort((o1, o2) -> (int) (o1.getPos().z - o2.getPos().z));
            for (PhysicalObject obj : GameManager.StaticInstance.ReadObjectList()) {
                Vector3D camPosition = GameManager.getCameraPos();
                Quaternion camRotation = GameManager.getCameraRot();

                Shape3D shape = obj.getShape();
                if(drawTriangles) {
                    for (Face3D face : shape.faces) {
                        if (!Objects.isNull(face)) {
                            Vector3D reverseCamVector = new Vector3D(camPosition.x, -camPosition.y, camPosition.z);

                            Quaternion objRot = obj.getRot();

                            Vector3D vert1 = JPerspective.RotatePoint3D( JPerspective.RotatePoint3D(new HmgVector3D(face.vert1.vertex, 1), objRot).add(new HmgVector3D(obj.getPos().add(reverseCamVector), 0)), camRotation);
                            Vector3D vert2 = JPerspective.RotatePoint3D( JPerspective.RotatePoint3D(new HmgVector3D(face.vert2.vertex, 1), objRot).add(new HmgVector3D(obj.getPos().add(reverseCamVector), 0)), camRotation);
                            Vector3D vert3 = JPerspective.RotatePoint3D( JPerspective.RotatePoint3D(new HmgVector3D(face.vert3.vertex, 1), objRot).add(new HmgVector3D(obj.getPos().add(reverseCamVector), 0)), camRotation);
                            Vector3D surfaceNorm = VectorOperation.CrossProduct3D(vert2.sub(vert1), vert3.sub(vert1));
                            Vector3D camToTrglVector = vert1.sub(camPosition);

                            double dotProduct = VectorOperation.DotProduct3D(camToTrglVector, surfaceNorm);
                            double NormalizeddotProduct = VectorOperation.DotProduct3D(camToTrglVector.normalize(), surfaceNorm.normalize());
                            // System.out.println(surfaceNorm.x + ", " + surfaceNorm.y + ", " + surfaceNorm.z);
                            // System.out.println(dotProduct);

                            if (dotProduct <= 0) {
                                int Col = (int) Math.min(Math.max(NormalizeddotProduct*-150+40, 0), 255);
                                Color ObjCol = obj.getColor();

                                Polygon triangle = new Polygon();
                                int HalfWindowWidth = (int) (LastRecordedWindowSize.width*0.5);
                                int HalfWindowHeight = (int) (LastRecordedWindowSize.height*0.5);

                                Point2D.Double projectedPos = JPerspective.Project3D(new HmgVector3D(vert1, 1), aspectRatio, fov, nearZ, farZ);
                                triangle.addPoint((int) Math.ceil((HalfWindowWidth + (projectedPos.x * HalfWindowWidth))), (int) (HalfWindowHeight - (projectedPos.y * HalfWindowWidth)));

                                projectedPos = JPerspective.Project3D(new HmgVector3D(vert2, 1), aspectRatio, fov, nearZ, farZ);
                                triangle.addPoint((int) Math.ceil((HalfWindowWidth + (projectedPos.x * HalfWindowWidth))), (int) (HalfWindowHeight - (projectedPos.y * HalfWindowWidth)));

                                projectedPos = JPerspective.Project3D(new HmgVector3D(vert3, 1), aspectRatio, fov, nearZ, farZ);
                                triangle.addPoint((int) Math.ceil((HalfWindowWidth + (projectedPos.x * HalfWindowWidth))), (int) (HalfWindowHeight - (projectedPos.y * HalfWindowWidth)));

                                frameG.setColor(new Color((int) (ObjCol.getRed()*Col*0.00392156862f),(int) (ObjCol.getGreen()*Col*0.00392156862f),(int) (ObjCol.getBlue()*Col*0.00392156862f), 255));
                                frameG.fillPolygon(triangle);
                            }
                        }
                    }
                }

                if(drawVertecies) { // maaaan, who needs vertices anyway - deth.
                    for (Vector3D pt : shape.vertexes) {
                        if (!Objects.isNull(pt)) {
                            Point2D.Double projectedNormalizedPos = JPerspective.Project3D(JPerspective.RotatePoint3D(new HmgVector3D(pt, 1), obj.getRot()).add(new HmgVector3D(obj.getPos().add(GameManager.getCameraPos()), 0)), aspectRatio, fov, nearZ, farZ);

                            Point projectedPos = new Point((int) (projectedNormalizedPos.x * (window.getContentPane().getSize().width*0.5)), (int) (projectedNormalizedPos.y * (window.getContentPane().getSize().width*0.5)));

                            frameG.setColor(new Color(235,20,20));
                            frameG.fillOval((int) (((window.getContentPane().getSize().width*0.5) + projectedPos.x) - (vertexSize*0.5)), (int) (((window.getContentPane().getSize().height*0.5) - projectedPos.y) - (vertexSize*0.5)), vertexSize,vertexSize);
                        }
                    }
                }
            }
        }

        // ------------------------------------------------------------------------------------------------- //
        //  UI
        // ------------------------------------------------------------------------------------------------- //

        frameG.setColor(new Color(255, 255, 255));

        if (renderUI) {
            frameG.fillRect(crosshairXPos - 7,crosshairYPos - 1,14,2);
            frameG.fillRect(crosshairXPos - 1,crosshairYPos - 7,2,14);
        }

        // ------------------------------------------------------------------------------------------------- //
        //  Debug
        // ------------------------------------------------------------------------------------------------- //

        if (renderDebug) {
            frameG.drawString("FPS: " + ProgramRuntimeManager.FPSCount, 10, 20);
            frameG.drawString("FPS High: " + ProgramRuntimeManager.FPSHigh, 10, 35);
            frameG.drawString("Cam Pos: " + Math.round(GameManager.getCameraPos().x*10)*0.1 + ", " + Math.round(GameManager.getCameraPos().y*10)*0.1 + ", " + Math.round(GameManager.getCameraPos().z*10)*0.1, 10, 50);
        }

        if (offFrame) {
            window.UpdateWindow(frame1);
        } else {
            window.UpdateWindow(frame2);
        }
        offFrame = !offFrame;
    }

    public static void DrawLoadingScreenToWindow(GameWindow window) {
        BufferedImage newImg = new BufferedImage(window.getContentPane().getSize().width, window.getContentPane().getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics2D imgGraphics = newImg.createGraphics();

        imgGraphics.drawString("Loading Assets", (int) (window.getContentPane().getSize().width*0.5 - 50), (int) (window.getContentPane().getSize().height*0.5 - 50));

        window.UpdateWindow(newImg);
    }

    @Override
    public Object apply(Object o) {
        return null;
    }
}
