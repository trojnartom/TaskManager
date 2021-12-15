package pl.trojnartom.workshops.TaskManager;


import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        taskLoader ();
        while (true) {
            Menu();
            String option = selectOption();
            if (!validOption(option)){
               printErrorMessage(option);
               continue;
            }
            executeOption(option);

        }
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

    }

    private static void removeTask() {

    }

    private static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please write task description: ");
        String desc = scanner.nextLine();
        System.out.print("Please write task due date: ");
        String date = scanner.nextLine();

        String important = null;
        do {
            System.out.print("Is Your task important (true/false)? ");
            important = scanner.nextLine();
        } while (!("false".equals(important) || ("true".equals(important))));
        tasks = ArrayUtils.add(tasks, new String[] {desc, date, important});
        System.out.println("Task was successfully added");
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
        System.out.println(BLUE + "Select an option: " + RESET);
    }

    private static void printWelcomeMessage() {
        String userName = System.getProperty("user.name");
        System.out.println("Hello " + userName);
    }

}
