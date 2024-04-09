package com.example.javaFXapp.controllers;

import com.example.javaFXapp.models.Course;
import com.example.javaFXapp.database.DBHandler;
import com.example.javaFXapp.models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCourseController implements Initializable {

    static ObservableList<Course> courseObservableList = FXCollections.observableArrayList(DBHandler.getCourseFromDatabase());

    @FXML private Button addCourse;
    @FXML private TableColumn<Course, Integer> availableCourses;
    @FXML private TableColumn<Course, String> availableSubjects;
    @FXML private ChoiceBox<Integer> courseInput;
    @FXML private Button updateCourse;
    @FXML private TableColumn<Course, String> firstSubject;
    @FXML private TextField firstSubjectInput;
    @FXML private TableColumn<Course, String> secondSubject;
    @FXML private TextField secondSubjectInput;
    @FXML private TableColumn<Course, String> thirdSubject;
    @FXML private TextField thirdSubjectInput;
    @FXML private TableView<Course> courseTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableCourses.setCellValueFactory(new PropertyValueFactory<Course, Integer>("courseNumber"));
        firstSubject.setCellValueFactory(new PropertyValueFactory<Course, String>("firstSubject"));
        secondSubject.setCellValueFactory(new PropertyValueFactory<Course, String>("secondSubject"));
        thirdSubject.setCellValueFactory(new PropertyValueFactory<Course, String>("thirdSubject"));
        courseInput.getItems().addAll(1,2,3);
        courseTable.setItems(courseObservableList);
    }

    @FXML
    void updateCourse(ActionEvent event) {
        Course course = courseTable.getSelectionModel().getSelectedItem();
        int courseNumber = course.getCourseNumber();
        String newFirstSubject=null;
        String newSecondSubject=null;
        String newThirdSubject=null;
        newFirstSubject = firstSubjectInput.getText();
        newSecondSubject = secondSubjectInput.getText();
        newThirdSubject = thirdSubjectInput.getText();
        if(!newFirstSubject.isEmpty() && !newSecondSubject.isEmpty() && !newThirdSubject.isEmpty()) {
            course.updateSubjects(newFirstSubject, newSecondSubject, newThirdSubject);
            courseTable.refresh();
            for (Student student : AddStudentController.listOfStudents) {
                if (courseNumber == student.getCourseNumber()) {
                    student.updateSubjects(newFirstSubject, newSecondSubject, newThirdSubject);
                }
            }
        }
    }

    @FXML
    void addCourse(ActionEvent event) {
        int courseNumber=0;
        String firstSubject=null;
        String secondSubject=null;
        String thirdSubject=null;
        courseNumber=courseInput.getSelectionModel().getSelectedItem();
            firstSubject = firstSubjectInput.getText();
            secondSubject = secondSubjectInput.getText();
            thirdSubject = thirdSubjectInput.getText();
        if(courseNumber != 0 && !firstSubject.isEmpty() && !secondSubject.isEmpty() && !thirdSubject.isEmpty()){
            Course course = new Course(courseNumber, firstSubject, secondSubject, thirdSubject);
            boolean courseExists = false;

            for (Course uniqueCourse : courseObservableList) {
                if (uniqueCourse.getCourseNumber() == courseNumber) {
                    courseExists = true;
                    break;
                }
            }
            if (!courseExists){
                courseObservableList.add(course);
                DBHandler.setCourseIntoDatabase(course);
            }
            else {
                System.out.println("Course already exist!");
                //добавить метод апдейта существующих курсов
            }
        }

    }
}
