package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.Course;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.TeacherAssistant;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class ShowTeacherAssistantController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<TeacherAssistant, String> name;
    @FXML
    private TableColumn<TeacherAssistant, String> gender;
    @FXML
    private TableColumn<TeacherAssistant, String> phoneNumber;
    @FXML
    private TableColumn<TeacherAssistant, String> address;
    @FXML
    private TableColumn<TeacherAssistant, String> userName;
    @FXML
    private TableColumn<TeacherAssistant, String> password;
    @FXML
    private TableColumn<TeacherAssistant, ArrayList<Course>> course;
    @FXML
    private TableView<TeacherAssistant> teacherAssistantTableView;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //make sure the property value factory should be exactly same as the getStudentId from your model class
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        course.setCellValueFactory(new PropertyValueFactory<>("courses"));
        teacherAssistantTableView.setItems(FXCollections.observableArrayList(model.getTeacherAssistants()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            TeacherAssistant teacherAssistant = event.getRowValue();
            teacherAssistant.setName(event.getNewValue());
        });
        address.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setOnEditCommit(event -> {
            TeacherAssistant teacherAssistant = event.getRowValue();
            teacherAssistant.setAddress(event.getNewValue());
        });
        userName.setCellFactory(TextFieldTableCell.forTableColumn());
        userName.setOnEditCommit(event -> {
            TeacherAssistant teacherAssistant = event.getRowValue();
            teacherAssistant.setUserName(event.getNewValue());
        });
        password.setCellFactory(TextFieldTableCell.forTableColumn());
        password.setOnEditCommit(event -> {
            TeacherAssistant teacherAssistant = event.getRowValue();
            teacherAssistant.setPassword(event.getNewValue());
        });
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.SYSTEM_MANAGER_FXML);
    }
}
