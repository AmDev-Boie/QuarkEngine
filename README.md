# **Quark Engine**

Quark engine is a java framework primarially used for game development, what makes this framework special is that its purely open source and written with the intention of modification. so (mostly) anybody who can understand java can contribute or modify the engine easily to implement features that i couldnt or havent gotten to, or maybe things that your specific game needs thats nowhere else. all while keeping basic tools for the 99.9% of other games to use, so ideally you shouldnt even need to touch the engine code in the first place.

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
