package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// this is where we will write the event handlers
public class Controller {

    @FXML
    private TextField nameField; // this is the id of the text field in the fxml file
    @FXML
    private Button helloBtn;
    @FXML
    private Button byeBtn;
    @FXML
    private CheckBox ourCheckBox;
    @FXML
    private Label ourLabel;

    // method that runs and initializes controllers when program starts
    @FXML
    public void initialize() {
        helloBtn.setDisable(true);
        byeBtn.setDisable(true);
    }

//    @FXML
//    public void onButtonClicked() {
//        System.out.println("Hello, " + nameField.getText());
//    }

//    @FXML
//    public void onButtonClicked(ActionEvent e) {  // can add params as well
//        System.out.println("Hello, " + nameField.getText());
//        System.out.println("The following button was pressed: " + e.getSource());
//    }

    @FXML
    public void onButtonClicked(ActionEvent e) {
        if (e.getSource().equals(helloBtn)) {
            System.out.println("Hello, " + nameField.getText());
        } else if (e.getSource().equals(byeBtn)) {
            System.out.println("Bye, " + nameField.getText());
        }

        if (ourCheckBox.isSelected()) {
            nameField.clear();
            helloBtn.setDisable(true);
            byeBtn.setDisable(true);
        }
    }

    // want to have a listener to see when a key is pressed, and if so, will enable the buttons
    // to be pressed , if no keys pressed, buttons will be disabled
    @FXML
    public void handleKeyReleased() {
        String text = nameField.getText();
        boolean disableButton = text.isEmpty() || text.trim().isEmpty();
        helloBtn.setDisable(disableButton);
        byeBtn.setDisable(disableButton);
    }

    @FXML
    public void handleChange() {
        System.out.println("The checkbox is " + (ourCheckBox.isSelected() ? "checked" : "not checked"));
    }
}
