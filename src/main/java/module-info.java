module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.example.javaFXapp.controllers;
    opens com.example.javaFXapp.controllers to javafx.fxml;
    exports com.example.javaFXapp.models;
    opens com.example.javaFXapp.models to javafx.fxml;
    exports com.example.javaFXapp.database;
    opens com.example.javaFXapp.database to javafx.fxml;
    exports com.example.javaFXapp.application;
    opens com.example.javaFXapp.application to javafx.fxml;
    exports com.example.javaFXapp.dataManagers;
    opens com.example.javaFXapp.dataManagers to javafx.fxml;
}