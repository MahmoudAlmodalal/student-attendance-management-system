package com.studentattendance.models;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Course implements Serializable {
    private String subject;
    private String bookName;
    private String place;
    private ArrayList<Lecture> lectures = new ArrayList<>();
    private String instructor;
    private ArrayList<Student> students = new ArrayList<>();
    private TeacherAssistant teacherAssistant;

    public Course() {
    }

    public Course(String subject, String bookName, String place, String instructor) {
        this.subject = subject;
        this.bookName = bookName;
        this.place = place;
        this.instructor = instructor;
    }

    public Course(String subject, String bookName, String place, ArrayList<Lecture> lectures, String instructor, ArrayList<Student> students, TeacherAssistant teacherAssistant) {
        this.subject = subject;
        this.bookName = bookName;
        this.place = place;
        this.lectures = lectures;
        this.instructor = instructor;
        this.students = students;
        this.teacherAssistant = teacherAssistant;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ArrayList<Lecture> getLecture() {
        return lectures;
    }

    public void setLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }
    public void removeLecture(Lecture lecture) {
        lectures.remove(lecture);
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }
    public void addLectures(Lecture lecture) {
        lectures.add(lecture);
    }
    public Lecture getLectureByName(String name) {
        for (Lecture lecture : lectures) {
            if (lecture.getName().equals(name)) {
                return lecture;
            }
        }
        return null;
    }
    public TeacherAssistant getTeacherAssistant() {
        return teacherAssistant;
    }

    public void setTeacherAssistant(TeacherAssistant teacherAssistant) {
        this.teacherAssistant = teacherAssistant;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
    public void removeStudent(Student student) {
        students.remove(student);
    }
    public void addStudent(Student student) {
        students.add(student);
        student.addCourses(this);
    }
    public Student getStudentByNameOrId(String name) {
        for (Student student : students) {
            if(student.getName().equals(name) || student.getId().equals(name)) {
                return student;
            }
        }
        return null;
    }
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
    public  ArrayList<Student> allStudentExistantInAllLecture() {
        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<Lecture> lecture = getLectures();
        for (Lecture x : lecture) {
            for (int i = 0; i < x.getLectureAttendance().size(); i++) {
                students.add(x.getLectureAttendance().get(i));
            }
        }
        return students;
    }
    public  ArrayList<Student> getStudentsUnder25() {
        /*to get The number of attendance times for each student its store in numberattendsForEachStudentSyncronisand
          related with studentsInCourse using the same index */
        ArrayList<Student> studentsAttendance = allStudentExistantInAllLecture();
        ArrayList<Student> studentsInCourse = getStudents();
        ArrayList<Integer> numberAttendsForEachStudentSyncronis = new ArrayList<Integer>();
        for (int i = 0; i < studentsInCourse.size(); i++) {
            int count = 0;
            for (int j = 0; j < studentsAttendance.size(); j++) {
                if ((studentsInCourse.get(i)).equals(studentsAttendance.get(j))) {
                    count++;
                }
            }
            numberAttendsForEachStudentSyncronis.add(count);
        }
        int numberOfLecture = getLectures().size();
        // This Array have Students under 25%
        ArrayList<Student> studentUnder25 = new ArrayList<Student>();
        for (int i = 0; i < numberAttendsForEachStudentSyncronis.size(); i++) {
            if ((numberAttendsForEachStudentSyncronis.get(i)) <= (0.25 * numberOfLecture)) {
                studentUnder25.add(studentsInCourse.get(i));
            }
        }
        return studentUnder25;
    }
    public void exportStudentsUnder25(String path) throws IOException {
        Path outputFile = Paths.get(path).resolve("students-under-25-percent.xls");
        Files.createDirectories(outputFile.getParent());

        try (HSSFWorkbook workbook = new HSSFWorkbook();
             FileOutputStream output = new FileOutputStream(outputFile.toFile())) {
            var sheet = workbook.createSheet(getSubject());
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Student ID");
            header.createCell(1).setCellValue("Student Name");

            ArrayList<Student> under25 = getStudentsUnder25();
            for (int index = 0; index < under25.size(); index++) {
                Student student = under25.get(index);
                Row row = sheet.createRow(index + 1);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getName());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            workbook.write(output);
        }
    }

    @Override
    public String toString() {
        return subject;
    }
}
