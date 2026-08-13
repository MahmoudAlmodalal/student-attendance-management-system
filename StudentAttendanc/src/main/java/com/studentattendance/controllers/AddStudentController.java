package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
    @FXML
    private AnchorPane rootPane;
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
    private TextField id;
    @FXML
    private TextField name;
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
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                id.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }
    public void onRadioButtonToggle(ActionEvent actionEvent) {
        ((actionEvent.getSource() == male) ? male : female).setSelected(true);
        ((actionEvent.getSource() == male) ? female : male).setSelected(false);
    }
    public void onAdd() {
        Course course = model.getRegisteredCourse();
        if (isValid() && course.getStudentByNameOrId(name.getText()) == null && name.getText().split(" ").length == 4) {
                Student student = new Student(name.getText(), ((male.isSelected()) ? "Male" : "Female"), address.getText(), id.getText());
                student.setPhoneNumber(phoneNumber1.getText(), phoneNumber2.getText());
                course.addStudent(student);
                MyAlert.informationAlert("The student has been added successfully", "Done", null);
        }
        else if (isValid() && course.getStudentByNameOrId(name.getText()) == null) {
            MyAlert.errorAlert("You must enter the name is quadruple", "Error", null);
        }
        else if (isValid()) {
            MyAlert.errorAlert("The student's name already exists!", "Error", null);
        }
        else {
            MyAlert.errorAlert("All fields must be entered!", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
    private boolean isValid() {
        return !name.getText().isEmpty()
                && !id.getText().isEmpty()
                && !address.getText().isEmpty()
                && (!phoneNumber1.getText().isEmpty() || !phoneNumber2.getText().isEmpty());
    }
}
