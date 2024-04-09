package com.example.javaFXapp.controllers;

import com.example.javaFXapp.application.Application;
import com.example.javaFXapp.dataManagers.UserLoginDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


//REGISTRATION AND LOGIN STAGE CONTROLLER

public class RegistrationController implements Initializable {

    @FXML
    private Label signedUpMessage;

    @FXML
    private Label ErrorMessage;

    @FXML
    private CheckBox checkBox;

    @FXML
    private PasswordField passwordHidden;

    @FXML
    private Button login;

    @FXML
    private TextField loginIn;

    @FXML
    private TextField passwordIn;

    @FXML
    private Button signUp;


    //Contain login, password of the users
    HashMap<String, String> userData = new HashMap<>();

    @FXML
    void addUser(MouseEvent event) {
        signedUpMessage.setVisible(false);
        ErrorMessage.setVisible(false);
        String login = loginIn.getText();
        String password = !passwordIn.getText().isBlank() ? passwordIn.getText() : passwordHidden.getText();

        if (login.isBlank() || password.isBlank() || userData.containsKey(login)) {
            ErrorMessage.setVisible(true);
        } else {
            userData.put(login, password);
            signedUpMessage.setVisible(true);
            UserLoginDataManager.updateUserDataFile(userData);
        }


    }

    @FXML
    void changeVisibility(ActionEvent event) {
        if (checkBox.isSelected()){
            passwordIn.setText(passwordHidden.getText());
            passwordIn.setVisible(true);
            passwordHidden.setVisible(false);
            return;
        }
        passwordHidden.setText(passwordIn.getText());
        passwordHidden.setVisible(true);
        passwordIn.setVisible(false);
    }

    @FXML
    void goNextStage(MouseEvent event) throws IOException {
        String password = !passwordIn.getText().isBlank() ? passwordIn.getText() : passwordHidden.getText();
        if (!loginIn.getText().isBlank() && (!password.isBlank())){
            if (!userData.containsKey(loginIn.getText())){
                ErrorMessage.setVisible(true);
            } if (password.equals(userData.get(loginIn.getText()))){
                Stage stageRegistration = (Stage) signUp.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainStage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stageRegistration.setTitle("Application");
                stageRegistration.setScene(scene);
            }
        }
        signedUpMessage.setVisible(false);
        ErrorMessage.setVisible(true);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData = UserLoginDataManager.readUserDataFromFile();
        if (userData == null) {
            userData = new HashMap<>();
        }
    }
}


