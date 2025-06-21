package org.example;

import java.util.Queue;

public class Employee implements Runnable {
    private final int id;
    private final Queue<Task> tasks;
    private int workTime = 0;
    private int idleTime = 0;

    public Employee(int id, Queue<Task> tasks) {
        this.id = id;
        this.tasks = tasks;
    }

    @Override
    public void run() {

        int workDay = 8;
        workTime = 0;

        while (workDay > 0 && !tasks.isEmpty()) {

            Task task = tasks.peek();
            int timeSpent = Math.min(task.getHoursTime(), workDay);

            task.setHoursTime(task.getHoursTime() - timeSpent);
            workTime += timeSpent;
            workDay -= timeSpent;

            if (task.getHoursTime() == 0) {
                tasks.poll();
            }
        }

        setIdleTime(8 - workTime);
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public int getId() {
        return id;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public Queue<Task> getTasks() {
        return tasks;
    }
}