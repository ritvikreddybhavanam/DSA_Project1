import models.Task;
import services.TaskLinkedList;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    // ===== Professional UI Colors =====
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
        int width = 80;
        int padding = (width - text.length()) / 2;
        System.out.println(" ".repeat(Math.max(0, padding)) + text);
    }

    public static void displayMenu() {
        System.out.println(BLUE + BOLD);
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        printCentered("🚀 TASK MANAGEMENT SYSTEM - DASHBOARD");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
        System.out.println(RESET + CYAN);

        System.out.println("   [1] ➕ Add New models.Task             [6] ⚠️  Show Overdue");
        System.out.println("   [2] 📋 View All Tasks           [7] ↩️  Undo Last Delete");
        System.out.println("   [3] ⭐ View Tasks by Priority   [8] 🚪 Exit System");
        System.out.println("   [4] ✅ Mark as Completed        ");
        System.out.println("   [5] 🔍 Search by Title          ");
        System.out.println("   [6] ❌ Delete models.Task              ");

        System.out.println(BLUE + BOLD);
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
        System.out.print(RESET + YELLOW + BOLD + " ➤ Select Option: " + RESET);
    }

    public static Task userInput(Scanner sc) {

        System.out.println("\n" + PURPLE + BOLD + "─── ADD NEW TASK ───" + RESET);

        System.out.print(CYAN + "Enter Title: " + RESET);
        String title = sc.nextLine();

        LocalDate dueDate = null;
        int priority = 0;

        while (true) {
            try {

                System.out.print(CYAN + "Enter Due Date (YYYY-MM-DD): " + RESET);
                dueDate = LocalDate.parse(sc.nextLine());

                if (dueDate.isBefore(LocalDate.now())) {
                    System.out.println(RED + "⚠ Due date cannot be in the past!" + RESET);
                    continue;
                }

                System.out.print(CYAN + "Enter Priority (Higher number = Higher priority): " + RESET);
                priority = Integer.parseInt(sc.nextLine());

                break;

            } catch (Exception e) {
                System.out.println(RED + "⚠ Invalid format! Try again." + RESET);
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
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println(RED + "⚠ Invalid input! Please enter a number." + RESET);
                continue;
            }

            switch (choice) {

                case 1 -> {
                    list.addTask(userInput(sc));
                    System.out.println(GREEN + "✔ models.Task created successfully!" + RESET);
                }

                case 2 -> list.displayTasks();

                case 3 -> list.showTasksByPriority();   // ⭐ NEW FEATURE

                case 4 -> {
                    System.out.print("Enter models.Task ID to complete: ");
                    try {
                        list.markCompleted(Integer.parseInt(sc.nextLine()));
                    } catch (Exception e) {
                        System.out.println(RED + "Invalid ID." + RESET);
                    }
                }

                case 5 -> {
                    System.out.print("Enter title keyword: ");
                    list.searchTask(sc.nextLine());
                }

                case 6 -> {
                    System.out.print("Enter models.Task ID to delete: ");
                    try {
                        list.deleteTask(Integer.parseInt(sc.nextLine()));
                    } catch (Exception e) {
                        System.out.println(RED + "Invalid ID." + RESET);
                    }
                }

                case 7 -> list.undoTask();

                case 8 -> {
                    System.out.println(GREEN + "Exiting... Goodbye!" + RESET);
                    return;
                }

                default -> System.out.println(RED + "⚠ Invalid choice!" + RESET);
            }
        }
    }
}