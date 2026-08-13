package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SystemManagerController {

    @FXML
    private AnchorPane rootPane;
    private static final Navigation navigation = new Navigation();
    public void onAddCourse() {
        navigation.navigateTo(rootPane, navigation.ADD_COURSE_FXML);
    }
    public void onUpdateCourse() {
        navigation.navigateTo(rootPane, navigation.UPDATE_COURSE_FXML);
    }
    public void onRemoveCourse() {
        navigation.navigateTo(rootPane, navigation.REMOVE_COURSE_FXML);
    }
    public void onShowCourse() {
        navigation.navigateTo(rootPane, navigation.SHOW_COURSE_FXML);
    }
    public void onAddTeacherAssistant() {
        navigation.navigateTo(rootPane, navigation.ADD_TEACHER_ASSISTANT_FXML);
    }
    public void onUpdateTeacherAssistant() {
        navigation.navigateTo(rootPane, navigation.UPDATE_TEACHER_ASSISTANT_FXML);
    }
    public void onRemoveTeacherAssistant() {
        navigation.navigateTo(rootPane, navigation.REMOVE_TEACHER_ASSISTANT_FXML);
    }
    public void onShowTeacherAssistant() {
        navigation.navigateTo(rootPane, navigation.SHOW_TEACHER_ASSISTANT_FXML);
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.MAIN_FXML);
    }

    public void onEditSystemManager() {
        navigation.navigateTo(rootPane, navigation.EDIT_SYSTEM_MANGER_FXML);
    }

    public void onAddCourseToTeacher() {
        navigation.navigateTo(rootPane, navigation.ADD_REMOVE_COURSE_TEACHER_FXML);
    }
}
