package QuarkEngine.Classes.Handlers.Drawing;

import Game.Advanced;
import Game.Init;
import QuarkEngine.Classes.Handlers.JGeometryHandlers.JPerspective;
import QuarkEngine.Classes.Handlers.JMath.VectorOperation;
import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.Handlers.Managers.ProgramRuntimeManager;
import QuarkEngine.Classes.types.GUI.GUIComp;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject2D;
import QuarkEngine.Classes.types.JGeometry.Face3D;
import QuarkEngine.Classes.types.JGeometry.Shape3D;
import QuarkEngine.Classes.types.JMath.HmgVector3D;
import QuarkEngine.Classes.types.JMath.Vector3D;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;
import QuarkEngine.Classes.types.Utils.GraphicsUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Objects;
import java.util.Vector;
import java.util.function.Function;

public class GameDrawer2D implements Function{
    // Render Flags
    public static boolean renderSprites = true;

    public static boolean renderUI = true;
    public static boolean renderDebug = true;
    public static boolean renderGUI = true;

    // Optimization Variables

    private static Dimension WindowSize;

    private static BufferedImage newImg;
    private static BufferedImage dataImg;
    private static Graphics2D imgGraphics;

    // Custom ImageDrawing Method (GPT ahh code because I couldn't find good docs on the raster class :sob:)
    // [edit after that comment]: CHANGED THE CODE BECAUSE GPT HAD A BILLION DIVISIONS IN THE LOOP, THEN DIDNT EVEN BOTHER USE FLOATS. WTF CHAT-GPT.
    public static void DrawRectCustom(BufferedImage sourceImage, Dimension location, Dimension size) {
        if (newImg == null) {
            throw new IllegalStateException("Static image has not been initialized.");
        }

        // Get the source Raster from the BufferedImage
        Raster sourceRaster = sourceImage.getRaster();

        // Get the WritableRaster from the static BufferedImage to modify its pixel data
        WritableRaster staticRaster = newImg.getRaster();

        // Calculate scaling factors for resizing
        float scaleX = 1f/((float) size.width / sourceImage.getWidth());
        float scaleY = 1f/((float) size.height / sourceImage.getHeight());

        // Iterate over the destination area in the static image
        for (int y = 0; y < size.height; y++) {
            for (int x = 0; x < size.width; x++) {
                // Calculate corresponding source coordinates, with bounds checking
                int srcX = (int) (x * scaleX);
                int srcY = (int) (y * scaleY);

                // Check bounds to avoid ArrayIndexOutOfBoundsException
                if (srcX >= 0 && srcX < sourceImage.getWidth() && srcY >= 0 && srcY < sourceImage.getHeight()) {
                    // Get pixel data from the source image
                    int[] pixel = sourceRaster.getPixel(srcX, srcY, (int[]) null);

                    // Set pixel data to the static image at the specified location, with bounds checking
                    int destX = location.width + x;
                    int destY = location.height + y;
                    if (destX >= 0 && destX < newImg.getWidth() && destY >= 0 && destY < newImg.getHeight()) {
                        staticRaster.setPixel(destX, destY, pixel);
                    }
                }
            }
        }

        // Update the static image with the modified raster data
        newImg.setData(staticRaster);
    }

    public static void run(GameWindow window) {

        // doubles as a check for the first time this method is called, and a way to update the size of the output image when needed.
        if (WindowSize != window.getContentPane().getSize()) {
            WindowSize = window.getContentPane().getSize();

            newImg = new BufferedImage(WindowSize.width, WindowSize.height, BufferedImage.TYPE_INT_RGB);
            dataImg = new BufferedImage(WindowSize.width, WindowSize.height, BufferedImage.TYPE_INT_RGB);
            imgGraphics = newImg.createGraphics();
        }

        // Camera state stuff
        Vector3D CameraPos = GameManager.getCameraPos();

        // ------------------------------------------------------------------------------------------------- //
        //  Sprites
        // ------------------------------------------------------------------------------------------------- //

        if (renderSprites) {
            for (PhysicalObject2D obj : GameManager.StaticInstance.ReadObjectList2D()) {
                BufferedImage Sprite = obj.getSprite();

                Vector3D ObjPos = obj.getPos();
                Vector3D ObjSize = obj.getSize();

                int offsetX = (int) ( ( (ObjPos.x - (ObjSize.x*0.5) )*10) + (WindowSize.width*0.5) );
                int offsetY = (int) ( ( (ObjPos.y - (ObjSize.y*0.5) )*10) + (WindowSize.height*0.5) );

                Dimension Size = new Dimension((int) (ObjSize.x*10),(int) (ObjSize.y*10));
                Dimension location = new Dimension(offsetX, offsetY);

                DrawRectCustom(Sprite, location, Size);
            }
        }

        // ------------------------------------------------------------------------------------------------- //
        //  GUI
        // ------------------------------------------------------------------------------------------------- //

        if (renderGUI) {
            // Draw UI Comps.
            for (GUIComp comp : GameManager.StaticInstance.ReadGUIComps()) {
                comp.DrawComp(WindowSize, imgGraphics); // silly thing to make this A LOT easier on myself.
            }

            // Draw special UI after all 'component' GUI has been drawn.
            new Advanced().OnGUICall(new GraphicsUtils(imgGraphics));
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
        BufferedImage newImg = new BufferedImage(WindowSize.width, WindowSize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D imgGraphics = newImg.createGraphics();

        imgGraphics.drawString("Loading Assets", (int) (WindowSize.width*0.5 - 50), (int) (WindowSize.height*0.5 - 50));

        window.UpdateWindow(newImg);
    }

    @Override
    public Object apply(Object o) {
        return null;
    }
}
