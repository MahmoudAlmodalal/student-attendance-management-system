package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.IOException;

public class ReportsController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField lectureName;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();
    public void onReport() {
        Course course = model.getRegisteredCourse();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        Lecture lecture = course.getLectureByName(lectureName.getText());
        if (file != null && lecture != null) {
            try {
                lecture.exportAttendance(file.getPath());
            } catch (IOException exception) {
                MyAlert.errorAlert("Unable to export report", "Error", exception.getMessage());
            }
        } else {
            MyAlert.errorAlert("Enter a valid lecture name", "Error", null);
        }
    }

    public void onStudentUnder25() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        if (file == null) {
            return;
        }

        try {
            model.getRegisteredCourse().exportStudentsUnder25(file.getPath());
        } catch (IOException exception) {
            MyAlert.errorAlert("Unable to export report", "Error", exception.getMessage());
        }
    }

    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
}
