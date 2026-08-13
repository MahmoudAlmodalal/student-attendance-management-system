package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.Course;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.Lecture;
import com.studentattendance.models.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateLectureController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField name;
    @FXML
    private TextField lectureName;
    @FXML
    private TextField lecturePlace;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();
    private static final Course course = model.getRegisteredCourse();
    public void onSearch() {
        Lecture lecture = course.getLectureByName(name.getText());
        if (lecture != null) {
            lectureName.setText(lecture.getName());
            lecturePlace.setText(lecture.getAddress());
        }
        else {
            MyAlert.errorAlert("Lecture dose not exist!", "Error", null);
        }
    }
    public void onUpdate() {
        Lecture lecture = course.getLectureByName(lectureName.getText());
        if (lecture != null && !lectureName.getText().isEmpty() && !lecturePlace.getText().isEmpty()) {
            lecture.setName(lectureName.getText());
            lecture.setAddress(lecturePlace.getText());
            MyAlert.informationAlert("The currentLecture has been updated successfully", "Done", null);
        }
        else {
            MyAlert.errorAlert("Lecture dose not exist! or All data must be entered", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
}
