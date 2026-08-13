package com.studentattendance;

import com.studentattendance.models.DataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static final String APPLICATION_TITLE = "Student Attendance Management System";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Main.class.getResource("views/start.fxml"))
        );
        Scene scene = new Scene(loader.load());

        stage.setTitle(APPLICATION_TITLE);
        stage.getIcons().add(new Image(
                Objects.requireNonNull(Main.class.getResourceAsStream("images/uni.jpg"))
        ));
        stage.setScene(scene);
        stage.setMinWidth(750);
        stage.setMinHeight(450);
        stage.show();

        stage.setOnCloseRequest(event -> DataModel.saveData());
    }
}
