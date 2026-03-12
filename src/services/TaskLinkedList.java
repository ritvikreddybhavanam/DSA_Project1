package services;

import datastructures.MaxHeap;
import datastructures.MyStack;
import datastructures.Node;
import models.Task;

public class TaskLinkedList {
    private Node head;
    private MyStack undoStack = new MyStack();

    public Node getHead() {
        return head;
    }

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

    public void displayTasks() {
        if (head == null) {
            System.out.println("\n   📂 Task list is empty.");
            return;
        }
        BubbleSort.bubbleSortById(head);
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
                temp.task.markCompleted();
                System.out.println("✔ Status updated!");
                return;
            }
            temp = temp.next;
        }
        System.out.println("⚠ ID not found.");
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
        Task restored = (Task) undoStack.pop();
        if (restored != null) {
            addTask(restored);
            System.out.println("↩️ Restored: " + restored.getTitle());
        } else {
            System.out.println("⚠ Nothing to undo!");
        }
    }

    public void showTasksByPriority() {
        if (head == null) {
            System.out.println("\n   📂 No tasks available to prioritize.");
            return;
        }
        MaxHeap heap = new MaxHeap(100);
        Node temp = head;
        while (temp != null) {
            heap.insert(temp.task);
            temp = temp.next;
        }
        System.out.println("\n   ⭐ PRIORITY RANKING (Highest First)");
        System.out.println("   ┌──────────┬──────┬──────────────────────────────────┐");
        System.out.printf("   │ %-8s │ %-4s │ %-32s │\n", "RANK", "PRIO", "TASK TITLE");
        System.out.println("   ├──────────┼──────┼──────────────────────────────────┤");
        int rank = 1;
        while (!heap.isEmpty()) {
            Task task = heap.extractMax();
            System.out.printf("   │ #%-7d │ %-4d │ %-32s │\n", rank++, task.getPriority(), task.getTitle());
        }
        System.out.println("   └──────────┴──────┴──────────────────────────────────┘");
    }
}