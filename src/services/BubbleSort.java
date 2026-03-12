package services;

import models.Task;
import datastructures.Node;

public class BubbleSort {

    public static void bubbleSortById(Node head) {
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
}