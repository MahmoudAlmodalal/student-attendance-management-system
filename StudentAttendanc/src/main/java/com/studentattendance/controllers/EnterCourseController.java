package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.Course;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.MyAlert;
import com.studentattendance.models.TeacherAssistant;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EnterCourseController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField courseName;

    private static final DataModel model = new DataModel();
    private static final Navigation navigation = new Navigation();
    public void onSelectCourse() {
        TeacherAssistant teacherAssistant = model.getRegisteredTeacherAssistant();
        Course course = teacherAssistant.getCourseByName(courseName.getText());
        if (course != null) {
            model.setRegisteredCourse(course);
            navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
        }
        else {
            MyAlert.errorAlert("Course not found!", "Error", null);
        }
    }

    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
}
