package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.Course;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.Lecture;
import com.studentattendance.models.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RemoveLectureController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField lectureName;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();

    public void onRemove() {
        Course course = model.getRegisteredCourse();
        Lecture removedLecture = null;
        boolean removed = false;
        for (Lecture lecture : course.getLectures()) {
            if (lecture.getName().equals(lectureName.getText())) {
                removed = true;
                removedLecture = lecture;
                break;
            }
        }
        if (removed) {
            course.removeLecture(removedLecture);
            MyAlert.informationAlert("The currentLecture has been successfully deleted", "Done", null);
        }
        else {
            MyAlert.errorAlert("Lecture dose not exists ", "Error", null);
        }
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }
}
