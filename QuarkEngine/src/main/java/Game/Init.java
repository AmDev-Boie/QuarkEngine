package Game;

import QuarkEngine.Classes.Handlers.Loading.ResourceLoader;
import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.Handlers.Managers.ProgramRuntimeManager;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject;
import QuarkEngine.Classes.types.JMath.Degree3D;
import QuarkEngine.Classes.types.JMath.Quaternion;
import QuarkEngine.Classes.types.JMath.Vector3D;
import QuarkEngine.Classes.types.JPrograms.Console.ConsoleWindow;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;
import QuarkEngine.Classes.types.JPrograms.InputMethod;
import QuarkEngine.QuarkEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Modifier;

public class Init implements QuarkEngine {
    // The Init (Initializer) class is REQUIRED for games to function using this game engine (more of a framework but still)
    // From here you can run everything else in your game from here as if it's the main method.

    public static PhysicalObject obj1;
    static double rotPerSecond = 0.2;

    private static boolean movingUp = false;
    private static boolean movingDown = false;

    private static boolean movingLeft = false;
    private static boolean movingRight = false;

    private static boolean movingForward = false;
    private static boolean movingBackward = false;

    // this method is used for startup stuff and loading, when this method is running the user will see a loading screen.
    @Override
    public void PreLoad() {
        ResourceLoader.loadModelsToMemory(); // Id recomend writing your own method to push model data to "ResourceLoader" rather than invoking the method.
    }

    // this method is called directly after "PreLoad();" and it defines the key binds you want the game to have using a 'fancy' API the engine has.
    // NOTE: you CAN also remove keybinds at a later point in runtime if nessessary.
    @Override
    public void SetInputs() {
        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingUp = true;
                    }
                },
                KeyStroke.getKeyStroke("pressed E")
        ));
        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingUp = false;
                    }
                },
                KeyStroke.getKeyStroke("released E")
        ));

        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingDown = true;
                    }
                },
                KeyStroke.getKeyStroke("Q")
        ));
        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingDown = false;
                    }
                },
                KeyStroke.getKeyStroke("released Q")
        ));

        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingLeft = true;
                    }
                },
                KeyStroke.getKeyStroke("A")
        ));
        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingLeft = false;
                    }
                },
                KeyStroke.getKeyStroke("released A")
        ));

        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingRight = true;
                    }
                },
                KeyStroke.getKeyStroke("D")
        ));
        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingRight = false;
                    }
                },
                KeyStroke.getKeyStroke("released D")
        ));

        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingForward = true;
                    }
                },
                KeyStroke.getKeyStroke("W")
        ));
        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingForward = false;
                    }
                },
                KeyStroke.getKeyStroke("released W")
        ));

        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingBackward = true;
                    }
                },
                KeyStroke.getKeyStroke("S")
        ));
        ProgramRuntimeManager.Inputs.add(new InputMethod(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movingBackward = false;
                    }
                },
                KeyStroke.getKeyStroke("released S")
        ));
    }

    // This Method is the entry point to your game; so *almost* all code will start here.
    // this method is called after "SetInputs()" finishes it's process.
    @Override
    public void Start() {

        // Create Game Window
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        gameWindow.setLocation(new Point(700,300));

        // Set Mouse to lock to screen and allow for first person camera movement.
        ProgramRuntimeManager.SetLockMouseToCenter(false);

        // Start Game Loop
        // Note: DO NOT INSTANCE GAME OBJECTS BEFORE INVOKING "StartGameLoop();". its a bug ill get around to fixing another time.
        ProgramRuntimeManager.StartGameLoop();

        obj1 = new PhysicalObject(ResourceLoader.GetModelByID(0));
        obj1.setPos(new Vector3D(0,0,5));
    }

    // This Method is the function the game will use to update on an Update Frame. so its pretty much your main thread of game logic.
    // NOTE: This function will not do anything until after "ProgramRuntimeManager.StartGameLoop();" is invoked.
    @Override
    public void OnUpdate() {
        // input stuff.
        if (movingUp) {
            GameManager.setCameraPos(GameManager.getCameraPos().add(new Vector3D(0,0.5*ProgramRuntimeManager.StateDeltaTime,0)));
        }
        if (movingDown) {
            GameManager.setCameraPos(GameManager.getCameraPos().add(new Vector3D(0,-0.5*ProgramRuntimeManager.StateDeltaTime,0)));
        }

        if (movingLeft) {
            GameManager.setCameraPos(GameManager.getCameraPos().add(new Vector3D(-0.5*ProgramRuntimeManager.StateDeltaTime,0,0)));
        }
        if (movingRight) {
            GameManager.setCameraPos(GameManager.getCameraPos().add(new Vector3D(0.5*ProgramRuntimeManager.StateDeltaTime,0,0)));
        }

        if (movingForward) {
            GameManager.setCameraPos(GameManager.getCameraPos().add(new Vector3D(0,0,-0.5*ProgramRuntimeManager.StateDeltaTime)));
        }
        if (movingBackward) {
            GameManager.setCameraPos(GameManager.getCameraPos().add(new Vector3D(0,0,0.5*ProgramRuntimeManager.StateDeltaTime)));
        }

        // Camera Turning things.
        // Quaternion AngleChange = new Degree3D(ProgramRuntimeManager.MouseDislocationFromCenter.y,ProgramRuntimeManager.MouseDislocationFromCenter.x,0).toQuaternion();
        // GameManager.setCameraRoT(GameManager.getCameraRot().multiply(AngleChange));

        // Object behavior stuff.
        // obj1.setRot(new Degree3D(((System.currentTimeMillis() * 0.001) * 360 * rotPerSecond), ((System.currentTimeMillis() * 0.001) * 360 * rotPerSecond), 0).toQuaternion());
    }
}
