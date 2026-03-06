package services;

import datastructures.MaxHeap;
import datastructures.MyStack;
import datastructures.Node;    
import models.Task;


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
                // FIXED: Direct access to current.task.getId()
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
            System.out.println("\n   📂 Task list is empty.");
            return;
        }
        sortTask();

        System.out.println("   ┌──────┬──────────────────────┬────────────┬──────────────┬──────────────┐");
        System.out.printf("   │ %-4s │ %-20s │ %-10s │ %-12s │ %-12s │\n", "ID", "TITLE", "STATUS", "CREATED", "DUE DATE");
        System.out.println("   ├──────┼──────────────────────┼────────────┼──────────────┼──────────────┤");

        Node temp = head;
        while (temp != null) {
            String status = temp.task.isCompleted() ? "✔ Done" : "⏳ Pending";
            System.out.printf("   │ %-4d │ %-20s │ %-10s │ %-12s │ %-12s │\n",
                    temp.task.getId(), temp.task.getTitle(), status,
                    temp.task.getCreatedDate(), temp.task.getDueDate());
            temp = temp.next;
        }
        System.out.println("   └──────┴──────────────────────┴────────────┴──────────────┴──────────────┘");
    }

    public void deleteTask(int id) {
        if (head == null) return;
        if (head.task.getId() == id) {
            undoStack.push(head.task);
            head = head.next;
            System.out.println("✔ Deleted successfully.");
            return;
        }
        Node temp = head;
        while (temp.next != null && temp.next.task.getId() != id) temp = temp.next;

        if (temp.next != null) {
            undoStack.push(temp.next.task);
            temp.next = temp.next.next;
            System.out.println("✔ Deleted successfully.");
        } else {
            System.out.println("⚠ ID not found.");
        }
    }

    public void markCompleted(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.task.getId() == id) {
                temp.task.markCompleted(); // Ensure this method exists in Task.java
                System.out.println("✔ Status updated!");
                return;
            }
            temp = temp.next;
        }
        System.out.println("⚠ ID not found.");
    }

    public void searchTask(String title) {
        Node temp = head;
        boolean found = false;
        int count = 0;

        System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("┃ SEARCH RESULTS FOR: %-26s ┃\n", title);
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        while (temp != null) {
            if (temp.task.getTitle().toLowerCase().contains(title.toLowerCase())) {
                count++;
                System.out.println("  [ Result #" + count + " ]");
                System.out.println("  ID: " + temp.task.getId());
                System.out.println("  Task: " + temp.task.getTitle());
                System.out.println("  Status: " + (temp.task.isCompleted() ? "✅ Done" : "⏳ Pending"));
                System.out.println("  ------------------------------------------------");
                found = true;
            }
            temp = temp.next;
        }

        if (!found) {
            System.out.println("   ❌ No matches found for \"" + title + "\"");
        } else {
            System.out.println("   ✨ Found " + count + " match(es).");
        }
    }

    public void showOverdue() {
        Node temp = head;
        boolean found = false;
        System.out.println("\n   🚨 OVERDUE TASKS");
        while (temp != null) {
            if (temp.task.isOverdue()) {
                System.out.println("   • " + temp.task.getTitle() + " (Due: " + temp.task.getDueDate() + ")");
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("   No overdue tasks. Well done!");
    }

    public void undoTask() {
        Task restored = (Task) undoStack.pop(); // FIXED: Cast to Task if MyStack uses Object
        if (restored != null) {
            addTask(restored);
            System.out.println("↩️ Restored: " + restored.getTitle());
        } else {
            System.out.println("⚠ Nothing to undo!");
        }
    }

    public void showTasksByPriority() {
        // Check if list is empty before building the heap
        if (head == null) {
            System.out.println(UIUtils.YELLOW + "\n   📂 No tasks available to prioritize." + UIUtils.RESET);
            return;
        }

        // Initialize MaxHeap
        MaxHeap heap = new MaxHeap(100);
        Node temp = head;

        // Fill the heap with tasks from the linked list
        while (temp != null) {
            heap.insert(temp.task);
            temp = temp.next;
        }

        // Header UI
        System.out.println("\n" + UIUtils.PURPLE + UIUtils.BOLD + "   ⭐ PRIORITY RANKING (Highest First)" + UIUtils.RESET);
        System.out.println(UIUtils.CYAN + "   ┌──────────┬──────┬──────────────────────────────────┐");
        System.out.printf("   │ %-8s │ %-4s │ %-32s │\n", "RANK", "PRIO", "TASK TITLE");
        System.out.println("   ├──────────┼──────┼──────────────────────────────────┤" + UIUtils.RESET);

        int rank = 1;
        while (!heap.isEmpty()) {
            Task task = heap.extractMax();

            // Color-coding based on priority level
            String prioColor = (task.getPriority() >= 7) ? UIUtils.RED : (task.getPriority() >= 4) ? UIUtils.YELLOW : UIUtils.GREEN;

            System.out.printf("   │ #%-7d │ " + prioColor + "%-4d" + UIUtils.RESET + " │ %-32s │\n",
                    rank++,
                    task.getPriority(),
                    task.getTitle());
        }

        System.out.println(UIUtils.CYAN + "   └──────────┴──────┴──────────────────────────────────┘" + UIUtils.RESET);
    }
}