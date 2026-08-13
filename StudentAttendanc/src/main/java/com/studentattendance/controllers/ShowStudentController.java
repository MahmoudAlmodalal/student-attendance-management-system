package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.MyAlert;
import com.studentattendance.models.Student;
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

public class ShowStudentController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Student> showStudent;
    @FXML
    private TableColumn<Student, String> id;
    @FXML
    private TableColumn<Student, String> name;
    @FXML
    private TableColumn<Student, ArrayList<String>> phoneNumber;
    @FXML
    private TableColumn<Student, String> gender;
    @FXML
    private TableColumn<Student, String> address;

    private static final DataModel model = new DataModel();
    private static final Navigation navigation = new Navigation();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Student> students = model.getRegisteredCourse().getStudents();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        showStudent.setItems(FXCollections.observableArrayList(students));
        // Make the columns editable
        id.setCellFactory(TextFieldTableCell.forTableColumn());
        id.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            student.setId(event.getNewValue());
        });
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            if (event.getNewValue().split(" ").length == 4) {
                student.setName(event.getNewValue());
                showStudent.setItems(FXCollections.observableList(students));
            }
            else {
                student.setName(event.getOldValue());
                MyAlert.errorAlert("You must enter the name is quadruple", "Error", null);
            }
        });
        address.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            student.setAddress(event.getNewValue());
        });
    }
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);}

}