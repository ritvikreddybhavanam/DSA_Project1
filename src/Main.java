import models.Task;
import services.TaskLinkedList;
import services.LinearSearch;
import services.BubbleSort;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BOLD = "\033[0;1m";

    static int idCounter = 1;

    public static void printCentered(String text) {
        int width = 74;
        int padding = (width - text.length()) / 2;
        System.out.print(" ".repeat(Math.max(0, padding)) + text);
        System.out.println();
    }

    public static void displayMenu() {
        System.out.println("\n" + BLUE + BOLD);
        System.out.println("   ╔══════════════════════════════════════════════════════════════════════╗");
        printCentered("🚀 TASKFLOW MANAGEMENT SYSTEM");
        System.out.println("   ╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println(RESET + CYAN);

        System.out.println("     [1] ➕ Add New Task             [6] ❌ Delete Task");
        System.out.println("     [2] 📋 View All Tasks           [7] ⚠️  Show Overdue");
        System.out.println("     [3] ⭐ View by Priority         [8] ↩️  Undo Last Delete");
        System.out.println("     [4] ✅ Mark Completed           [9] 🆔 Search by ID");
        System.out.println("     [5] 🔍 Search by Title          [0] 🚪 Exit System");
        System.out.println();

        System.out.println(BLUE + BOLD);
        System.out.println("   ╚══════════════════════════════════════════════════════════════════════╝");
        System.out.print(RESET + YELLOW + BOLD + "   ➤ Select Option: " + RESET);
    }

    public static Task userInput(Scanner sc) {
        System.out.println("\n" + PURPLE + BOLD + "   ─── ADD NEW TASK ───" + RESET);

        String title = "";
        while (title.trim().isEmpty()) {
            System.out.print(CYAN + "   Enter Title: " + RESET);
            title = sc.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println(RED + "   ⚠ Title cannot be empty!" + RESET);
            }
        }

        LocalDate dueDate = null;
        int priority = 0;

        while (true) {
            try {
                System.out.print(CYAN + "   Enter Due Date (YYYY-MM-DD): " + RESET);
                String dateInput = sc.nextLine();
                dueDate = LocalDate.parse(dateInput);

                if (dueDate.isBefore(LocalDate.now())) {
                    System.out.println(RED + "   ⚠ Due date cannot be in the past!" + RESET);
                    continue;
                }

                System.out.print(CYAN + "   Enter Priority (1-10): " + RESET);
                priority = Integer.parseInt(sc.nextLine());

                if (priority < 1 || priority > 10) {
                    System.out.println(RED + "   ⚠ Priority must be between 1 and 10." + RESET);
                    continue;
                }

                break;
            } catch (Exception e) {
                System.out.println(RED + "   ⚠ Invalid input! Ensure date is YYYY-MM-DD and priority is a number." + RESET);
            }
        }

        return new Task(idCounter++, title, false, LocalDate.now(), dueDate, priority);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskLinkedList list = new TaskLinkedList();

        while (true) {
            displayMenu();

            int choice;
            try {
                String input = sc.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(RED + "   ⚠ Invalid input! Please enter a number." + RESET);
                continue;
            }

            switch (choice) {
                case 1 -> {
                    list.addTask(userInput(sc));
                    System.out.println(GREEN + "   ✔ Task created successfully!" + RESET);
                }
                case 2 -> {
                    BubbleSort.bubbleSortById(list.getHead());
                    list.displayTasks();
                }
                case 3 -> list.showTasksByPriority();
                case 4 -> {
                    System.out.print(CYAN + "   Enter Task ID to complete: " + RESET);
                    try {
                        list.markCompleted(Integer.parseInt(sc.nextLine()));
                    } catch (Exception e) {
                        System.out.println(RED + "   ⚠ Invalid ID format." + RESET);
                    }
                }
                case 5 -> {
                    System.out.print(CYAN + "   Enter title keyword: " + RESET);
                    String keyword = sc.nextLine();
                    System.out.println(PURPLE + "\n   ─── SEARCH RESULTS ───" + RESET);
                    LinearSearch.searchByTitle(list.getHead(), keyword);
                }
                case 6 -> {
                    System.out.print(CYAN + "   Enter Task ID to delete: " + RESET);
                    try {
                        list.deleteTask(Integer.parseInt(sc.nextLine()));
                    } catch (Exception e) {
                        System.out.println(RED + "   ⚠ Invalid ID format." + RESET);
                    }
                }
                case 7 -> list.showOverdue();
                case 8 -> list.undoTask();
                case 9 -> {
                    System.out.print(CYAN + "   Enter Task ID to find: " + RESET);
                    try {
                        int searchId = Integer.parseInt(sc.nextLine());
                        System.out.println(PURPLE + "\n   ─── ID SEARCH RESULT ───" + RESET);
                        // Passes list.getHead() which is a Node
                        LinearSearch.searchById(list.getHead(), searchId);
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "   ⚠ Invalid ID format! Please enter a number." + RESET);
                    }
                }
                case 0 -> {
                    System.out.println(GREEN + "   Exiting... Goodbye!" + RESET);
                    sc.close();
                    return;
                }
                default -> System.out.println(RED + "   ⚠ Invalid choice! Please select 0-9." + RESET);
            }
        }
    }
}