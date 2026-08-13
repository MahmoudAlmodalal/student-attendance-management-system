package com.studentattendance.models;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class Lecture implements Serializable{
    private String name;
    private String address;
    private Course course;
    private ArrayList<Student> lectureAttendance = new ArrayList<>();

    public Lecture() {
    }

    public Lecture(String name, String address, Course course) {
        this.name = name;
        this.address = address;
        this.course = course;
    }

    public Lecture(String name, String address, Course course, ArrayList<Student> lectureAttendance) {
        this.name = name;
        this.address = address;
        this.course = course;
        this.lectureAttendance = lectureAttendance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<Student> getLectureAttendance() {
        return lectureAttendance;
    }
    public void removeLectureAttendance(Student student) {
        lectureAttendance.remove(student);
    }
    public Student getLectureAttendanceById(String id) {
        for (Student student : lectureAttendance) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public void setLectureAttendanceById(String id) {
        ArrayList<Student> students = getCourse().getStudents();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                lectureAttendance.add(student);
            }
        }
    }
    public void setLectureExcelAttendance(HSSFWorkbook AttendanceFile){
        HSSFSheet sheet = AttendanceFile.getSheetAt(0);
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            String studentId = "";
            if (cell != null) {
                switch (cell.getCellType()) {
                    case STRING -> studentId = cell.getStringCellValue();
                    case NUMERIC -> studentId = (int) (cell.getNumericCellValue()) + "";
                }
                if (getLectureAttendanceById(studentId) == null) {
                    setLectureAttendanceById(studentId);
                }
            }
        }
    }
    public WritableWorkbook getExcelAttendance(String path) {
        try {

            WritableWorkbook excelAttendance = Workbook.createWorkbook(new File(path + "/" + getName() + "studentAttendance.xls"));
            WritableSheet excelSheet = excelAttendance.createSheet("Sheet 1", 0);
            excelSheet.addCell(new Label(0, 0, "id"));
            excelSheet.addCell(new Label(1, 0, "Student Name"));
            excelSheet.addCell(new Label(2, 0, "Attendance"));

            ArrayList<Student> students = getCourse().getStudents();

            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                String studentId = student.getId();
                String studentName = student.getName();

                Label label1 = new Label(0, i + 1, studentId);
                excelSheet.addCell(label1);

                Label label2 = new Label(1, i + 1, studentName);
                excelSheet.addCell(label2);

                Label label3 = new Label(2, i + 1, (getLectureAttendanceById(studentId) != null) ? "1" : "0");
                excelSheet.addCell(label3);
            }
            Label label4 = new Label(3, 1, "Ratio Attendance: ");
            excelSheet.addCell(label4);

            Label label5 = new Label(4, 1, ((getLectureAttendance().size() * 100) / (students.size())) + "%");
            excelSheet.addCell(label5);

            Label label6 = new Label(3, 2, "Number Of Attendance: ");
            excelSheet.addCell(label6);

            Label label7 = new Label(4, 2, getLectureAttendance().size() + "");
            excelSheet.addCell(label7);

            excelAttendance.write();
            excelAttendance.close();
            return excelAttendance;
        }
        catch (IOException | WriteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", course=" + course +
                '}';
    }
}
