package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            List<Employee> employees = ExcelUtil.readEmployeesFromExcel("src/main/resources/TaskList.xlsx");

            List<Thread> threads = new ArrayList<>();

            for (Employee emp : employees) {
                Thread thread = new Thread(emp);
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.out.println("Поток был прерван: " + e.getMessage());
                }
            }

            ExcelUtil.saveStatsToExcel(employees, "src/main/resources/statistics.xlsx", 3);

            ExcelUtil.updateTaskExcel(employees, "src/main/resources/TaskList.xlsx");

            System.out.println("Рабочий день завершен!");

        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
            e.printStackTrace();
        }
    }
}