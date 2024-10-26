package QuarkEngine.Classes.Handlers.Managers;

import QuarkEngine.Classes.types.JPrograms.Console.ConsoleEntry;

import java.util.ArrayList;
import java.util.List;

public class ConsoleManager {
    protected static List<ConsoleEntry> consoleEntries = new ArrayList<ConsoleEntry>();

    public static void AddMsg(ConsoleEntry Entry) {
        consoleEntries.add(Entry);
    }

    public static ConsoleEntry[] GetMsgs() {
        return consoleEntries.toArray(new ConsoleEntry[consoleEntries.size()]);
    }
}
