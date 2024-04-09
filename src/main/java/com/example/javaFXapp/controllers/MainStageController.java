package com.example.javaFXapp.controllers;

import com.example.javaFXapp.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainStageController {

    @FXML
    private Button addStudentButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button quitButton;

    @FXML
    private Button addCourseButton;

    @FXML
    private Button showGradesButton;

    @FXML
    void backToRegistration(MouseEvent event) throws IOException {
        Stage registrationStage = (Stage) quitButton.getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(Application.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlloader.load());
        registrationStage.setTitle("Application");
        registrationStage.setScene(scene);


    }


    @FXML
    void switchScene(ActionEvent event) throws IOException {
        String resource = "";

        if (event.getSource() == addStudentButton) {
            resource = "addStudent.fxml";
        } else if (event.getSource() == addCourseButton) {
            resource = "addCourse.fxml";
        } else if (event.getSource() == showGradesButton) {
            resource = "showGrades.fxml";
        }

        if (!resource.isEmpty()) {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource)));
            borderPane.setCenter(view);
        }
    }

}
