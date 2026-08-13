package com.studentattendance.models;
import java.io.Serializable;
import java.util.ArrayList;


public class TeacherAssistant extends User implements Serializable {
    private ArrayList<Course> courses = new ArrayList<>();

    public TeacherAssistant() {
    }

    public TeacherAssistant(String name, String gender, String address, String userName, String password) {
        super(name, gender, address, userName, password);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    public void addCourse(Course course) {
        courses.add(course);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    public Course getCourseByName(String name) {
        for (Course course : courses) {
            if (course.getSubject().equals(name)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "TeacherAssistant{" +
                "name=" + getName() +
                "gender=" + getGender() +
                "address=" + getAddress() +
                "userName=" + getUserName() +
                "password=" + getPassword() +
                '}';
    }
}
