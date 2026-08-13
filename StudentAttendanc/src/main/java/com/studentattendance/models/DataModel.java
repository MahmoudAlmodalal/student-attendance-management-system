package com.studentattendance.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class DataModel {
    private static final Path DATA_DIRECTORY = Paths.get("data");
    private static final Path DATA_FILE = DATA_DIRECTORY.resolve("attendance-data.dat");

    private static SystemManager systemManager;
    private static TeacherAssistant registeredTeacherAssistant;
    private static ArrayList<Course> courses;
    private static ArrayList<TeacherAssistant> teacherAssistants;
    private static Course registeredCourse;

    public DataModel() {
        loadData();
    }

    public static synchronized void loadData() {
        if (systemManager != null) {
            return;
        }

        if (Files.exists(DATA_FILE)) {
            try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(DATA_FILE))) {
                systemManager = (SystemManager) input.readObject();
                courses = systemManager.getCourses();
                teacherAssistants = systemManager.getTeacherAssistants();
                if (courses == null) {
                    courses = new ArrayList<>();
                }
                if (teacherAssistants == null) {
                    teacherAssistants = new ArrayList<>();
                }
                return;
            } catch (IOException | ClassNotFoundException | ClassCastException exception) {
                System.err.println("Unable to load attendance data: " + exception.getMessage());
            }
        }

        initializeDefaults();
    }

    public static synchronized void saveData() {
        if (systemManager == null) {
            loadData();
        }

        try {
            Files.createDirectories(DATA_DIRECTORY);
            try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(DATA_FILE))) {
                output.writeObject(systemManager);
            }
        } catch (IOException exception) {
            System.err.println("Unable to save attendance data: " + exception.getMessage());
        }
    }

    private static void initializeDefaults() {
        courses = new ArrayList<>();
        teacherAssistants = new ArrayList<>();
        systemManager = new SystemManager(
                "Ahmed",
                "Male",
                "Gaza",
                "admin",
                "admin@gmail.com",
                courses,
                teacherAssistants
        );
    }

    public SystemManager getSystemManager() {
        return systemManager;
    }

    public void setSystemManager(SystemManager systemManager) {
        DataModel.systemManager = Objects.requireNonNull(systemManager);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        DataModel.courses = Objects.requireNonNull(courses);
    }

    public void addCourse(Course course) {
        courses.add(Objects.requireNonNull(course));
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public Course getCourseByName(String name) {
        return courses.stream()
                .filter(course -> Objects.equals(course.getSubject(), name))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<TeacherAssistant> getTeacherAssistants() {
        return teacherAssistants;
    }

    public void setTeacherAssistants(ArrayList<TeacherAssistant> teacherAssistants) {
        DataModel.teacherAssistants = Objects.requireNonNull(teacherAssistants);
    }

    public void addTeacherAssistant(TeacherAssistant teacherAssistant) {
        teacherAssistants.add(Objects.requireNonNull(teacherAssistant));
    }

    public void removeTeacherAssistant(TeacherAssistant teacherAssistant) {
        teacherAssistants.remove(teacherAssistant);
    }

    public TeacherAssistant getTeacherAssistantByName(String name) {
        return teacherAssistants.stream()
                .filter(teacherAssistant -> Objects.equals(teacherAssistant.getName(), name))
                .findFirst()
                .orElse(null);
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
