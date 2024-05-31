package QuarkEngine;

public interface QuarkEngine {
    /**
     * The PreLoad() Method is the first method ran after the Start() method.
     * <br></br>
     * <br></br>
     * you should use this method to load models, textures, sounds, and any other files.
     */
    public void PreLoad();
    /**
     * The Start() method is your entry point for the 'Developer --> Quark Engine' interface.
     * <br></br>
     * This is THE FIRST CODE that gets run over everything else, even PreLoad().
     * <br></br>
     * <br></br>
     * you should use this method to define the initial game state, and also do some pre-calculations.
     */
    public void Start();
    /**
     * The OnUpdate() method is invoked EVERY Game state frame, it contains the logic of your game.
     * <br></br>
     * the OnUpdate() method goes as fast as you allow FPS to go.
     * <br></br>
     * <br></br>
     * you should use this method to update the game state.
     */
    public void OnUpdate();
    /**
     * The SetInputs() method is the 3rd method invoked in the initialization pipeline. it's invoked before initial inputs get Mapped.
     * <br></br>
     * <br></br>
     * you should use this method to add Key bindings and controls to your game.
     */
    public void SetInputs();
}
