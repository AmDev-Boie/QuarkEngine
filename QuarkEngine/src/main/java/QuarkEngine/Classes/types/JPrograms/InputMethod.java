package QuarkEngine.Classes.types.JPrograms;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.function.Function;

public class InputMethod {
    public Action func;
    public KeyStroke keyStroke;

    public InputMethod(Action func, KeyStroke keyStroke) {
        this.func = func;
        this.keyStroke = keyStroke;
    }
}
