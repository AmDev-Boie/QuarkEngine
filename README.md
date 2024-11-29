# **Quark Engine**

Quark engine in short is a java based framework ment to solve problems in the ways mods are handled in games.
**also an attempt to a decent solution to 'game engines' as a whole.**

[!!!] QUARK ENGINE IS CURRENTLY GOING UNDER A COMPLETE REWRITE [!!!]

i became aware of aparapi api, and i am going to try and implement it into the framework, however with the way the entire game engine side was structured it requires a ground up rewrite to work.
the engine will become more optimal as i go over the old features and implement hardware acceleration and try to make better use of multithreaded behaviors.

and obc everything below probably wont last for too long

## Feature list:
- 2D & 3D Functionality
- Custom Logging
- MultiThreaded Architecture & Support
- Completely open & modifiable code
- Input Management system
- GUI Component API

### Fully customizable from initialization to runtime.
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
    
    @Override
    public void Start() {
        // Abstracted to remove clutter in "Start()" method
        StartupInit();

        obj = new PhysicalObject2D(ResourceLoader.TextureArray[0]);
        obj.setPos(new Vector3D(0,0,0));
        obj.setSize(new Vector3D(5,5,0));
    }
