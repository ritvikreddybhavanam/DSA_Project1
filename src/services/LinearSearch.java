package services;

import models.Task;
import datastructures.Node;

public class LinearSearch {

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public static void searchById(Node head, int id) {
        Node temp = head;
        boolean found = false;

        while (temp != null) {
            if (temp.task.getId() == id) {
                System.out.println(GREEN + "   ✔ Task Found:" + RESET);
                // Ensure your Task model has a clean toString() method
                System.out.println("   " + temp.task.toString());
                found = true;
                break;
            }
            temp = temp.next;
        }

        if (!found) {
            System.out.println(RED + "   ⚠ No task found with ID: " + id + RESET);
        }
    }

    public static void searchByTitle(Node head, String keyword) {
        Node temp = head;
        boolean found = false;

        while (temp != null) {
            if (temp.task.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("   [ID: " + temp.task.getId() + "] " + temp.task.getTitle() +
                        " | Status: " + (temp.task.isCompleted() ? "✅ Done" : "⏳ Pending"));
                found = true;
            }
            temp = temp.next;
        }

        if (!found) {
            System.out.println(RED + "   ❌ No tasks found matching: " + keyword + RESET);
        }
    }
}