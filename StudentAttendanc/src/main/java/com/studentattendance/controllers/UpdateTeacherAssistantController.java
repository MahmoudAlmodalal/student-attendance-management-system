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

public class UpdateTeacherAssistantController implements Initializable {
    @FXML
    private AnchorPane rootPane ;
    @FXML
    private TextField name ;
    @FXML
    private TextField TeacherAssistantNameText;
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
    @FXML
    private RadioButton female;
    @FXML
    private RadioButton male;
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
    public void onSearch() {
        boolean found = false;
        for (TeacherAssistant teacherAssistant : model.getTeacherAssistants()) {
            if (teacherAssistant.getName().equals(TeacherAssistantNameText.getText())) {
                found = true;
                name.setText(teacherAssistant.getName());
                address.setText(teacherAssistant.getAddress());
                userName.setText(teacherAssistant.getUserName());
                phoneNumber1.setText(teacherAssistant.getPhoneNumber().get(0));
                phoneNumber2.setText(teacherAssistant.getPhoneNumber().get(1));
                password.setText(teacherAssistant.getPassword());
                male.setSelected((teacherAssistant.getGender().equals("Male")));
                female.setSelected(!male.isSelected());
                break;
            }
        }
        if (!found) {
            MyAlert.errorAlert("The teacherAssistant dose not exist", "Error", null);
        }
    }
    public void onUpdate() {
        TeacherAssistant teacherAssistantUpdate = model.getTeacherAssistantByName(name.getText());
        if (isValid() && teacherAssistantUpdate != null) {
            teacherAssistantUpdate.setName(name.getText());
            teacherAssistantUpdate.setPhoneNumber(phoneNumber1.getText(), phoneNumber2.getText());
            teacherAssistantUpdate.setAddress(address.getText());
            teacherAssistantUpdate.setUserName(userName.getText());
            teacherAssistantUpdate.setGender((male.isSelected()) ? "Male" : "Female");
            teacherAssistantUpdate.setPassword(password.getText());
            MyAlert.informationAlert("The data of teacher Assistant  has been updated successfully", "Done", null);
        }
        else if (isValid()) {
                MyAlert.errorAlert("Teacher Assistant not found! ", "Error", null);
        }
        else {
            MyAlert.errorAlert("All fields must be entered!", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.SYSTEM_MANGER_FXML);
    }
    private boolean isValid() {
        return !name.getText().isEmpty()
                && (!phoneNumber1.getText().isEmpty() || !phoneNumber2.getText().isEmpty())
                && !address.getText().isEmpty()
                && !userName.getText().isEmpty()
                && !password.getText().isEmpty();
    }
}
