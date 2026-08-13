package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.MyAlert;
import com.studentattendance.models.TeacherAssistant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTeacherAssistantController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField name;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private TextField phoneNumber1;
    @FXML
    private TextField phoneNumber2;
    @FXML
    private TextField address;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set TextFiled to accept Numbers Only
        phoneNumber1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                phoneNumber1.setText(newValue.replaceAll("\\D", ""));
            }
        });
        phoneNumber2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                phoneNumber2.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    public void onRadioButtonToggle(ActionEvent actionEvent) {
        ((actionEvent.getSource() == male) ? male : female).setSelected(true);
        ((actionEvent.getSource() == male) ? female : male).setSelected(false);

    }
    public void onAddTeacherAssistant() {
        if (isValid() && !isExist()) {
            TeacherAssistant teacherAssistant = new TeacherAssistant(name.getText(), ((male.isSelected() ? "Male" : "Female")), address.getText(), userName.getText(), password.getText());
            teacherAssistant.setPhoneNumber(phoneNumber1.getText(), phoneNumber2.getText());
            model.addTeacherAssistant(teacherAssistant);
            MyAlert.informationAlert("The teacherAssistant has been added successfully", "Done", null);
        }
        else if (isValid()) {
                MyAlert.errorAlert("The teacherAssistant already added!", "Error", null);
            }
        else {
            MyAlert.errorAlert("All fields must be entered!", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.SYSTEM_MANGER_FXML);
    }
    private boolean isExist() {
        for (TeacherAssistant teacherAssistant : model.getTeacherAssistants()) {
            if (teacherAssistant.getName().equals(name.getText())
                    || teacherAssistant.getUserName().equals(userName.getText())
                    || teacherAssistant.getPassword().equals(password.getText())) {
                return true;
            }
        }
        return false;
    }
    private boolean isValid() {
        return !name.getText().isEmpty()
                && (!phoneNumber1.getText().isEmpty() || !phoneNumber2.getText().isEmpty())
                && !address.getText().isEmpty()
                && !userName.getText().isEmpty()
                && !password.getText().isEmpty();
    }
}
