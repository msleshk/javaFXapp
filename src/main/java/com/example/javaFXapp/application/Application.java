package com.example.javaFXapp.application;

import com.example.javaFXapp.database.DBHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        DBHandler.getConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Application");
        Image image = new Image("file:C:\\Users\\Max\\IdeaProjects\\demo1\\src\\main\\java\\com\\example\\demo1\\images\\AppIcon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}