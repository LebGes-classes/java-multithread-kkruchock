package org.example;

public class Task {

    private String name;
    private int hoursTime;

    public Task(String name, int hoursTime) {
        this.name = name;
        this.hoursTime = hoursTime;
    }

    public String getName() {
        return name;
    }

    public int getHoursTime() {
        return hoursTime;
    }

    public void setHoursTime(int hoursTime) {
        this.hoursTime = hoursTime;
    }
}