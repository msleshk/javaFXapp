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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {

    @FXML private TableColumn<Student, String> GenderColumn;
    @FXML
    private TableColumn<Student, String> NameColumn;
    @FXML
    private TableColumn<Student, Integer> ageColumn;
    @FXML
    private TableColumn<Student, Integer> courseColumn;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TextField ageIn;
    @FXML
    private TextField idIn;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button updateStudentButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private Button addCourseButton;
    @FXML
    private ChoiceBox<Integer> courseChoice;
    @FXML
    private TextField firstGradeIn;
    @FXML
    private TextField thirdGradeIn;
    @FXML
    private TextField secondGradeIn;
    @FXML
    private RadioButton female;
    @FXML
    private ToggleGroup genderIn;
    @FXML
    private RadioButton male;
    @FXML
    private TextField nameIn;
    @FXML
    private TableView<Student> table;

    @FXML
    void updateStudent(ActionEvent event) {
        int selectedId = table.getSelectionModel().getSelectedIndex();
        if (selectedId >= 0) {
            String newName = nameIn.getText();
            String newGender = null;
            int newAge = parseInt(ageIn.getText());
            int newCourseNumber = courseChoice.getSelectionModel().getSelectedItem();
            int newFirstGrade = parseInt(firstGradeIn.getText());
            int newSecondGrade = parseInt(secondGradeIn.getText());
            int newThirdGrade = parseInt(thirdGradeIn.getText());

            if (genderIn.getSelectedToggle() != null) {
                if (genderIn.getSelectedToggle().equals(male)) {
                    newGender = "Male";
                } else {
                    newGender = "Female";
                }
            }

            if (!newName.isEmpty() && newAge != 0 && newGender != null) {
                Student selectedStudent = listOfStudents.get(selectedId);

                // Update student data
                selectedStudent.setName(newName);
                selectedStudent.setAge(newAge);
                selectedStudent.setGender(newGender);
                selectedStudent.setCourseNumber(newCourseNumber);

                // Find the corresponding course
                Course selectedCourse = AddCourseController.courseObservableList.stream()
                        .filter(course -> course.getCourseNumber() == newCourseNumber)
                        .findFirst()
                        .orElse(null);

                // Initialize or update grades
                if (selectedCourse != null) {
                    selectedStudent.initializeGrades(selectedCourse, newFirstGrade, newSecondGrade, newThirdGrade);
                }

                // Refresh the table
                table.refresh();
                System.out.println("Student updated");
            } else {
                System.out.println("Please fill in all fields");
            }
        } else {
            System.out.println("Invalid selection index");
        }
    }




    @FXML
    void changeName(MouseEvent event) {
        String name = nameIn.getText();
        String gender = null;
        Integer courseNumber;
        int studentId =0, age =0, firstGrade = 0, secondGrade = 0, thirdGrade = 0;
        studentId = parseInt(idIn.getText());
        age = parseInt(ageIn.getText());
        firstGrade = parseInt(firstGradeIn.getText());
        secondGrade = Integer.parseInt(secondGradeIn.getText());
        thirdGrade = Integer.parseInt(thirdGradeIn.getText());

        if (genderIn.getSelectedToggle()!=null) {
            if (genderIn.getSelectedToggle().equals(male)) {
                gender = "Male";
            } else {
                gender = "Female";
            }
        }
        if(!name.isEmpty() && age != 0 && gender != null){
            boolean studentUnique = true;
            for (Student student:listOfStudents){
                if (studentId==student.getStudentId()){
                    studentUnique = false;
                }
            }
            courseNumber= courseChoice.getSelectionModel().getSelectedItem();
            Student student = new Student(studentId, name, age, gender, courseNumber);
            if (courseNumber!=0){
                for (Course course : AddCourseController.courseObservableList){
                    if (courseNumber==course.getCourseNumber()){
                        student.initializeGrades(course, firstGrade, secondGrade, thirdGrade);

                    }
                }
            }
            if (studentUnique){
                listOfStudents.add(student);
                DBHandler.setStudentIntoDatabase(student);
                DBHandler.setGradesIntoDatabase(student);
            }
            table.setItems(listOfStudents);
        }

    }
    @FXML
    void setSubjectsFieldsNames(ActionEvent event) {
        int courseNumber= courseChoice.getSelectionModel().getSelectedItem();
        if (courseNumber!=0){
            for (Course course : AddCourseController.courseObservableList){
                if (courseNumber==course.getCourseNumber()){
                    firstGradeIn.setPromptText(course.getFirstSubject());
                    secondGradeIn.setPromptText(course.getSecondSubject());
                    thirdGradeIn.setPromptText(course.getThirdSubject());

                }
            }
        }
    }
    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Exception in parseInt: " + e);
            return 0;
        }
    }

//    @FXML
//    void deleteStudent(ActionEvent event) {
//        SelectionModel<Student> studentSelectionModel = table.getSelectionModel();
//        Student selectedStudent = studentSelectionModel.getSelectedItem();
//
//        if (selectedStudent != null) {
//            int studentId = selectedStudent.getStudentId();
//            listOfStudents.removeIf(student -> studentId == student.getStudentId());
//        }
//    }
    @FXML
    void deleteStudent(ActionEvent event) {
        int selectedId=table.getSelectionModel().getSelectedIndex();
        table.getItems().remove(selectedId);
    }



    static ObservableList<Student> listOfStudents = FXCollections.observableArrayList(DBHandler.getStudentFromDatabase());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        GenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        table.setItems(listOfStudents);
        courseChoice.getItems().addAll(1,2,3);
    }

}




