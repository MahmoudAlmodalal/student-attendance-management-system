package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;

public class ReportsController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField lectureName;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();
    public WritableWorkbook onReport() {
        Course course = model.getRegisteredCourse();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        Lecture lecture = course.getLectureByName(lectureName.getText());
        if (file != null && lecture != null) {
            String path = file.getPath();
            return lecture.getExcelAttendance(path);
        }
        else {
            MyAlert.errorAlert("Enter correct lecturer name!", "Error", null);
        }
        return null;
    }
    public WritableWorkbook onStudentUnder25() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(null);
            // to get Exale of pepole under 25%
            if (file != null) {
                String path = file.getPath();
                return (model.getRegisteredCourse().getExcelStudentUnder25(path));
            } else {
                MyAlert.errorAlert("Enter correct lecturer name!", "Error", null);
            }
        }
        catch (IOException | WriteException e) {
            MyAlert.errorAlert("Error", "Error", null);
        }
        return null;
    }

    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
}
