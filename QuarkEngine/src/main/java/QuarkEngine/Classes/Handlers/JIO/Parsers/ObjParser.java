package QuarkEngine.Classes.Handlers.JIO.Parsers;

import QuarkEngine.Classes.types.JGeometry.Face3D;
import QuarkEngine.Classes.types.JGeometry.Shape3D;
import QuarkEngine.Classes.types.JMath.Vector3D;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;

import static QuarkEngine.Classes.Handlers.JIO.FileIO.ReadFile;

public class ObjParser {
    public static Shape3D ParseObjFile(String filepath) {
        List<String> contents = ReadFile(new File( filepath ));

        Vector3D[] vertexArray = new Vector3D[contents.size()];
        Point2D.Double[] vertexTextureArray = new Point2D.Double[contents.size()];
        Vector3D[] vertexNormArray = new Vector3D[contents.size()];
        Face3D[] faceArray = new Face3D[contents.size()];

        int vertexCount = 0;
        int vertexTextureCount = 0;
        int vertexNormalCount = 0;
        int faceCount = 0;

        for (String str : contents) {
            if (str.startsWith("v ")) {
                String cutLine = str.substring(2);

                double vtX = Double.parseDouble(cutLine.substring(0, cutLine.indexOf(" ")));
                cutLine = cutLine.substring(cutLine.indexOf(" ") + 1);

                double vtY = Double.parseDouble(cutLine.substring(0, cutLine.indexOf(" ")));
                cutLine = cutLine.substring(cutLine.indexOf(" ") + 1);

                double vtZ = Double.parseDouble(cutLine);

                vertexArray[vertexCount] = new Vector3D(vtX, vtY, vtZ);
                vertexCount += 1;
                // System.out.println("Vertex: " + vertexArray[vertexArray.length - 1].x + ", " + vertexArray[vertexArray.length - 1].y + ", " + vertexArray[vertexArray.length - 1].z);
            }

            if (str.startsWith("vt ")) {
                String cutLine = str.substring(3);

                double vtX = Double.parseDouble(cutLine.substring(0, cutLine.indexOf(" ")));
                cutLine = cutLine.substring(cutLine.indexOf(" ") + 1);

                double vtY = Double.parseDouble(cutLine);

                vertexTextureArray[vertexTextureCount] = new Point2D.Double(vtX, vtY);
                vertexTextureCount += 1;
                // System.out.println("Vertex Texture: " + vtX + ", " + vtY);
            }

            if (str.startsWith("vn ")) {
                String cutLine = str.substring(3);

                double vtX = Double.parseDouble(cutLine.substring(0, cutLine.indexOf(" ")));
                cutLine = cutLine.substring(cutLine.indexOf(" ") + 1);

                double vtY = Double.parseDouble(cutLine.substring(0, cutLine.indexOf(" ")));
                cutLine = cutLine.substring(cutLine.indexOf(" ") + 1);

                double vtZ = Double.parseDouble(cutLine);

                vertexNormArray[vertexNormalCount] = new Vector3D(vtX, vtY, vtZ);
                vertexNormalCount += 1;
                // System.out.println("Vertex Norm: " + vtX + ", " + vtY + ", " + vtZ);
            }

            if (str.startsWith("f ")) {
                String cutLine = str.substring(2);

                int vertNum1 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf("/")));
                cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                int textureNum1;
                if (cutLine.startsWith("/")) {
                    cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                    textureNum1 = 0;
                } else {
                    textureNum1 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf("/")));
                    cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                }
                int vertNormNum1 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf(" ")));
                cutLine = cutLine.substring(cutLine.indexOf(" ") + 1);

                int vertNum2 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf("/")));
                cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                int textureNum2;
                if (cutLine.startsWith("/")) {
                    cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                    textureNum2 = 0;
                } else {
                    textureNum2 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf("/")));
                    cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                }
                int vertNormNum2 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf(" ")));
                cutLine = cutLine.substring(cutLine.indexOf(" ") + 1);

                int vertNum3 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf("/")));
                cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                int textureNum3;
                if (cutLine.startsWith("/")) {
                    cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                    textureNum3 = 0;
                } else {
                    textureNum3 = (int) Double.parseDouble(cutLine.substring(0, cutLine.indexOf("/")));
                    cutLine = cutLine.substring(cutLine.indexOf("/") + 1);
                }
                int vertNormNum3 = (int) Double.parseDouble(cutLine);

                Point2D.Double vertTex1 = null;
                Point2D.Double vertTex2 = null;
                Point2D.Double vertTex3 = null;

                if (textureNum1 != 0) {
                    vertTex1 = vertexTextureArray[textureNum1-1];
                }

                if (textureNum2 != 0) {
                    vertTex2 = vertexTextureArray[textureNum2-1];
                }

                if (textureNum3 != 0) {
                    vertTex3 = vertexTextureArray[textureNum3-1];
                }


                faceArray[faceCount] = new Face3D(
                        vertexArray[vertNum1-1], vertexArray[vertNum2-1], vertexArray[vertNum3-1],
                        vertTex1, vertTex2, vertTex3,
                        vertexNormArray[vertNormNum1-1], vertexNormArray[vertNormNum2-1], vertexNormArray[vertNormNum3-1]
                );

                faceCount += 1;

                // System.out.println("Face: " + faceArray[faceCount-1].vert1 + " " + faceArray[faceCount-1].vert2 + " " + faceArray[faceCount-1].vert3);
            }
        }

        return new Shape3D(vertexArray, vertexTextureArray, vertexNormArray, faceArray);
    }
}
