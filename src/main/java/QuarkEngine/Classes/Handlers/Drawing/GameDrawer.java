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
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.function.Function;

public class GameDrawer implements Function{
    public static int fov = 100;
    public static double nearZ = 0.5;
    public static double farZ = 15;
    public static int vertexSize = 4;

    // Render Flags

    public static boolean renderGeometry = true;
    public static boolean drawTriangles = true;
    public static boolean drawVertecies = false;

    public static boolean renderUI = true;
    public static boolean renderDebug = true;

    public static void run(GameWindow window) {
        BufferedImage newImg = new BufferedImage(window.getContentPane().getSize().width, window.getContentPane().getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics2D imgGraphics = newImg.createGraphics();
        // imgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int aspectRatio = window.getContentPane().getSize().width/window.getContentPane().getSize().height;

        // ------------------------------------------------------------------------------------------------- //
        //  Geometry
        // ------------------------------------------------------------------------------------------------- //

        if (renderGeometry) {
            GameManager.StaticInstance.ReadObjectList().sort((o1, o2) -> (int) (o1.getPos().z - o2.getPos().z));
            for (PhysicalObject obj : GameManager.StaticInstance.ReadObjectList()) {

                Shape3D shape = obj.getShape();
                if(drawTriangles) {
                    for (Face3D face : shape.faces) {
                        if (!Objects.isNull(face)) {
                            Vector3D reverseCamVector = new Vector3D(GameManager.getCameraPos().x, -GameManager.getCameraPos().y, GameManager.getCameraPos().z);

                            Vector3D vert1 = JPerspective.RotatePoint3D( JPerspective.RotatePoint3D(new HmgVector3D(face.vert1.vertex, 1), obj.getRot()).add(new HmgVector3D(obj.getPos().add(reverseCamVector), 0)), GameManager.getCameraRot());
                            Vector3D vert2 = JPerspective.RotatePoint3D( JPerspective.RotatePoint3D(new HmgVector3D(face.vert2.vertex, 1), obj.getRot()).add(new HmgVector3D(obj.getPos().add(reverseCamVector), 0)), GameManager.getCameraRot());
                            Vector3D vert3 = JPerspective.RotatePoint3D( JPerspective.RotatePoint3D(new HmgVector3D(face.vert3.vertex, 1), obj.getRot()).add(new HmgVector3D(obj.getPos().add(reverseCamVector), 0)), GameManager.getCameraRot());
                            if (true) {
                                Vector3D surfaceNorm = VectorOperation.CrossProduct3D(vert2.sub(vert1), vert3.sub(vert1)).normalize();
                                Vector3D camToTrglVector = vert1.sub(GameManager.getCameraPos());

                                double dotProduct = VectorOperation.DotProduct3D(camToTrglVector, surfaceNorm);
                                // System.out.println(surfaceNorm.x + ", " + surfaceNorm.y + ", " + surfaceNorm.z);
                                // System.out.println(dotProduct);

                                if (dotProduct <= 0) {
                                    Polygon triangle = new Polygon();

                                    Point2D.Double projectedPos = JPerspective.Project3D(new HmgVector3D(vert1, 1), aspectRatio, fov, nearZ, farZ);
                                    triangle.addPoint((int) Math.ceil(((window.getContentPane().getSize().width*0.5) + (projectedPos.x * (window.getContentPane().getSize().width*0.5)))), (int) (Math.ceil(((window.getContentPane().getSize().height*0.5) - (projectedPos.y * (window.getContentPane().getSize().width*0.5))))));

                                    projectedPos = JPerspective.Project3D(new HmgVector3D(vert2, 1), aspectRatio, fov, nearZ, farZ);
                                    triangle.addPoint((int) Math.ceil(((window.getContentPane().getSize().width*0.5) + (projectedPos.x * (window.getContentPane().getSize().width*0.5)))), (int) (Math.ceil(((window.getContentPane().getSize().height*0.5) - (projectedPos.y * (window.getContentPane().getSize().width*0.5))))));

                                    projectedPos = JPerspective.Project3D(new HmgVector3D(vert3, 1), aspectRatio, fov, nearZ, farZ);
                                    triangle.addPoint((int) Math.ceil(((window.getContentPane().getSize().width*0.5) + (projectedPos.x * (window.getContentPane().getSize().width*0.5)))), (int) (Math.ceil(((window.getContentPane().getSize().height*0.5) - (projectedPos.y * (window.getContentPane().getSize().width*0.5))))));

                                    imgGraphics.setColor(new Color(255,255,255, 255));
                                    imgGraphics.drawPolygon(triangle);
                                }
                            }
                        }
                    }
                }

                if(drawVertecies) { // maaaan, who needs vertices anyway - deth.
                    for (Vector3D pt : shape.vertexes) {
                        if (!Objects.isNull(pt)) {
                            Point2D.Double projectedNormalizedPos = JPerspective.Project3D(JPerspective.RotatePoint3D(new HmgVector3D(pt, 1), obj.getRot()).add(new HmgVector3D(obj.getPos().add(GameManager.getCameraPos()), 0)), aspectRatio, fov, nearZ, farZ);

                            Point projectedPos = new Point((int) (projectedNormalizedPos.x * (window.getContentPane().getSize().width*0.5)), (int) (projectedNormalizedPos.y * (window.getContentPane().getSize().width*0.5)));

                            imgGraphics.setColor(new Color(235,20,20));
                            imgGraphics.fillOval((int) (((window.getContentPane().getSize().width*0.5) + projectedPos.x) - (vertexSize*0.5)), (int) (((window.getContentPane().getSize().height*0.5) - projectedPos.y) - (vertexSize*0.5)), vertexSize,vertexSize);
                        }
                    }
                }
            }
        }

        // ------------------------------------------------------------------------------------------------- //
        //  UI
        // ------------------------------------------------------------------------------------------------- //

        int crosshairXPos = (int) (window.getContentPane().getSize().width*0.5);
        int crosshairYPos = (int) (window.getContentPane().getSize().height*0.5);

        imgGraphics.setColor(new Color(255, 255, 255));

        if (renderUI) {
            imgGraphics.fillRect(crosshairXPos - 7,crosshairYPos - 1,14,2);
            imgGraphics.fillRect(crosshairXPos - 1,crosshairYPos - 7,2,14);
        }

        // ------------------------------------------------------------------------------------------------- //
        //  Debug
        // ------------------------------------------------------------------------------------------------- //

        if (renderDebug) {
            imgGraphics.drawString("FPS: " + ProgramRuntimeManager.FPSCount, 10, 20);
            imgGraphics.drawString("FPS High: " + ProgramRuntimeManager.FPSHigh, 10, 35);
            imgGraphics.drawString("Cam Pos: " + Math.round(GameManager.getCameraPos().x*10)*0.1 + ", " + Math.round(GameManager.getCameraPos().y*10)*0.1 + ", " + Math.round(GameManager.getCameraPos().z*10)*0.1, 10, 50);
        }

        window.UpdateWindow(newImg);
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
