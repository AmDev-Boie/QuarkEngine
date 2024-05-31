package QuarkEngine.Classes.Handlers.Drawing;

import QuarkEngine.Classes.Handlers.Managers.ConsoleManager;
import QuarkEngine.Classes.types.JPrograms.Console.ConsoleEntry;
import QuarkEngine.Classes.types.JPrograms.Console.ConsoleWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Function;

public class ConsoleDrawer implements Function {
    public static void run(ConsoleWindow Console) {
        BufferedImage newImg = new BufferedImage(Console.getContentPane().getSize().width, Console.getContentPane().getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics imgGraphics = newImg.getGraphics();

        ConsoleEntry[] ConsoleMsgs = ConsoleManager.GetMsgs();

        String lastEntryMsg = "";
        int entryRepeatCount = 1;
        int totalSkipLines = 0;
        for (int i = 0; i < ConsoleMsgs.length; i++) {

            // Draw Entries
            if(lastEntryMsg.equals(ConsoleMsgs[i].entryText)) {
                imgGraphics.setColor(new Color(0,0,0,255));
                imgGraphics.fillRect(0,(10 + Console.windowScroll + (30 * (i - (totalSkipLines + 1)))),Console.getContentPane().getSize().width,25);

                imgGraphics.setColor(new Color(ConsoleMsgs[i].entryColor.getRed(),ConsoleMsgs[i].entryColor.getGreen(),ConsoleMsgs[i].entryColor.getBlue(),25));
                imgGraphics.fillRect(0,(10 + Console.windowScroll + (30 * (i - (totalSkipLines + 1)))),Console.getContentPane().getSize().width,25);

                imgGraphics.setColor(new Color(ConsoleMsgs[i].entryColor.getRed(),ConsoleMsgs[i].entryColor.getGreen(),ConsoleMsgs[i].entryColor.getBlue(),255));
                imgGraphics.fillRect(0,(10 + Console.windowScroll + (30 * (i - (totalSkipLines + 1)))),5,25);

                imgGraphics.setColor(new Color(ConsoleMsgs[i].entryColor.getRed(),ConsoleMsgs[i].entryColor.getGreen(),ConsoleMsgs[i].entryColor.getBlue(),255));
                imgGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                imgGraphics.drawString(ConsoleMsgs[i].entryText + " (x" + (entryRepeatCount + 1) + ")", Console.TEXT_DISTANCE_FROM_EDGE,(30 + Console.windowScroll + (30 * (i - (totalSkipLines + 1)))));

                try {
                    imgGraphics.drawImage(ImageIO.read(Console.getClass().getResourceAsStream(ConsoleMsgs[i].iconFilePath)),10,(12 + Console.windowScroll + (30 * (i - (totalSkipLines + 1)))),20,20,null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                entryRepeatCount += 1;
                totalSkipLines += 1;

            } else {
                imgGraphics.setColor(new Color(ConsoleMsgs[i].entryColor.getRed(),ConsoleMsgs[i].entryColor.getGreen(),ConsoleMsgs[i].entryColor.getBlue(),25));
                imgGraphics.fillRect(0,(10 + Console.windowScroll + (30 * (i - totalSkipLines))),Console.getContentPane().getSize().width,25);

                imgGraphics.setColor(new Color(ConsoleMsgs[i].entryColor.getRed(),ConsoleMsgs[i].entryColor.getGreen(),ConsoleMsgs[i].entryColor.getBlue(),255));
                imgGraphics.fillRect(0,(10 + Console.windowScroll + (30 * (i - totalSkipLines))),5,25);

                imgGraphics.setColor(new Color(ConsoleMsgs[i].entryColor.getRed(),ConsoleMsgs[i].entryColor.getGreen(),ConsoleMsgs[i].entryColor.getBlue(),255));
                imgGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                imgGraphics.drawString(ConsoleMsgs[i].entryText, Console.TEXT_DISTANCE_FROM_EDGE,(30 + Console.windowScroll + (30 * (i - totalSkipLines))));

                try {
                    imgGraphics.drawImage(ImageIO.read(Console.getClass().getResourceAsStream(ConsoleMsgs[i].iconFilePath)),10,(12 + Console.windowScroll + (30 * (i - (totalSkipLines)))),20,20,null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                entryRepeatCount = 1;
                lastEntryMsg = ConsoleMsgs[i].entryText;

            }
        }

        // Draw additional UI

        imgGraphics.setColor(new Color(100,100,100,255));
        imgGraphics.fillRect(0,Console.getContentPane().getSize().height-65,Console.getContentPane().getSize().width,65);

        imgGraphics.setColor(new Color(50,50,50,255));
        imgGraphics.fillRect(10,Console.getContentPane().getSize().height-55,Console.getContentPane().getSize().width - 200,45);

        Console.UpdateWindow(newImg);
    }

    @Override
    public Object apply(Object o) {
        return null;
    }
}
