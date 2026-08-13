package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
public class RemoveStudentController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField studentNameOrID;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();

    public void onRemove() {
        boolean removed = false;
        Course course = model.getRegisteredCourse();
        ArrayList<Student> students = course.getStudents();
        Student studentRemoved = null;
        for (Student student : students) {
            if (student.getName().equals(studentNameOrID.getText()) || student.getId().equals(studentNameOrID.getText())) {
                studentRemoved = student;
                removed = true;
                break;
            }
        }
        if (removed) {
            course.removeStudent(studentRemoved);
            for (Lecture lecture : course.getLectures()) {
                lecture.removeLectureAttendance(studentRemoved);
            }
            MyAlert.informationAlert("The student has been successfully deleted", "Done", null);
        }
        else {
            MyAlert.errorAlert("Student dose not exists ", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
}
