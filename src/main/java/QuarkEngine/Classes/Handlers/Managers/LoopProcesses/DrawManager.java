package QuarkEngine.Classes.Handlers.Managers.LoopProcesses;

import QuarkEngine.Classes.Handlers.Drawing.ConsoleDrawer;
import QuarkEngine.Classes.Handlers.Drawing.GameDrawer2D;
import QuarkEngine.Classes.Handlers.Drawing.GameDrawer3D;
import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.types.JPrograms.Console.ConsoleWindow;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;
import QuarkEngine.Classes.types.JPrograms.ProgramWindow;

public class DrawManager {
    public static void DrawWindows(boolean ThreeD) {
        for (ProgramWindow win : ProgramWindow.ProgramWindowsArray) {
            synchronized (GameManager.StaticInstance) {
                if (win instanceof GameWindow) {
                    if (ThreeD) {
                        GameDrawer3D.run((GameWindow) win);
                    } else {
                        GameDrawer2D.run((GameWindow) win);
                    }
                }

                if (win instanceof ConsoleWindow) {
                    ConsoleDrawer.run((ConsoleWindow) win);
                }
                GameManager.StaticInstance.notifyAll();
            }
        }
    }
}
