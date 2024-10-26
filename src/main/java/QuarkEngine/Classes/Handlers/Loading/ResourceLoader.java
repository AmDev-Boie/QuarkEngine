package QuarkEngine.Classes.Handlers.Loading;

import QuarkEngine.Classes.Handlers.JIO.FileIO;
import QuarkEngine.Classes.Handlers.JIO.Parsers.ObjParser;
import QuarkEngine.Classes.types.JGeometry.Shape3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ResourceLoader {
    public static Shape3D[] ModelArray = new Shape3D[99999];
    public static BufferedImage[] TextureArray = new BufferedImage[99999];

    // Textures

    public static void loadTexturesToMemory() {
        try {
            TextureArray[0] = ImageIO.read(ResourceLoader.class.getResource("./src/main/resources/Textures/MissingTexture.png"));
        } catch (IOException e) {
            System.out.println(new RuntimeException(e).getMessage());
        }
    }

    // Models

    public static void loadModelsToMemory() {
        ModelArray[0] = ObjParser.ParseObjFile("./src/main/resources/models/BaseShapes/Cube.obj");
        ModelArray[1] = ObjParser.ParseObjFile("./src/main/resources/models/BaseShapes/Monkey.obj");
        ModelArray[2] = ObjParser.ParseObjFile("./src/main/resources/models/BaseShapes/Test.obj");
    }

    public static Shape3D GetModelByID(int id) {
        return ModelArray[id];
    }
}
