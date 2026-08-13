package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.Course;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
public class UpdateCourseController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField courseName;
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

    public void onSearch() {
        boolean found = false;
        for (Course course : model.getCourses()) {
            if (course.getSubject().equals(courseName.getText())) {
                found = true;
                subject.setText(course.getSubject());
                bookName.setText(course.getBookName());
                place.setText(course.getPlace());
                instructor.setText(course.getInstructor());
                break;
            }
        }
        if (!found) {
            MyAlert.errorAlert("The course dose not exist", "Error", null);
        }
    }
    public void onUpdate() {
        Course courseUpdate = model.getCourseByName(subject.getText());
        if (isValid() && courseUpdate != null) {
            courseUpdate.setSubject(subject.getText());
            courseUpdate.setBookName(bookName.getText());
            courseUpdate.setPlace(place.getText());
            courseUpdate.setInstructor(instructor.getText());
            MyAlert.informationAlert("The course has been updated successfully", "Done", null);
        }
        else if (isValid()) {
            MyAlert.errorAlert("The course dose not exist", "Error", null);
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
