package com.studentattendance.models;

import java.io.*;
import java.util.ArrayList;
import java.io.File;

public class DataModel {
    private static SystemManager systemManager;
    private static TeacherAssistant registeredTeacherAssistant;
    private static ArrayList<Course> courses;
    private static ArrayList<TeacherAssistant> teacherAssistants;
    private static Course registeredCourse;

    public DataModel() {
        load_Data();
    }

    public static void load_Data() {
        if (systemManager == null){
            File file = new File("systemManager.dat");
            if (file.exists()) {
                try (ObjectInput input = new ObjectInputStream(new FileInputStream(file))) {
                    systemManager = (SystemManager) input.readObject();
                    courses = systemManager.getCourses();
                    teacherAssistants = systemManager.getTeacherAssistants();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                courses = new ArrayList<>();
                teacherAssistants = new ArrayList<>();
                systemManager = new SystemManager("Ahmed", "Male", "Gaza","admin", "admin@gmail.com", courses, teacherAssistants);
            }
        }
    }

    public static void save_Data() {
        try (ObjectOutputStream output = new  ObjectOutputStream(new FileOutputStream("systemManager.dat"))) {
            output.writeObject(systemManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SystemManager getSystemManager() {
        return systemManager;
    }

    public void setSystemManager(SystemManager systemManager) {
        DataModel.systemManager = systemManager;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        DataModel.courses = courses;
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
    public ArrayList<TeacherAssistant> getTeacherAssistants() {
        return teacherAssistants;
    }

    public void setTeacherAssistants(ArrayList<TeacherAssistant> teacherAssistants) {
        DataModel.teacherAssistants = teacherAssistants;
    }
    public void addTeacherAssistant(TeacherAssistant teacherAssistant) {
        teacherAssistants.add(teacherAssistant);
    }
    public void removeTeacherAssistant(TeacherAssistant teacherAssistant) {
        teacherAssistants.remove(teacherAssistant);
    }
    public TeacherAssistant getTeacherAssistantByName(String name) {
        for (TeacherAssistant teacherAssistant : teacherAssistants) {
            if (teacherAssistant.getName().equals(name)) {
                return teacherAssistant;
            }
        }
        return null;
    }

    public TeacherAssistant getRegisteredTeacherAssistant() {
        return registeredTeacherAssistant;
    }

    public void setRegisteredTeacherAssistant(TeacherAssistant registeredTeacherAssistant) {
        DataModel.registeredTeacherAssistant = registeredTeacherAssistant;
    }

    public Course getRegisteredCourse() {
        return registeredCourse;
    }

    public void setRegisteredCourse(Course registeredCourse) {
        DataModel.registeredCourse = registeredCourse;
    }
}
