package QuarkEngine.Classes.types.GUI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends GUIComp {
    public BufferedImage Texture;
    public int BorderStroke = 0;

    public Frame() {

    }

    public void DrawComp(Dimension ScreenSize, Graphics imgGraphics) {
        if (Texture == null) {
            imgGraphics.fillRect(this.position.width, this.position.height, this.offset.width, this.offset.height);
        } else {
            imgGraphics.drawImage(Texture, this.position.width, this.position.height, null);
        }
    }
}
