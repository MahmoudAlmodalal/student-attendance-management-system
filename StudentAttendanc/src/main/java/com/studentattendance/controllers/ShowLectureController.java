package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.DataModel;
import com.studentattendance.models.Lecture;
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

public class ShowLectureController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Lecture> showLecture;
    @FXML
    private TableColumn<Lecture, String> lectureName;
    @FXML
    private TableColumn<Lecture, String> lecturePlace;

    private static final DataModel model = new DataModel();
    private static final Navigation navigation = new Navigation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lectureName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lecturePlace.setCellValueFactory(new PropertyValueFactory<>("address"));
        showLecture.setItems(FXCollections.observableArrayList(model.getRegisteredCourse().getLectures()));
        // Make the columns editable
        lectureName.setCellFactory(TextFieldTableCell.forTableColumn());
        lectureName.setOnEditCommit(event -> {
            Lecture lecture1 = event.getRowValue();
            lecture1.setName(event.getNewValue());
        });
        lecturePlace.setCellFactory(TextFieldTableCell.forTableColumn());
        lecturePlace.setOnEditCommit(event -> {
            Lecture lecture1 = event.getRowValue();
            lecture1.setAddress(event.getNewValue());
        });

    }

    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);}
}