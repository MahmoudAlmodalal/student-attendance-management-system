package com.studentattendance.models;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void exportAttendance(String path) throws IOException {
        Path outputFile = Paths.get(path).resolve(safeFileName(getName()) + "-attendance.xls");
        Files.createDirectories(outputFile.getParent());

        try (HSSFWorkbook workbook = new HSSFWorkbook();
             FileOutputStream output = new FileOutputStream(outputFile.toFile())) {
            HSSFSheet sheet = workbook.createSheet("Attendance");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Student ID");
            header.createCell(1).setCellValue("Student Name");
            header.createCell(2).setCellValue("Attendance");

            ArrayList<Student> students = getCourse().getStudents();
            for (int index = 0; index < students.size(); index++) {
                Student student = students.get(index);
                Row row = sheet.createRow(index + 1);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getName());
                row.createCell(2).setCellValue(getLectureAttendanceById(student.getId()) != null ? "Present" : "Absent");
            }

            int summaryRow = students.size() + 2;
            Row attendanceSummary = sheet.createRow(summaryRow);
            attendanceSummary.createCell(0).setCellValue("Attendance ratio");
            double ratio = students.isEmpty() ? 0 : (getLectureAttendance().size() * 100.0) / students.size();
            attendanceSummary.createCell(1).setCellValue(String.format("%.1f%%", ratio));
            Row countSummary = sheet.createRow(summaryRow + 1);
            countSummary.createCell(0).setCellValue("Number present");
            countSummary.createCell(1).setCellValue(getLectureAttendance().size());

            for (int column = 0; column < 3; column++) {
                sheet.autoSizeColumn(column);
            }
            workbook.write(output);
        }
    }

    private String safeFileName(String value) {
        return value == null || value.isBlank()
                ? "lecture"
                : value.replaceAll("[\\\\/:*?\"<>|]", "_");
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
