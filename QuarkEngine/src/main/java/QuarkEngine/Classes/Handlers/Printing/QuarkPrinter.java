package QuarkEngine.Classes.Handlers.Printing;

import QuarkEngine.Classes.Handlers.JIO.FileIO;

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
        FileIO.AppendFile("OutputLog.txt", " [Quark Engine]: " + "|  Log  | " + Msg);
    }

    public static void LogEngineWarn(String Msg) {
        FileIO.AppendFile("OutputLog.txt", " [Quark Engine]: " + "|Warning| " + Msg);
    }

    public static void LogEngineErr(String Msg) {
        FileIO.AppendFile("OutputLog.txt", " [Quark Engine]: " + "| Error | " + Msg);
    }
}
