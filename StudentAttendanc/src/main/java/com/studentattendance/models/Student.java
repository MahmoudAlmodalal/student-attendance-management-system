package com.studentattendance.models;
import java.io.Serializable;
import java.util.ArrayList;

public class Student extends Pearson implements Serializable {
    private ArrayList<Course> courses = new ArrayList<>();
    private String id;

    public Student() {
    }

    public Student(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Student(String name, String gender, String address,String id) {
        super(name, gender, address);
        this.id = id;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourses(Course course) {
        courses.add(course);
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object student) {
        return (student instanceof Student && ((Student) student).getName().equals(getName()));
    }

    @Override
    public String toString() {
        return getId() + ", " + getName() + ", " + getPhoneNumber().toString() + ", " + getGender() + ", " + getAddress();
    }
}
