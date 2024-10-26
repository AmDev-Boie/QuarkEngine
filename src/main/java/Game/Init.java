package Game;

import QuarkEngine.Classes.Handlers.Loading.ResourceLoader;
import QuarkEngine.Classes.Handlers.Managers.GameManager;
import QuarkEngine.Classes.Handlers.Managers.ProgramRuntimeManager;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject;
import QuarkEngine.Classes.types.JGameObjects.PhysicalObject2D;
import QuarkEngine.Classes.types.JMath.Degree3D;
import QuarkEngine.Classes.types.JMath.Quaternion;
import QuarkEngine.Classes.types.JMath.Vector3D;
import QuarkEngine.Classes.types.JPrograms.Console.ConsoleWindow;
import QuarkEngine.Classes.types.JPrograms.Game.GameWindow;
import QuarkEngine.Classes.types.JPrograms.InputMethod;
import QuarkEngine.QuarkEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Modifier;

import static QuarkEngine.Classes.Handlers.Loading.ResourceLoader.loadTexturesToMemory;

public class Init implements QuarkEngine {
    // The Init (Initializer) class is REQUIRED for games to function using this game engine (more of a framework but still)
    // From here you can run everything else in your game from here as if it's the main method.

    // Changes the Engine methods based on what type of game your making
    public static boolean ThreeD = false;

    // Variables
    private static PhysicalObject2D obj;

    // this method is used for startup stuff and loading, when this method is running the user will see a loading screen.
    @Override
    public void PreLoad() {
        loadTexturesToMemory();
    }

    // this method is called directly after "PreLoad();" and it defines the key binds you want the game to have using a 'fancy' API the engine has.
    // NOTE: you CAN also remove keybinds at a later point in runtime if nessessary.
    @Override
    public void SetInputs() {

    }

    private void StartupInit() {
        // Create Game Window
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        gameWindow.setLocation(new Point(700,300));

        // Set Mouse to lock to screen and allow for first person camera movement.
        ProgramRuntimeManager.SetLockMouseToCenter(false);

        // Start Game Loop
        // Note: DO NOT INSTANCE GAME OBJECTS BEFORE INVOKING "StartGameLoop();". its a bug ill get around to fixing another time.
        ProgramRuntimeManager.StartGameLoop(ThreeD);
    }

    // This Method is the entry point to your game; so *almost* all code will start here.
    // this method is called after "SetInputs()" finishes it's process.
    @Override
    public void Start() {
        // Abstracted to remove clutter in "Start()" method
        StartupInit();

        obj = new PhysicalObject2D(ResourceLoader.TextureArray[0]);
    }

    // This Method is the function the game will use to update on an Update Frame. so it's pretty much your main thread of game logic.
    // NOTE: This function will not do anything until after "ProgramRuntimeManager.StartGameLoop();" is invoked.
    @Override
    public void OnUpdate() {

    }
}
