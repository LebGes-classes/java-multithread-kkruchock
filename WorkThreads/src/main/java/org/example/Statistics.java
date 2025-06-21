package org.example;

public class Statistics {

    private final int employeeId;
    private final int dayNumber;
    private final int workTime;
    private final int idleTime;

    public Statistics(int employeeId, int dayNumber, int workTime, int idleTime) {
        this.employeeId = employeeId;
        this.dayNumber = dayNumber;
        this.workTime = workTime;
        this.idleTime = idleTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getIdleTime() {
        return idleTime;
    }
}