package pl.trojnartom.workshops.TaskManager;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class taskManagerApp {

    private static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    private static final String EXIT_OPTION = "exit";
    private static final String TASKS_FILE_NAME = "tasks.csv";

    private static String[][] tasks = new String[0][];

    public static void main(String[] args) {
        app();
    }


    public static void app() {
        printWelcomeMessage();
        taskLoader();
        while (true) {
            Menu();
            String option = selectOption();
            if (!validOption(option)) {
                printErrorMessage(option);
                continue;
            }
            executeOption(option);
                if(isExitOption(option)){
                break;
            }
        }
        printExitMessage();
        saveTasks();
    }

    private static void saveTasks(){
        try (PrintWriter writer = new PrintWriter(new File(TASKS_FILE_NAME))) {
            for (String [] taskList : tasks) {
                String taskLine = StringUtils.join(taskList, ",");
                writer.println(taskLine);
            }
            System.out.println(GREEN + tasks.length + " tasks has been saved." + RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printExitMessage() {
        System.out.println(GREEN + "Hope You enjoy this program. See Ya :)");
    }

    private static boolean isExitOption(String option) {
        return EXIT_OPTION.equalsIgnoreCase(option);
    }

    private static void executeOption(String option) {
        switch (option) {
            case "add": {
                addTask();
                break;
            }
            case "remove": {
                removeTask();
                break;
            }
            case "list": {
                listTask();
                break;
            }
            default: {
                break;
            }
        }
    }

    private static void listTask() {
        for (String[] task : tasks) {
            for (int j = 0; j < 3; j++) {
                System.out.print(task[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void removeTask() {
        if (tasks.length == 0){
            System.out.println(RED + "No tasks to remove. Your schedule is empty" + RESET);
        }
        System.out.println("Please select task to remove from: 0 - " + (tasks.length-1));
        Scanner scanner = new Scanner(System.in);
        int taskNumber;
        while (true){
            while (!(scanner.hasNextInt())) {
                scanner.nextLine();
                System.out.println("Invalid task no. Please give no between 0 - " + (tasks.length-1));
            }
            taskNumber = scanner.nextInt();
            if (taskNumber >=0 && taskNumber < tasks.length){
                break;
            } else {
                System.out.println(RED + "Invalid task no. Please give no between 0 - " + (tasks.length-1) + RESET);
            }
        }
        tasks = ArrayUtils.remove(tasks, taskNumber);
        System.out.println(GREEN + "Task was successfuly deleted" + RESET);
        saveTasks();
    }


    private static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please write task description: ");
        String desc = scanner.nextLine();
        System.out.print("Please write task due date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        String important;
        do {
            System.out.print("Is Your task important (true/false)? ");
            important = scanner.nextLine();
        } while (!("false".equals(important) || ("true".equals(important))));
        tasks = ArrayUtils.add(tasks, new String[] {desc, date, important});
        System.out.println(GREEN_BOLD + "Task was successfully added" + RESET);
        saveTasks();
    }

    private static void printErrorMessage(String option) {
        System.out.println(RED + "Invalid menu option: "+ option + RESET);
    }

    private static boolean validOption(String option) {
        return ArrayUtils.contains(OPTIONS, option);
    }

    private static String selectOption() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void taskLoader() {
        StringBuilder taskBuilder = new StringBuilder ();
        try (Scanner scanner = new Scanner(new File(TASKS_FILE_NAME))) {
            while (scanner.hasNextLine()){
                taskBuilder.append(scanner.nextLine()).append(";");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        String [] taskLines = taskBuilder.toString().split(";");
        for (String taskLine: taskLines) {
            String[] task = taskLine.split(",");
            tasks = ArrayUtils.add(tasks, task);
        }
        System.out.println(CYAN + tasks.length + " task has been read" + RESET);
    }


    private static void Menu() {
        System.out.println("Available options: ");
        for (String option: OPTIONS){
            System.out.println("" + option);
        }
        System.out.print(BLUE + "Select an option: " + RESET);
    }

    private static void printWelcomeMessage() {
        String userName = System.getProperty("user.name");
        System.out.println("Hello " + userName);
    }

}
