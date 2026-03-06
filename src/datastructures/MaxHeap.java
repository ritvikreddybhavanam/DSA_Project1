package datastructures;

import models.Task;

public class MaxHeap {
    private Task[] heap;
    private int size;
    private int capacity;

    public MaxHeap(int capactiy) {
        this.capacity = capactiy;
        heap = new Task[capactiy];
        size = 0;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return (2 * i) + 1;
    }

    private int rightChild(int i) {
        return (2 * i) + 2;
    }

    private void swap(int i, int j) {
        Task temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void insert(Task task) {
        if (size == capacity) {
            System.out.println("Heap is full");
            return;
        }
        heap[size] = task;
        int current = size;
        size++;

        while (current > 0 && heap[current].getPriority() > heap[parent(current)].getPriority()) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public Task extractMax() {

        if (size == 0)
            return null;

        Task max = heap[0];

        heap[0] = heap[size - 1];
        size--;

        heapify(0);

        return max;
    }

    private void heapify(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && heap[left].getPriority() > heap[largest].getPriority()) {
            largest = left;
        }
        if (right < size && heap[right].getPriority() > heap[largest].getPriority()) {
            largest = right;
        }
        if (largest != i) {
            swap(i, largest);
            heapify(largest);
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
}
