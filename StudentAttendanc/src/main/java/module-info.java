module com.studentattendance {
    requires javafx.controls;
    requires javafx.fxml;
    requires jxl;
    requires poi;


    exports com.studentattendance;
    opens com.studentattendance to javafx.fxml;
    exports com.studentattendance.controllers;
    opens com.studentattendance.controllers to javafx.fxml;
    exports com.studentattendance.models;
    opens com.studentattendance.models to javafx.fxml;
}