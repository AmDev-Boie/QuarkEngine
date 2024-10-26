package QuarkEngine.Classes.types.JPrograms.Console;

import QuarkEngine.Classes.types.JPrograms.ProgramWindow;

import java.awt.*;

public class ConsoleWindow extends ProgramWindow {
    public static final int TEXT_DISTANCE_FROM_EDGE = 50;
    public int windowScroll = 0;
    public static List Consoles = new List();
    private ProgramWindow thisConsole;
    public ConsoleWindow() {
        super(new Dimension(400, 300));
        setTitle("Java Console");
        thisConsole = this;
    }
}
