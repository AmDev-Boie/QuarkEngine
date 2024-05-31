package QuarkEngine.Classes.Handlers.Managers;

import Game.Init;
import QuarkEngine.Classes.Handlers.JIO.FileIO;
import QuarkEngine.Classes.Handlers.Managers.LoopProcesses.DrawManager;
import QuarkEngine.Classes.Handlers.Printing.QuarkPrinter;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;
import QuarkEngine.Classes.types.JPrograms.InputMethod;
import QuarkEngine.Classes.types.JPrograms.ProgramWindow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Vector;

public class ProgramRuntimeManager {
    public static volatile boolean running = true;
    public static long last_time = System.nanoTime();
    public static int FPSCap = 144;

    public static Vector<InputMethod> Inputs = new Vector<InputMethod>();
    public static GameWindow[] windows;

    public static double FPSCount = 0;
    public static double FPSHigh = 0;
    private static long lastTime;
    protected static boolean lockMouseToCenter = false;
    public static Point MouseDislocationFromCenter = new Point(0,0); // will always equal zero unless the mouse is ever unlocked from the center.

    public static double GraphicsDeltaTime;
    public static double StateDeltaTime;
    public static void StartGameLoop() {
        //---------------------------------------------------------------//
        //  Initial Function
        //---------------------------------------------------------------//

        QuarkPrinter.PrintEngineMsg("--------------------------------------");
        QuarkPrinter.PrintEngineMsg(" Quark Engine Initiation Log;");
        QuarkPrinter.PrintEngineMsg("--------------------------------------\n");

        QuarkPrinter.PrintEngineMsg("Cleaning up OutputLog.txt...\n");
        FileIO.InstanceFile("./OutputLog.txt");

        QuarkPrinter.PrintEngineMsg("Invoking 'PreLoad();' Method...\n");
        new Init().PreLoad();

        //---------------------------------------------------------------//
        //  Register Keybindings
        //---------------------------------------------------------------//

        QuarkPrinter.PrintEngineMsg("Invoking 'SetInputs();' Method...\n");
        new Init().SetInputs();

        QuarkPrinter.PrintEngineMsg("Adding KeyBinds to Input and Action Maps...");
        for (ProgramWindow win : ProgramWindow.ProgramWindowsArray) {
            if (win instanceof GameWindow) {
                for(InputMethod method : Inputs) {
                    win.getLabel().getInputMap().put(method.keyStroke,method.keyStroke);
                    win.getLabel().getActionMap().put(method.keyStroke, method.func);
                    QuarkPrinter.PrintEngineMsg("Mapped Key [" + method.keyStroke.toString() + "] To [" + method.func.toString() + "] in JFrame ['" + win.getName() + "'].");
                }
            }
        }
        QuarkPrinter.PrintEngineMsg("Added All keybindings found in 'Vector<InputMethod> Inputs'.\n");

        //---------------------------------------------------------------//
        //  Game loop threads
        //---------------------------------------------------------------//

        QuarkPrinter.PrintEngineMsg("Initiating Game Loop.\n");
        Long OptimalTime = (long) (1000000000/FPSCap);

        // FPS Counter Thread
        Thread FPSCounterThread = new Thread(new Runnable() {
            @Override
            public void run(){
                while (true){ // lazy me, add a condition for a finish able thread
                    lastTime = System.nanoTime();
                    try{
                        Thread.sleep(1000); // longer than one frame
                    }
                    catch (InterruptedException ignored){}
                    FPSCount = Math.round(1000000000.0/(System.nanoTime() - lastTime)); //one second(nano) divided by amount of time it takes for one frame to finish
                    if(FPSHigh < FPSCount) {
                        FPSHigh = FPSCount;
                    }
                    lastTime = System.nanoTime();
                }
            }
        });

        // Game State Thread
        Thread GameStateThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while(running) {
                    long time = System.nanoTime();
                    StateDeltaTime = (double) ((time - last_time)*0.00000001);
                    last_time = time;
                    try { // put in a try-catch so the game doesn't have a mental breakdown over the data not existing yet.
                        if(lockMouseToCenter) { // only do if lock mouse to center is enabled.
                            if(!Objects.isNull(ProgramWindow.ProgramWindowsArray.get(0))) {
                                Point MiddleOfMainWindow = new Point(ProgramWindow.ProgramWindowsArray.get(0).getX() + (int) Math.ceil(ProgramWindow.ProgramWindowsArray.get(0).getWidth()*0.5), ProgramWindow.ProgramWindowsArray.get(0).getY() + (int) Math.ceil(ProgramWindow.ProgramWindowsArray.get(0).getHeight()*0.5));

                                PointerInfo PtrInfo = MouseInfo.getPointerInfo();
                                Point MouseLocation = PtrInfo.getLocation();
                                MouseDislocationFromCenter = new Point(MiddleOfMainWindow.x-MouseLocation.x, MiddleOfMainWindow.y-MouseLocation.y); // measure distance from center before resetting the position (obv why).

                                Robot robot = new Robot();
                                robot.mouseMove(MiddleOfMainWindow.x, MiddleOfMainWindow.y);
                                Thread.sleep((long) (StateDeltaTime + (OptimalTime*0.0000005)));
                            }
                        }

                        // Do the user specified on update invocation after the mouse stuff has been calculated.
                        new Init().OnUpdate();
                    } catch (NullPointerException | AWTException | InterruptedException e) {
                        QuarkPrinter.PrintEngineErr(e.getMessage());
                    }

                    /*if ( ((1.0/UFPSCap) - ((time - last_time)*0.0000001)) < 0) {
                        try {
                            Thread.sleep((long) ((1.0/UFPSCap) - ((time - last_time)*0.0000001)));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }*/
                }
            }
        });

        // Graphics Thread
        Thread GraphicsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(running) {
                    long time = System.nanoTime();
                    GraphicsDeltaTime = (double) ((time - last_time)*0.000000001);
                    last_time = time;

                    DrawManager.DrawWindows();
                    /*if ( ((1.0/GFPSCap) - ((time - last_time)*0.0000001)) < 0) {
                        try {
                            Thread.sleep((long) ((1.0/GFPSCap) - ((time - last_time)*0.0000001)));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }*/
                    FPSCounterThread.interrupt();
                    try {
                        Thread.sleep((long) (GraphicsDeltaTime + (OptimalTime*0.000001)));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        GameStateThread.start();
        GraphicsThread.start();
        FPSCounterThread.start();
    }

    public static boolean GetLockMouseToCenter() {
        return lockMouseToCenter;
    }

    public static void SetLockMouseToCenter(boolean value) {
            if(!Objects.isNull(ProgramWindow.ProgramWindowsArray.get(0))) {
                if(value) {
                    BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                            cursorImg, new Point(0, 0), "blank cursor");
                    ProgramWindow.ProgramWindowsArray.get(0).getContentPane().setCursor(blankCursor);
                } else {
                    ProgramWindow.ProgramWindowsArray.get(0).setCursor(Cursor.getDefaultCursor());
                }
            }
        lockMouseToCenter = value;
    }
}
