package QuarkEngine.Classes.types.JPrograms;

import javax.naming.Name;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ProgramWindow extends JFrame {
    public static List<ProgramWindow> ProgramWindowsArray = new ArrayList<ProgramWindow>();
    protected JLabel label;
    protected ImageIcon image;
    protected BufferedImage bufferedWindow;
    private ProgramWindow thisWindow;
    public ProgramWindow(Dimension size) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Java Window");

        this.setSize(size);

        bufferedWindow = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_RGB);
        image = new ImageIcon(bufferedWindow);

        label = new JLabel(image);
        this.add(label);
        this.pack();

        thisWindow = this;
        ProgramWindowsArray.add(this);
    }

    public void UpdateWindow(BufferedImage imageReplace) {
        label.setIcon(new ImageIcon(imageReplace));
    }
    public JLabel getLabel() {
        return this.label;
    }
}
