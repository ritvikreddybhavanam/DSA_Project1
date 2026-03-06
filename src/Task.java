import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    private int id;
    private String title;
    private boolean status;
    private LocalDate createdDate;
    private LocalDate dueDate;

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Task(int id, String title, boolean status,
                LocalDate createdDate, LocalDate dueDate) {

        this.id = id;
        this.title = title;
        this.status = status;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return status; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getDueDate() { return dueDate; }

    public void markCompleted() { this.status = true; }

    public boolean isOverdue() {
        return !status && dueDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {

        return String.format(
                "%-5d %-20s %-12s %-15s %-15s",
                id,
                title,
                status ? "Completed" : "Pending",
                createdDate.format(formatter),
                dueDate.format(formatter)
        );
    }
}