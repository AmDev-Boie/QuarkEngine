import Game.Init;

public class Main {
    public static void main(String[] args) {
        // NOTE: the architecture of quark engine should be made in a way so that you don't need to alter the engine in most cases.
        // exception being if your game requires modification to the source engine framework, which is why its open.

        // [!!!] DO NOT EDIT THE FRAMEWORK UNLESS YOU KNOW WHAT YOU ARE DOING. [!!!]
        new Init().Start();
    }
}