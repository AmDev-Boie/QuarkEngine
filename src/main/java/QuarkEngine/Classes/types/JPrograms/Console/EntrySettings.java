package QuarkEngine.Classes.types.JPrograms.Console;

import java.awt.*;

public enum EntrySettings {
    MESSAGE (255,255,255, "/images/Console/MessageIco.png"),
    USER_MESSAGE (200,200,200, "/images/Console/UserInputIco.png"),
    WARNING (250,200,25, "/images/Console/WarningIco.png"),
    ERROR (220,50,25, "/images/Console/ErrorIco.png");

    private final int r;
    private final int g;
    private final int b;

    private final String iconpath;

    private EntrySettings(final int r, final int g, final int b, final String iconpath) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.iconpath = iconpath;
    }

    public Color getColor(){
        return new Color(r,g,b);
    }

    public String getIconpath() {
        return iconpath;
    }
}
