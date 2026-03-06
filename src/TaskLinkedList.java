class Node {
    Task task;
    Node next;

    Node(Task task) {
        this.task = task;
        this.next = null;
    }
}

class MyStack {
    private Node top;

    void push(Task task) {
        Node temp = new Node(task);
        temp.next = top;
        top = temp;
    }

    Task pop() {
        if (top == null) return null;
        Task task = top.task;
        top = top.next;
        return task;
    }
}

public class TaskLinkedList {
    private Node head;
    private MyStack undoStack = new MyStack();

    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = newNode;
    }

    public void sortTask() {
        if (head == null || head.next == null) return;
        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.task.getId() > current.next.task.getId()) {
                    Task temp = current.task;
                    current.task = current.next.task;
                    current.next.task = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public void displayTasks() {
        if (head == null) {
            System.out.println(Main.YELLOW + "\n   📂 Task list is empty." + Main.RESET);
            return;
        }
        sortTask();
        System.out.println(Main.PURPLE + Main.BOLD);
        System.out.println("   ┌──────┬──────────────────────┬────────────┬──────────────┬──────────────┐");
        System.out.printf("   │ %-4s │ %-20s │ %-10s │ %-12s │ %-12s │\n", "ID", "TITLE", "STATUS", "CREATED", "DUE DATE");
        System.out.println("   ├──────┼──────────────────────┼────────────┼──────────────┼──────────────┤");
        System.out.print(Main.RESET);

        Node temp = head;
        while (temp != null) {
            String status = temp.task.isCompleted() ? Main.GREEN + "✔ Done" : Main.RED + "⏳ Pending";
            System.out.printf("   │ %-4d │ %-20s │ %-19s " + Main.RESET + "│ %-12s │ %-12s │\n",
                    temp.task.getId(), temp.task.getTitle(), status,
                    temp.task.getCreatedDate(), temp.task.getDueDate());
            temp = temp.next;
        }
        System.out.println(Main.PURPLE + Main.BOLD + "   └──────┴──────────────────────┴────────────┴──────────────┴──────────────┘" + Main.RESET);
    }

    public void deleteTask(int id) {
        if (head == null) return;
        if (head.task.getId() == id) {
            undoStack.push(head.task);
            head = head.next;
            System.out.println(Main.GREEN + "✔ Deleted successfully." + Main.RESET);
            return;
        }
        Node temp = head;
        while (temp.next != null && temp.next.task.getId() != id) temp = temp.next;

        if (temp.next != null) {
            undoStack.push(temp.next.task);
            temp.next = temp.next.next;
            System.out.println(Main.GREEN + "✔ Deleted successfully." + Main.RESET);
        } else {
            System.out.println(Main.RED + "⚠ ID not found." + Main.RESET);
        }
    }

    public void markCompleted(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.task.getId() == id) {
                temp.task.markCompleted();
                System.out.println(Main.GREEN + "✔ Status updated!" + Main.RESET);
                return;
            }
            temp = temp.next;
        }
        System.out.println(Main.RED + "⚠ ID not found." + Main.RESET);
    }

    public void searchTask(String title) {
        Node temp = head;
        boolean found = false;
        int count = 0;

        // Header UI
        System.out.println("\n" + Main.BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃ SEARCH RESULTS FOR: " + String.format("%-26s", title) + " ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Main.RESET);

        while (temp != null) {
            if (temp.task.getTitle().toLowerCase().contains(title.toLowerCase())) {
                count++;
                // Each result in its own mini-card
                System.out.println(Main.GREEN + "  [ Result #" + count + " ]" + Main.RESET);
                System.out.println("  ID: " + temp.task.getId());
                System.out.println("  Task: " + Main.YELLOW + temp.task.getTitle() + Main.RESET);
                System.out.println("  Status: " + (temp.task.isCompleted() ? "✅ Done" : "⏳ Pending"));
                System.out.println(Main.RESET + "  ------------------------------------------------" + Main.RESET);
                found = true;
            }
            temp = temp.next;
        }

        if (!found) {
            System.out.println(Main.RED + "   ❌ No matches found for \"" + title + "\"" + Main.RESET);
        } else {
            System.out.println(Main.CYAN + "   ✨ Found " + count + " match(es)." + Main.RESET);
        }
    }

    public void showOverdue() {
        Node temp = head;
        boolean found = false;
        System.out.println(Main.RED + Main.BOLD + "\n   🚨 OVERDUE TASKS" + Main.RESET);
        while (temp != null) {
            if (temp.task.isOverdue()) {
                System.out.println("   " + Main.RED + "• " + temp.task.getTitle() + " (Due: " + temp.task.getDueDate() + ")" + Main.RESET);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println(Main.GREEN + "   No overdue tasks. Well done!" + Main.RESET);
    }

    public void undoTask() {
        Task restored = undoStack.pop();
        if (restored != null) {
            addTask(restored);
            System.out.println(Main.GREEN + "↩️ Restored: " + restored.getTitle() + Main.RESET);
        } else {
            System.out.println(Main.RED + "⚠ Nothing to undo!" + Main.RESET);
        }
    }
}