package pl.trojnartom.workshops.TaskManager;


import static pl.coderslab.ConsoleColors.BLUE;

public class taskManagerApp {

    private static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    private static final String EXIT_OPTION = "exit";
    private static final String TASKS_FILE_NAME = "tasks.csv";

    private static String[][] tasks = new String[0][];

    public static void main(String[] args) {
    }


    public static void app() {
        printWelcomeMessage ();
        Menu();

    }


    private static void printWelcomeMessage() {
        String userName = System.getProperty("user.name");
        System.out.println("Hello " + userName);
    }

    private static void Menu () {
        System.out.println("Available options: ");
        for (String option: OPTIONS){
            System.out.println("" + option);
        }
        System.out.println(BLUE);

    }

}
