package QuarkEngine.Classes.Handlers.Printing;

import QuarkEngine.Classes.Handlers.JIO.FileIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class QuarkPrinter {
    private static final String ANSI_CLEAR = "\033[0m";
    private static final String ANSI_ENGINE_COLOR = "\033[56m";
    private static final String ANSI_LOG_COLOR = "\033[37m";
    private static final String ANSI_WRN_COLOR = "\033[33m";
    private static final String ANSI_ERR_COLOR = "\033[31m";

    public static void PrintEngineMsg(String Msg) {
        System.out.println(ANSI_ENGINE_COLOR + " [Quark Engine]: " + ANSI_LOG_COLOR + "|  Log  | " + Msg + ANSI_CLEAR);
    }

    public static void PrintEngineWarn(String Msg) {
        System.out.println(ANSI_ENGINE_COLOR + " [Quark Engine]: " + ANSI_WRN_COLOR + "|Warning| " + Msg + ANSI_CLEAR);
    }

    public static void PrintEngineErr(String Msg) {
        System.out.println(ANSI_ENGINE_COLOR + " [Quark Engine]: " + ANSI_ERR_COLOR + "| Error | " + Msg + ANSI_CLEAR);
    }

    public static void LogEngineMsg(String Msg) {
        try {
            Files.writeString(Path.of("./OutputLog.txt"), " [Quark Engine]: " + "|  Log  | " + '"' + Msg + "\"\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LogEngineWarn(String Msg) {
        try {
            Files.writeString(Path.of("./OutputLog.txt"), " [Quark Engine]: " + "|Warning| " + '"' + Msg + "\"\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LogEngineErr(String Msg) {
        try {
            Files.writeString(Path.of("./OutputLog.txt"), " [Quark Engine]: " + "| Error | " + '"' + Msg + "\"\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
