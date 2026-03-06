package datastructures;
import models.Task;

public class MyStack {
    private Node top;

    public void push(Task task) {
        Node temp = new Node(task);
        temp.next = top;
        top = temp;
    }

    public Task pop() {
        if (top == null) return null;
        Task task = top.task;
        top = top.next;
        return task;
    }
}