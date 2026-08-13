package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TeacherAssistantController {

    @FXML
    private AnchorPane rootPane;
    private static final Navigation navigation = new Navigation();

    public void onAddStudent() {
        navigation.navigateTo(rootPane, navigation.ADD_STUDENT_FXML);
    }

    public void onUpdateStudent() {
        navigation.navigateTo(rootPane, navigation.UPDATE_STUDENT_FXML);
    }
    public void onRemoveStudent() {
        navigation.navigateTo(rootPane, navigation.REMOVE_STUDENT_FXML);
    }
    public void onAddLecture() {
        navigation.navigateTo(rootPane, navigation.ADD_LECTURE_FXML);
    }

    public void onUpdateLecture() {
        navigation.navigateTo(rootPane, navigation.UPDATE_LECTURE_FXML);
    }

    public void onRemoveLecture() {
        navigation.navigateTo(rootPane, navigation.REMOVE_LECTURE_FXML);
    }

    public void onAttendance() {
        navigation.navigateTo(rootPane, navigation.ATTENDANCE_FXML);
    }

    public void onReports() {
        navigation.navigateTo(rootPane, navigation.REPORT_FXML);
    }

    public void onBack() {
        navigation.navigateTo(rootPane, navigation.ENTER_COURSE_FXML);
    }

    public void onShowStudent() {
        navigation.navigateTo(rootPane, navigation.SHOW_STUDENT_FXML);
    }

    public void onShowLecture() {
        navigation.navigateTo(rootPane, navigation.SHOW_LECTURE_FXML);
    }

    public void onEditTeacherAssistant() {
        navigation.navigateTo(rootPane, navigation.EDIT_TEACHER_ASSISTANT_FXML);
    }
}
