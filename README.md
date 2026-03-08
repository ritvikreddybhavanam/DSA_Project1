# 📋 Task Manager (Java Console Application)

## 📌 Project Overview
The Task Manager is a Java console-based application designed to help users organize and manage their daily tasks efficiently. The system allows users to add, delete, search, and update tasks while maintaining task priorities and deadlines.

This project demonstrates the practical implementation of Data Structures such as Arrays, Linked Lists, Stacks, Hashing, and Heap (Priority Queue) to efficiently manage task operations.

---

## 🎯 Objectives
- To design a simple task management system using Java.
- To demonstrate the practical use of Data Structures.
- To organize tasks based on priority and deadlines.
- To implement efficient searching and sorting techniques.
- To provide features like undo delete operations using stacks.
- To improve task management efficiency through structured data handling.

---

## ⚙️ Features
- Add new tasks with priority and due date
- Delete tasks
- Undo deleted tasks using Stack
- Search tasks by title
- Display all tasks
- Display tasks based on priority using Max Heap
- Show overdue tasks
- Mark tasks as completed

---

## 🧠 Data Structures Used

| Data Structure | Purpose |
|----------------|--------|
| Arrays | Store and manage tasks |
| Linked List | Dynamic task storage |
| Stack | Undo delete functionality |
| Heap (Max Heap) | Display tasks based on priority |
| Searching Algorithms | Find tasks quickly |

---

## 🏗 System Architecture

User  
↓  
Main.java (Console Interface)  
↓  
TaskLinkedList.java (Task Operations)  
↓  
Data Structures
- Linked List (Task Storage)
- Stack (Undo Delete)
- Max Heap (Priority Tasks)  
  ↓  
  Task.java (Task Data Model)

---

## 📂 Project Structure

TaskManagerApp
│
├── Main.java
│
├── models
│   └── Task.java
│
├── datastructures
│   ├── Node.java
│   ├── MyStack.java
│   └── MaxHeap.java
│
└── services
└── TaskLinkedList.java

---

## 🖥 Example Console Menu

===== TASK MANAGER =====

1. Add Task
2. Delete Task
3. Display All Tasks
4. Search Task
5. Mark Task Completed
6. Show Overdue Tasks
7. Undo Delete
8. Show Tasks by Priority
9. Exit

---

## 🔍 Algorithm Overview

### Add Task
1. Create a new task object.
2. Insert the task into the linked list.
3. Store task priority.

### Delete Task
1. Search task by ID.
2. Remove it from the linked list.
3. Push deleted task into stack for undo.

### Show Tasks by Priority
1. Insert tasks into Max Heap.
2. Extract tasks from heap.
3. Display tasks in descending priority order.

### Undo Delete
1. Pop task from stack.
2. Insert the task back into linked list.

---

## 🎓 Course Outcome Mapping

| CO | Concept | Implementation |
|----|--------|---------------|
| CO1 | Searching & Sorting | Task search and sorting |
| CO2 | Arrays & Linked List | Task storage |
| CO3 | Stacks & Queues | Undo delete functionality |
| CO4 | Hashing & Heap | Priority-based task display |

---

## 🚀 Technologies Used
- Java
- Object Oriented Programming
- Data Structures
- Java Time API (LocalDate)

---

## 📊 Advantages
- Efficient task organization
- Priority-based task management
- Demonstrates multiple data structures
- Simple and easy-to-use console interface

---

## ⚠ Limitations
- Console-based interface only
- No database storage
- Tasks are not saved permanently after program exit

---

## 🔮 Future Enhancements
- Add GUI interface using JavaFX or Swing
- Implement database storage (MySQL / SQLite)
- Add user authentication
- Implement notifications for overdue tasks
- Convert the application into a web or mobile application

---

## 📌 Conclusion
The Task Manager application demonstrates how different data structures can be applied to solve real-world problems. By integrating linked lists, stacks, heaps, and searching techniques, the system efficiently manages tasks, prioritizes important activities, and provides an organized way to track daily responsibilities. This project helps in understanding the practical implementation of core data structure concepts in Java.