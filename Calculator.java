package GB.lesson8;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Calculator {

    private double result;
    static boolean start;

    public Calculator(ActionEvent e, JTextField in) {

        if (e.getActionCommand().equals("+") ||
                e.getActionCommand().equals("-") ||
                e.getActionCommand().equals("/") ||
                e.getActionCommand().equals("*") ||
                e.getActionCommand().equals("√"))
            start = false;


        if (start) {
            in.setText("");
            start = false;
        }

        StringBuilder stringBuilder = new StringBuilder(in.getText());

        JButton btn;

        btn = (JButton) e.getSource();

        if (e.getActionCommand().equals("C")) {
            in.setText("");

        } else if (e.getActionCommand().equals("√")) {
            result = Math.sqrt(Double.parseDouble(stringBuilder.toString()));
            in.setText(Double.toString(result));

        } else if (e.getActionCommand().equals("=")) {

            char[] chars = new char[stringBuilder.length()];

            for (int i = 0, j = 0; i < stringBuilder.length(); i++) {
                if (stringBuilder.charAt(i) == '*' || stringBuilder.charAt(i) == '/' ||
                        stringBuilder.charAt(i) == '-' || stringBuilder.charAt(i) == '+') {
                    chars[j] = stringBuilder.charAt(i);
                    j++;
                    stringBuilder.setCharAt(i, ' ');
                }
            }

            String[] strings = stringBuilder.toString().split(" ");

            for (int i = 1; i < strings.length; i++) {

                if (chars[i - 1] == '*') {
                    result = Double.parseDouble(strings[i - 1]) * Double.parseDouble(strings[i]);
                    strings[i] = Double.toString(result);

                } else if (chars[i - 1] == '/') {
                    if (Double.parseDouble(strings[i]) == 0) {
                        result = 0;
                        start = true;
                        break;
                    }
                    result = Double.parseDouble(strings[i - 1]) / Double.parseDouble(strings[i]);
                    strings[i] = Double.toString(result);

                } else if (chars[i - 1] == '-') {
                    result = Double.parseDouble(strings[i - 1]) - Double.parseDouble(strings[i]);
                    strings[i] = Double.toString(result);

                } else {
                    result = Double.parseDouble(strings[i - 1]) + Double.parseDouble(strings[i]);
                    strings[i] = Double.toString(result);
                }
            }
            in.setText(Double.toString(result));

            start = true;

        } else {
            stringBuilder.append(btn.getText());
            in.setText(stringBuilder.toString());
        }

    }
}
