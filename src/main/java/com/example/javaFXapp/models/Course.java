package com.example.javaFXapp.models;

public class Course {
    private int courseNumber;

    private String firstSubject;
    private String secondSubject;
    private String thirdSubject;

    public Course(int courseNumber, String firstSubject, String secondSubject, String thirdSubject) {
        this.courseNumber = courseNumber;
        this.firstSubject = firstSubject;
        this.secondSubject = secondSubject;
        this.thirdSubject = thirdSubject;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setFirstSubject(String firstSubject) {
        this.firstSubject = firstSubject;
    }

    public void setSecondSubject(String secondSubject) {
        this.secondSubject = secondSubject;
    }

    public void setThirdSubject(String thirdSubject) {
        this.thirdSubject = thirdSubject;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getFirstSubject() {
        return firstSubject;
    }

    public String getSecondSubject() {
        return secondSubject;
    }

    public String getThirdSubject() {
        return thirdSubject;
    }
    public void updateSubjects(String newFirstSubject, String newSecondSubject, String newThirdSubject) {
        this.firstSubject = newFirstSubject;
        this.secondSubject = newSecondSubject;
        this.thirdSubject = newThirdSubject;

    }
}
