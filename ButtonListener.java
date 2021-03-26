package GB.lesson8;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    private JTextField in;

    public ButtonListener(JTextField in) {
        this.in = in;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Calculator(e, in);
    }
}
