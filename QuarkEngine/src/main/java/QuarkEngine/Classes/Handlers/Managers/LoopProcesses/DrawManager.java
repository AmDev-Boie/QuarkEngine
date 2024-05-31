package QuarkEngine.Classes.Handlers.Managers.LoopProcesses;

import QuarkEngine.Classes.Handlers.Drawing.ConsoleDrawer;
import QuarkEngine.Classes.Handlers.Drawing.GameDrawer;
import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.types.JPrograms.Console.ConsoleWindow;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;
import QuarkEngine.Classes.types.JPrograms.ProgramWindow;

public class DrawManager {
    public static void DrawWindows() {
        for (ProgramWindow win : ProgramWindow.ProgramWindowsArray) {
            synchronized (GameManager.StaticInstance) {
                if (win instanceof GameWindow) {
                    GameDrawer.run((GameWindow) win);
                }

                if (win instanceof ConsoleWindow) {
                    ConsoleDrawer.run((ConsoleWindow) win);
                }
                GameManager.StaticInstance.notifyAll();
            }
        }
    }
}
