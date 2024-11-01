package QuarkEngine.Classes.Handlers.Loading;

import QuarkEngine.Classes.Handlers.JIO.Parsers.ObjParser;
import QuarkEngine.Classes.types.JGeometry.Shape3D;

import java.awt.image.BufferedImage;

public class ResourceLoader {
    public static Shape3D[] ModelArray = new Shape3D[99999];
    public static BufferedImage[] TextureArray = new BufferedImage[99999];

    // Textures



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
