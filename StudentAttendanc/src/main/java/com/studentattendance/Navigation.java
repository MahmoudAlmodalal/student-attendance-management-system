package com.studentattendance;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class Navigation {
    public final String MAIN_FXML = "views/start.fxml";
    public final String SYSTEM_MANGER_FXML = "views/systemManager.fxml";
    public final String TEACHER_ASSISTANT_FXML = "views/teacherAssistant.fxml";
    public final String ADD_COURSE_FXML = "views/addCourse.fxml";
    public final String UPDATE_COURSE_FXML = "views/updateCourse.fxml";
    public final String REMOVE_COURSE_FXML = "views/removeCourse.fxml";
    public final String SHOW_COURSE_FXML = "views/ShowCourse.fxml";
    public final String ADD_TEACHER_ASSISTANT_FXML = "views/addTeacherAssistant.fxml";
    public final String UPDATE_TEACHER_ASSISTANT_FXML = "views/updateTeacherAssistant.fxml";
    public final String REMOVE_TEACHER_ASSISTANT_FXML = "views/removeTeacherAssistant.fxml";
    public final String SHOW_TEACHER_ASSISTANT_FXML = "views/ShowTeacherAssistant.fxml";
    public final String ADD_STUDENT_FXML = "views/addStudent.fxml";
    public final String UPDATE_STUDENT_FXML = "views/updateStudent.fxml";
    public final String REMOVE_STUDENT_FXML = "views/removeStudent.fxml";
    public final String ADD_LECTURE_FXML = "views/addLecture.fxml";
    public final String UPDATE_LECTURE_FXML = "views/updateLecture.fxml";
    public final String REMOVE_LECTURE_FXML = "views/removeLecture.fxml";
    public final String ATTENDANCE_FXML = "views/attendance.fxml";
    public final String REPORT_FXML = "views/reports.fxml";
    public final String LECTURE_ATTENDANCE_FXML = "views/lectureAttendance.fxml";
    public final String EDIT_SYSTEM_MANGER_FXML = "views/editSystemManager.fxml";
    public final String SHOW_STUDENT_FXML = "views/showStudent.fxml";
    public final String SHOW_LECTURE_FXML = "views/showLecture.fxml";
    public final String EDITE_TEACHER_ASSISTANT_FXML = "views/editTeacherAssistant.fxml";
    public final String ENTER_COURSE_FXML = "views/enterCourse.fxml";
    public final String ADD_REMOVE_COURSE_TEACHER_FXML = "views/addRemoveCourseTeacher.fxml";

    public void navigateTo(Parent rootPane, String path) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            rootPane.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
