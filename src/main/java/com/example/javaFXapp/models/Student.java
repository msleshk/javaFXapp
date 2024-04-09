package com.example.javaFXapp.models;

import com.example.javaFXapp.database.DBHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private Integer studentId;
    private Integer courseNumber;
    private String name;
    private Integer age;
    private String gender;

    private Map<String, Integer> grades;

    public Student(int studentId, String name, int age, String gender, int courseNumber) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.courseNumber = courseNumber;
        this.grades = new HashMap<>(loadGradesFromDatabase(studentId));
    }

    public void initializeGrades(Course course, int firstGrade, int secondGrade, int thirdGrade) {
        grades.clear();
        grades.put(course.getFirstSubject(), firstGrade);
        grades.put(course.getSecondSubject(), secondGrade);
        grades.put(course.getThirdSubject(), thirdGrade);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStudentId() {
        return studentId;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return Objects.equals(studentId, student.studentId) &&
                Objects.equals(courseNumber, student.courseNumber) &&
                Objects.equals(name, student.name) &&
                Objects.equals(age, student.age) &&
                Objects.equals(gender, student.gender) &&
                Objects.equals(grades, student.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseNumber, name, age, gender, grades);
    }

    public void updateSubjects(String newFirstSubject, String newSecondSubject, String newThirdSubject) {
        int firstGrade = grades.getOrDefault(newFirstSubject, 0);
        int secondGrade = grades.getOrDefault(newSecondSubject, 0);
        int thirdGrade = grades.getOrDefault(newThirdSubject, 0);

        grades.clear();

        // Добавляем новые предметы с соответствующими оценками
        grades.put(newFirstSubject, firstGrade);
        grades.put(newSecondSubject, secondGrade);
        grades.put(newThirdSubject, thirdGrade);
    }
    public static Map<String, Integer> loadGradesFromDatabase(int studentId) {
        HashMap<String, Integer> grades=new HashMap<>();
        grades = (HashMap<String, Integer>) DBHandler.getGradesByStudentIdFromDatabase(studentId);
        return grades;
    }
}
