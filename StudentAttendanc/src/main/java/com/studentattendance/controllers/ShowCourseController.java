package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.Course;
import com.studentattendance.models.DataModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowCourseController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<Course, String> subject;
    @FXML
    private TableColumn<Course, String> bookName;
    @FXML
    private TableColumn<Course, String> place;
    @FXML
    private TableColumn<Course, String> instructor;
    @FXML
    private TableView<Course> table;
    private static final Navigation navigation = new Navigation();
    private static final DataModel model = new DataModel();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //make sure the property value factory should be exactly same as the getStudentId from your model class
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        place.setCellValueFactory(new PropertyValueFactory<>("place"));
        instructor.setCellValueFactory(new PropertyValueFactory<>("instructor"));

        table.setItems(FXCollections.observableArrayList(model.getCourses()));
        // Make the columns editable
        subject.setCellFactory(TextFieldTableCell.forTableColumn());
        subject.setOnEditCommit(event -> {
            Course course = event.getRowValue();
            course.setSubject(event.getNewValue());
        });
        bookName.setCellFactory(TextFieldTableCell.forTableColumn());
        bookName.setOnEditCommit(event -> {
            Course course = event.getRowValue();
            course.setBookName(event.getNewValue());
        });
        place.setCellFactory(TextFieldTableCell.forTableColumn());
        place.setOnEditCommit(event -> {
            Course course = event.getRowValue();
            course.setPlace(event.getNewValue());
        });
        instructor.setCellFactory(TextFieldTableCell.forTableColumn());
        instructor.setOnEditCommit(event -> {
            Course course = event.getRowValue();
            course.setInstructor(event.getNewValue());
        });
    }
    public void onBack(){
        navigation.navigateTo(rootPane, navigation.SYSTEM_MANAGER_FXML);
    }
}
