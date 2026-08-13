package com.studentattendance.models;

import javafx.scene.control.Alert;

public class MyAlert {
    private static Alert alert;

    public static Alert getAlert() {
        return alert;
    }

    public static void setAlert(Alert alert) {
        MyAlert.alert = alert;
    }

    public static void informationAlert(String headerText, String title, String contentText) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
    public static void errorAlert(String headerText, String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
    public static void confirmationAlert(String headerText, String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

}
