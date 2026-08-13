package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.Course;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddCourseController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField subject;
    @FXML
    private TextField bookName;
    @FXML
    private TextField place;
    @FXML
    private TextField instructor;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();

    public void onAdd() {
        if (isValid() && model.getCourseByName(subject.getText()) == null) {
            model.addCourse(new Course(subject.getText(), bookName.getText(), place.getText(), instructor.getText()));
            MyAlert.informationAlert("The course has been added successfully", "Done", null);
        }
        else if (isValid()) {
            MyAlert.errorAlert("The course already added!", "Error", null);
        }
        else {
            MyAlert.errorAlert("All fields must be entered!", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.SYSTEM_MANGER_FXML);
    }
    private boolean isValid() {
        return !subject.getText().isEmpty()
                && !bookName.getText().isEmpty()
                && !place.getText().isEmpty()
                && !instructor.getText().isEmpty();
    }
}
