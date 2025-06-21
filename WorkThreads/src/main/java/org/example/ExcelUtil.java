package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class ExcelUtil {

    public static List<Employee> readEmployeesFromExcel(String filePath) throws IOException {

        List<Employee> employees = new ArrayList<>();
        Map<Integer, Queue<Task>> employeeTasks = new HashMap<>();

        try (InputStream is = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропускаем заголовок

                int employeeId = (int) row.getCell(0).getNumericCellValue();
                String taskName = row.getCell(1).getStringCellValue();
                int hoursRequired = (int) row.getCell(2).getNumericCellValue();

                Task task = new Task(taskName, hoursRequired);
                if (!employeeTasks.containsKey(employeeId)) {
                    employeeTasks.put(employeeId, new LinkedList<>());
                }
                employeeTasks.get(employeeId).add(task);
            }
        }

        for (Map.Entry<Integer, Queue<Task>> entry : employeeTasks.entrySet()) {
            employees.add(new Employee(entry.getKey(), entry.getValue()));
        }

        return employees;
    }

    public static void saveStatsToExcel(List<Employee> employees, String filePath, int dayNumber) throws IOException {
        Workbook workbook;

        File file = new File(filePath);
        if (file.exists()) {
            try (InputStream is = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(is);
            }
        } else {
            workbook = new XSSFWorkbook();
        }
        Sheet sheet = workbook.createSheet("Day " + dayNumber);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Employee ID");
        headerRow.createCell(1).setCellValue("Day Number");
        headerRow.createCell(2).setCellValue("Work Time");
        headerRow.createCell(3).setCellValue("Idle Time");

        int rowNum = 1;
        for (Employee emp : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(dayNumber);
            row.createCell(2).setCellValue(emp.getWorkTime());
            row.createCell(3).setCellValue(emp.getIdleTime());
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }

    public static void updateTaskExcel(List<Employee> employees, String filePath) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tasks");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Employee ID");
        headerRow.createCell(1).setCellValue("Task Name");
        headerRow.createCell(2).setCellValue("Hours Time");

        int rowNum = 1;
        for (Employee emp : employees) {
            for (Task task : emp.getTasks()) {
                if (task.getHoursTime() > 0) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(emp.getId());
                    row.createCell(1).setCellValue(task.getName());
                    row.createCell(2).setCellValue(task.getHoursTime());

//                    System.out.printf("Сохранена задача: %s для %d, осталось %d часов%n",
//                            task.getName(), emp.getId(), task.getHoursTime());
                }
            }
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }
}