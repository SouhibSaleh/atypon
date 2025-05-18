package com.example.demo.models;

public class CourseGrade {
    private int id;
    private String courseName;
    private int grade;

    public CourseGrade(int id, String courseName, int grade) {
        this.id = id;
        this.courseName = courseName;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "CourseGrade{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", grade=" + grade +
                '}';
    }
}
