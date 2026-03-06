package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    private int id;
    private String title;
    private boolean status;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private int priority;

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Task(int id, String title, boolean status,
                LocalDate createdDate, LocalDate dueDate, int priority) {

        this.id = id;
        this.title = title;
        this.status = status;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return status; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getDueDate() { return dueDate; }
    public int getPriority() { return priority; }

    public void markCompleted() {
        this.status = true;
    }

    public boolean isOverdue() {
        return !status && dueDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {

        return String.format(
                "%-5d %-20s %-12s %-15s %-15s %-8d",
                id,
                title,
                status ? "Completed" : "Pending",
                createdDate.format(formatter),
                dueDate.format(formatter),
                priority
        );
    }
}