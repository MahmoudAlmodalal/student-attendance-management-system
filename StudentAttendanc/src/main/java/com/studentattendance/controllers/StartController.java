package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.MyAlert;
import com.studentattendance.models.SystemManager;
import com.studentattendance.models.TeacherAssistant;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StartController {

    @FXML
    private AnchorPane rootPane;
    @FXML
     private TextField userName;
    @FXML
     private PasswordField password;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();

    public void onLogin() {
        boolean found = false;
        SystemManager systemManager = model.getSystemManager();
        if (systemManager.getUserName() != null
                && systemManager.getPassword() != null
                && systemManager.getUserName().equals(userName.getText())
                && systemManager.getPassword().equals(password.getText())) {
            navigation.navigateTo(rootPane, navigation.SYSTEM_MANAGER_FXML);
            found = true;
        } else {
            for (TeacherAssistant teacherAssistants : model.getTeacherAssistants()) {
                if (teacherAssistants.getUserName() != null
                        && teacherAssistants.getPassword() != null
                        && teacherAssistants.getUserName().equals(userName.getText())
                        && teacherAssistants.getPassword().equals(password.getText())) {
                    model.setRegisteredTeacherAssistant(teacherAssistants);
                    navigation.navigateTo(rootPane, navigation.ENTER_COURSE_FXML);
                    found = true;
                }
            }
        }
        if (!found) {
            MyAlert.errorAlert("User Not Found!", "Error", "User name: " + userName.getText() + ", Password: " + password.getText());
        }
    }
}
