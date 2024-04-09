package com.example.javaFXapp.controllers;

import com.example.javaFXapp.models.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShowGradesController implements Initializable {

    @FXML
    private TableColumn<Student, Integer> courseColumn;

    @FXML
    private TableColumn<Student, String> gradesColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchForStudentInput;

    @FXML
    private TableColumn<Student, String> studentIdColumn;

    @FXML
    private TableView<Student> table;
    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Exception in parseInt: " + e);
            return 0;
        }
    }

    @FXML
    void showRequiredStudent(ActionEvent event) {
        int studentId = parseInt(searchForStudentInput.getText());

        // Проверяем, есть ли студент с указанным идентификатором
        Optional<Student> optionalStudent = AddStudentController.listOfStudents.stream()
                .filter(student -> studentId == student.getStudentId())
                .findFirst();

        if (optionalStudent.isPresent()) {
            Student foundStudent = optionalStudent.get();

            table.getItems().clear();
            table.getItems().add(foundStudent);

            // Если необходимо, выбираем найденного студента
            table.getSelectionModel().select(foundStudent);

            System.out.println("Student found and added to the table.");
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));

        gradesColumn.setCellValueFactory(cellData -> {
            Map<String, Integer> grades = cellData.getValue().getGrades();
            StringBuilder gradesString = new StringBuilder();

            // Убедитесь, что grades не пустой перед обработкой
            if (!grades.isEmpty()) {
                for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                    gradesString.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
                }

                // Удалите последнюю запятую и пробел, если они присутствуют
                if (gradesString.length() > 2) {
                    gradesString.setLength(gradesString.length() - 2);
                }
            }

            return new SimpleStringProperty(gradesString.toString());
        });
    }


}

