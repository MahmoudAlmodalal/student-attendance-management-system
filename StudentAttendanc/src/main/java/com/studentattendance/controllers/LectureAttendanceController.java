package com.studentattendance.controllers;

import com.studentattendance.Navigation;
import com.studentattendance.models.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LectureAttendanceController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Student> studentAttendance;
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
    @FXML
    private Label lectureNameLabel;
    @FXML
    private ComboBox<String> search;
    private static String lectureName;
    private static final DataModel model = new DataModel();
    private static final Course course = model.getRegisteredCourse();

    private static final Navigation navigation = new Navigation();
    public void onBack() {
        navigation.navigateTo(rootPane, navigation.TEACHER_ASSISTANT_FXML);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lectureNameLabel.setText("Lecture " + lectureName);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        if (course.getLectureByName(lectureName) != null)
            studentAttendance.setItems(FXCollections.observableArrayList(course.getLectureByName(lectureName).getLectureAttendance()));
        // Make the columns editable
        id.setCellFactory(TextFieldTableCell.forTableColumn());
        id.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            student.setId(event.getNewValue());
        });
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            student.setName(event.getNewValue());
        });
        gender.setCellFactory(TextFieldTableCell.forTableColumn());
        gender.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            student.setGender(event.getNewValue());
        });
        address.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setOnEditCommit(event -> {
            Student student = event.getRowValue();
            student.setAddress(event.getNewValue());
        });
    }
    public void onSelect() {
        Lecture lecture = course.getLectureByName(lectureName);
        String studentId = "";
        if (search.getValue() != null) {
            studentId = search.getValue().split(", ")[0];
        }
        Student student = course.getStudentByNameOrId(studentId);
        if (student != null && lecture != null && lecture.getLectureAttendanceById(studentId) == null) {
            lecture.setLectureAttendanceById(studentId);
            MyAlert.informationAlert("The student has been added successfully", "Done", null);
        }
        else if (lecture.getLectureAttendanceById(studentId) != null) {
            MyAlert.errorAlert("The student already added!", "Error", null);
        }
        else {
            MyAlert.errorAlert("The student does not exist!", "Error", null);
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        studentAttendance.setItems(FXCollections.observableArrayList(lecture.getLectureAttendance()));
    }

    public void onKeyReleased() {
        onSelect();
        String currentValue = search.getEditor().getText();
        if (!currentValue.isEmpty() && !currentValue.equals(search.getPromptText())) {
            String keyword = currentValue.toLowerCase();
            List<String> studentList = course.getStudents()
                    .stream()
                    .filter(student -> student.getName().toLowerCase().contains(keyword)
                            || student.getId().toLowerCase().contains(keyword)
                            || student.getPhoneNumber().stream().anyMatch(number -> number.contains(keyword)))
                    .map(Student::toString)
                    .collect(Collectors.toList());
            search.getItems().setAll(studentList);
            if (!search.isShowing()) {
                search.show();
            }
        } else {
            search.getItems().clear();
            search.hide();
        }
    }
    public void onRemove() {
        Lecture lecture = course.getLectureByName(lectureName);
        boolean found = false;
        for (Student student : studentAttendance.getItems()) {
            if (search.getValue() != null && student.getId().equals(search.getValue().split(", ")[0])) {
                course.getLectureByName(lectureName).removeLectureAttendance(student);
                found = true;
            }
        }
        if (!found) {
            MyAlert.errorAlert("Student not found", "Error", null);
        }
        studentAttendance.setItems(FXCollections.observableArrayList(lecture.getLectureAttendance()));
    }
    public static void setLecture(String lecture) {
        LectureAttendanceController.lectureName = lecture;
    }

    public void onStudentAttendance() {
        if (search.getValue() == null) {
            MyAlert.errorAlert("You must select a student", "Error", null);
            return;
        }

        String studentId = search.getValue().split(", ")[0];
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        Student student = course.getStudentByNameOrId(studentId);
        if (file == null || student == null) {
            return;
        }

        Path outputFile = Paths.get(file.getPath())
                .resolve(safeFileName(student.getName()) + "-attendance.xls");
        try {
            Files.createDirectories(outputFile.getParent());
            try (HSSFWorkbook workbook = new HSSFWorkbook();
                 FileOutputStream output = new FileOutputStream(outputFile.toFile())) {
                var sheet = workbook.createSheet("Attendance");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Lecture name");
                header.createCell(1).setCellValue("Attendance");

                ArrayList<Lecture> lectures = course.getLectures();
                for (int index = 0; index < lectures.size(); index++) {
                    Lecture lecture = lectures.get(index);
                    Row row = sheet.createRow(index + 1);
                    row.createCell(0).setCellValue(lecture.getName());
                    row.createCell(1).setCellValue(
                            lecture.getLectureAttendanceById(studentId) != null ? "Present" : "Absent"
                    );
                }

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                workbook.write(output);
            }
        } catch (IOException exception) {
            MyAlert.errorAlert("Unable to export report", "Error", exception.getMessage());
        }
    }

    private String safeFileName(String value) {
        return value == null || value.isBlank()
                ? "student"
                : value.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    public void onExcelAttendance() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String path = file.getPath();
            try {
                HSSFWorkbook attendance = new HSSFWorkbook(new FileInputStream(path));
                Lecture lecture = course.getLectureByName(lectureName);
                lecture.setLectureExcelAttendance(attendance);
                studentAttendance.setItems(FXCollections.observableArrayList(lecture.getLectureAttendance()));
            }
            catch (IOException e) {
                MyAlert.errorAlert("Can't save", "Error", null);
            }
        }
    }
}

