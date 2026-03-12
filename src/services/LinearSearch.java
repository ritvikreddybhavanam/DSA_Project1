package services;

import models.Task;
import datastructures.Node;

public class LinearSearch {

    public static Task linearSearchById(Node head, int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.task.getId() == id) {
                return temp.task;
            }
            temp = temp.next;
        }
        return null;
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
            System.out.println("   ❌ No tasks found matching: " + keyword);
        }
    }
}