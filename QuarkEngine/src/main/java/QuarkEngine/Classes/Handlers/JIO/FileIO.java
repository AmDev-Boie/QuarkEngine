package QuarkEngine.Classes.Handlers.JIO;

import QuarkEngine.Classes.Handlers.Printing.QuarkPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {
    public static List<String> ReadFile(File file) {
        List<String> result = new ArrayList<String>();

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                result.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return result;
    }

    public static void InstanceFile(String FilePath) {
        try (FileWriter writer = new FileWriter(FilePath)) {
            File file = new File(FilePath);
            file.createNewFile();
        } catch (IOException e) {
            QuarkPrinter.PrintEngineErr(e.getMessage());
        }
    }

    public static void InstanceFile(String FilePath, String Contents) {
        try (FileWriter writer = new FileWriter(FilePath)) {
            File file = new File(FilePath);
            file.createNewFile();
            writer.write(Contents);
        } catch (IOException e) {
            QuarkPrinter.PrintEngineErr(e.getMessage());
        }
    }

    public static void AppendFile(String FilePath, String Contents) {
        try (FileWriter writer = new FileWriter(FilePath, true)) {
            writer.write(Contents);
        } catch (IOException e) {
            QuarkPrinter.PrintEngineErr(e.getMessage());
        }
    }
}
