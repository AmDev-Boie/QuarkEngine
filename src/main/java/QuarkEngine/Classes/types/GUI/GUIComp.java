package QuarkEngine.Classes.types.GUI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUIComp {
    public Dimension position;
    public Dimension offset;
    public Dimension scale;

    public GUIComp() {
        this.position = new Dimension(0,0);
        this.offset = new Dimension(50,50);
        this.scale = new Dimension(0,0);
    }

    public void DrawComp(Dimension ScreenSize, Graphics imgGraphics) {} // Defined in Extended classes
}
