package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.MyAlert;
import com.studentattendance.models.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateStudentController implements Initializable {
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
    private TextField id;
    @FXML
    private TextField studentName;
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
    public void onSearch() {
        boolean found = false;
        for (Student student : model.getRegisteredCourse().getStudents()){
            if (student.getName().equals(name.getText())) {
                found = true;
                studentName.setText(student.getName());
                male.setSelected(student.getGender().equals("Male"));
                female.setSelected(student.getGender().equals("Female"));
                phoneNumber1.setText(student.getPhoneNumber().get(0));
                phoneNumber2.setText(student.getPhoneNumber().get(1));
                address.setText(student.getAddress());
                id.setText(student.getId());
                break;
            }
        }
        if (!found) {
            MyAlert.errorAlert("Student not found! ", "Error", null);
        }
    }
    public void onUpdate() {
        Student studentUpdated = model.getRegisteredCourse().getStudentByNameOrId(studentName.getText());
        if (isValid() && studentUpdated != null && studentName.getText().split(" ").length == 4) {
            studentUpdated.setName(studentName.getText());
            studentUpdated.setGender((male.isSelected()) ? "Male" : "Female");
            studentUpdated.setPhoneNumber(phoneNumber1.getText(), phoneNumber2.getText());
            studentUpdated.setAddress(address.getText());
            studentUpdated.setId(id.getText());
            MyAlert.informationAlert("The studentUpdated  has been updated successfully", "Done", null);
        }
        else if (isValid() && studentUpdated != null) {
            MyAlert.errorAlert("You must enter the name is quadruple", "Error", null);
        }
        else if (isValid()) {
            MyAlert.errorAlert("Student not found! ", "Error", null);
        }
        else {
            MyAlert.errorAlert("All fields must be entered!", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
    private boolean isValid() {
        return !studentName.getText().isEmpty()
                && !id.getText().isEmpty()
                && !address.getText().isEmpty()
                && (!phoneNumber1.getText().isEmpty() || !phoneNumber2.getText().isEmpty());
    }
}
