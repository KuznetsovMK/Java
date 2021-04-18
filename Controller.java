package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    TextArea mainTextArea;

    @FXML
    TextField bottomTextField;

    public void btnOneClickAction (ActionEvent e) {
        if (!bottomTextField.getText().equals("")) {
            mainTextArea.appendText(bottomTextField.getText() + "\n");
            bottomTextField.clear();
        }

    }

    public void btnEnterAction (ActionEvent e) {
        if (!bottomTextField.getText().equals("")) {
            mainTextArea.appendText(bottomTextField.getText() + "\n");
            bottomTextField.clear();
        }

    }
}
