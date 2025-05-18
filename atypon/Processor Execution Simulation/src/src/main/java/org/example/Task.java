package org.example;

import java.util.Objects;

public class Task {
    private int id;
    private int creationTime;
    private int executionTime;
    private int priority;
    private String taskDescription;

    public Task(int id, int creationTime, int executionTime, int priority, String taskDescription) {
        this.id = id;
        this.creationTime = creationTime;
        this.executionTime = executionTime;
        this.priority = priority;
        this.taskDescription = taskDescription;
    }

    public int getId() {
        return id;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getPriority() {
        return priority;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && creationTime == task.creationTime && executionTime == task.executionTime && priority == task.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationTime, executionTime, priority);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", creationTime=" + creationTime +
                ", executionTime=" + executionTime +
                ", priority=" + priority +
                '}';
    }
}
