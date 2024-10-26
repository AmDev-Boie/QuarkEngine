import Game.Init;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static Process LogTerminal;
    public static void main(String[] args) {
        //========================================================================//
        // NOTE: the architecture of quark engine should be made in a way so that you don't need to alter the engine in most cases.
        // exception being if your game requires advanced modifications to the source engine framework, which is why its open.
        //
        // [!!!] DO NOT EDIT THE FRAMEWORK UNLESS YOU KNOW WHAT YOU ARE DOING. [!!!]
        //========================================================================//

        new Init().Start();
    }
}