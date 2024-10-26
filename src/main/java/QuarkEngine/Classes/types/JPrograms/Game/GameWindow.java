package QuarkEngine.Classes.types.JPrograms.Game;

import QuarkEngine.Classes.types.JPrograms.ProgramWindow;

import java.awt.*;
/**
 * The main window for games made in the JavEngine
 * <p>
 * The GameWindow class represents a game view port JPanel, of which is pre-configured and seperate from the functionality of a ProgramWindow.
 * </p>
 */
public class GameWindow extends ProgramWindow {
    private ProgramWindow thisGameWindow;

    public GameWindow() {
        super(new Dimension(1000, 800));
        setTitle("Java Game");

        thisGameWindow = this;
    }

    public GameWindow(Dimension size) {
        super(size);
        setTitle("Java Game");

        thisGameWindow = this;
    }
}
