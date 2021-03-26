package GB.lesson8;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame extends JFrame {
    public ApplicationFrame() {
        setTitle("Application Frame v1.0");
        setBounds(650, 350, 260, 300);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setJMenuBar(createMenuBar());

        setLayout(new BorderLayout());

        JPanel top = createTop();
        add(top, BorderLayout.NORTH);

        Component component = top.getComponent(0);
        add(createBottom((JTextField) component), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createBottom(JTextField in) {
        JPanel bottom = new JPanel();

        bottom.setLayout(new GridLayout(6, 3));

        ButtonListener buttonListener = new ButtonListener(in);

        JButton clear = new JButton("C");
        clear.addActionListener(buttonListener);
        bottom.add(clear);

        JButton multiply = new JButton("*");
        multiply.addActionListener(buttonListener);
        bottom.add(multiply);

        JButton divisionSign = new JButton("/");
        divisionSign.addActionListener(buttonListener);
        bottom.add(divisionSign);

        JButton squareRoot = new JButton("√");
        squareRoot.addActionListener(buttonListener);
        bottom.add(squareRoot);


        JButton minus = new JButton("-");
        minus.addActionListener(buttonListener);
        bottom.add(minus);

        JButton plus = new JButton("+");
        plus.addActionListener(buttonListener);
        bottom.add(plus);

        for (int i = 9; i >= 0; i--) {
            JButton btn = new JButton(String.valueOf(i));
            btn.addActionListener(buttonListener);
            bottom.add(btn);
        }

        JButton point = new JButton(".");
//        point.addActionListener(buttonListener);
//        bottom.add(point);

        JButton calc = new JButton("=");
        calc.addActionListener(buttonListener);
        bottom.add(calc);

        return bottom;
    }

    private JPanel createTop() {
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());

        JTextField in = new JTextField();
        in.setEditable(false);

        top.add(in, BorderLayout.NORTH);

        return top;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = menuBar.add(new JMenu("File"));
        menu.add(new JMenuItem("Power Safe Mode"));

        JMenuItem exit = menu.add(new JMenuItem("Exit"));
//        exit.addActionListener(new ExitButtonListener());

        return menuBar;
    }
}
