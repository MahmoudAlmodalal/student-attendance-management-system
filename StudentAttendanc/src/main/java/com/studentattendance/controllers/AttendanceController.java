package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.Lecture;
import com.studentattendance.models.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class AttendanceController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField lectureName;

    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();

    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }

    public void onSearch() {
        Lecture lecture = model.getRegisteredCourse().getLectureByName(lectureName.getText());
        if (lecture != null) {
            LectureAttendanceController.setLecture(lectureName.getText());
            navigation.navigateTo(rootPane, navigation.LECTURE_ATTENDANCE_FXML);
        } else {
            MyAlert.errorAlert("The currentLecture does not exist!", "Error", null);
        }
    }
}
