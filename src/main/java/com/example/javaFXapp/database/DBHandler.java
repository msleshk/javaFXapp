package com.example.javaFXapp.database;

import com.example.javaFXapp.models.Course;
import com.example.javaFXapp.models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHandler {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/trpo_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "W@2915djkq#";
    private static Connection connection=null;

    static ArrayList<Student> databaseStudents=new ArrayList<>();

    public static void getConnection() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setStudentIntoDatabase(Student student){
            String sql = "INSERT INTO student (studentId, name, age, gender, courseNumber) VALUES (?, ?, ?, ?, ?);";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, student.getStudentId());
                statement.setString(2, student.getName());
                statement.setInt(3, student.getAge());
                statement.setString(4, student.getGender());
                statement.setInt(5, student.getCourseNumber());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
    public static ObservableList<Student> getStudentFromDatabase(){
        ObservableList<Student> listOfStudents = FXCollections.observableArrayList();
        String sql = "SELECT * FROM student";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet= preparedStatement.executeQuery();){
            while(resultSet.next()){
                Student student=new Student(resultSet.getInt("studentId"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getString("gender"), resultSet.getInt("courseNumber"));
                listOfStudents.add(student);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfStudents;
    }
    public static ObservableList<Course> getCourseFromDatabase(){
        ObservableList<Course> courseObservableList=FXCollections.observableArrayList();
        String sql = "SELECT * FROM courses";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();)
        {
            while (resultSet.next()){
                courseObservableList.add(new Course(resultSet.getInt("courseNumber"), resultSet.getString("firstSubject"), resultSet.getString("secondSubject"), resultSet.getString("thirdSubject")));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseObservableList;
    }
    public static void setCourseIntoDatabase(Course course){
        String sql = "INSERT INTO courses (courseNumber, firstSubject, secondSubject, thirdSubject) VALUES (?, ?, ?, ?);";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, course.getCourseNumber());
            preparedStatement.setString(2, course.getFirstSubject());
            preparedStatement.setString(3, course.getSecondSubject());
            preparedStatement.setString(4, course.getThirdSubject());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setGradesIntoDatabase(Student student){
        Map<String, Integer> grades = new HashMap<>(student.getGrades());
        String sql = "Insert INTO grades (studentId, subject, grade) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            for (Map.Entry<String , Integer> entry: grades.entrySet()){
                preparedStatement.setInt(1, student.getStudentId());
                preparedStatement.setString(2, entry.getKey());
                preparedStatement.setInt(3, entry.getValue());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Map<String, Integer> getGradesByStudentIdFromDatabase(int studentId) {
        Map<String, Integer> grades = new HashMap<>();

        try {
            String sql = "SELECT subject, grade FROM grades WHERE studentId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String subject = resultSet.getString("subject");
                        int grade = resultSet.getInt("grade");
                        grades.put(subject, grade);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }
}

