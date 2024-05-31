package QuarkEngine.Classes.types.JPrograms.Console;

import java.awt.*;

public class ConsoleEntry {
    public String entryText;
    public Color entryColor;

    public String iconFilePath;

    public ConsoleEntry(String text, Color color, String iconPath) {
        this.entryText = text;
        this.entryColor = color;
        this.iconFilePath = iconPath;
    }

    public ConsoleEntry(String text, EntrySettings settings) {
        this.entryText = text;
        this.entryColor = settings.getColor();
        this.iconFilePath = settings.getIconpath();
    }
}
