package QuarkEngine.Classes.Handlers.Drawing;

import QuarkEngine.Classes.Handlers.JGeometryHandlers.JPerspective;
import QuarkEngine.Classes.Handlers.JMath.VectorOperation;
import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.Handlers.Managers.ProgramRuntimeManager;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject2D;
import QuarkEngine.Classes.types.JGeometry.Face3D;
import QuarkEngine.Classes.types.JGeometry.Shape3D;
import QuarkEngine.Classes.types.JMath.HmgVector3D;
import QuarkEngine.Classes.types.JMath.Vector3D;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;

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

    // Optimization Variables

    private static Dimension WindowSize;

    private static BufferedImage newImg;
    private static BufferedImage dataImg;
    private static Graphics2D imgGraphics;
    private static Graphics2D dataImgGraphics;

    // Custom ImageDrawing Method (GPT ahh code because I couldn't find good docs on the raster class :sob:)
    public static void DrawRectCustom(BufferedImage sourceImage, Dimension location, Dimension size) {
        if (newImg == null) {
            throw new IllegalStateException("Static image has not been initialized.");
        }

        // Get the source Raster from the BufferedImage
        Raster sourceRaster = sourceImage.getRaster();

        // Get the WritableRaster from the static BufferedImage to modify its pixel data
        WritableRaster staticRaster = newImg.getRaster();

        // Calculate scaling factors for resizing
        double scaleX = (double) size.width / sourceImage.getWidth();
        double scaleY = (double) size.height / sourceImage.getHeight();

        // Iterate over the destination area in the static image
        for (int y = 0; y < size.height; y++) {
            for (int x = 0; x < size.width; x++) {
                // Calculate corresponding source coordinates
                int srcX = (int) (x / scaleX);
                int srcY = (int) (y / scaleY);

                // Get pixel data from the source image
                int[] pixel = sourceRaster.getPixel(srcX, srcY, (int[]) null);

                // Set pixel data to the static image at the specified location
                staticRaster.setPixel(location.width + x, location.height + y, pixel);
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
            dataImgGraphics = dataImg.createGraphics();
        }

        // ------------------------------------------------------------------------------------------------- //
        //  Sprites
        // ------------------------------------------------------------------------------------------------- //

        if (renderSprites) {
            for (PhysicalObject2D obj : GameManager.StaticInstance.ReadObjectList2D()) {
                BufferedImage Sprite = obj.getSprite();

                Vector3D ObjPos = obj.getPos();
                Vector3D ObjSize = obj.getSize();

                Dimension Size = new Dimension((int) (ObjSize.x*10),(int) (ObjSize.y*10));
                Dimension location = new Dimension((int) (ObjPos.x - (Size.width * 0.5)), (int) (ObjPos.y + (Size.height * 0.5)));

                DrawRectCustom(Sprite, location, Size);
            }
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
