import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String greeting = "Hey yo, I'm Travis.\nI make you work. \n";
        String goodbye = "    Bye bye, catch you soon.";
        Scanner sc = new Scanner(System.in);
        String input = "";
        ArrayList<Task> listOfTasks = new ArrayList<>();
        int numberOfTasks = 0;

        System.out.println(greeting);
        input = sc.nextLine();

        while (!input.equals("bye")) {
            String command = input.split(" ")[0];
            if (input.equals("list")) {
                System.out.println("    Here are the tasks in your list:");
                for (int i = 1; i <= numberOfTasks; i++) {
                    System.out.println("    " + i + ". " + listOfTasks.get(i - 1).getStatus());
                }
                System.out.println();
            } else if (command.equals("done")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                listOfTasks.get(taskNumber - 1).setDone(true);
                System.out.println("    Nice! I've marked this task as done: \n" + "      "
                        + listOfTasks.get(taskNumber - 1).getStatus());
            } else if (command.equals("delete")) {
                int deleteIndex = Integer.parseInt(input.split(" ")[1]);
                System.out.println("    Noted. I've removed this task:");
                System.out.println("      " + listOfTasks.get(deleteIndex - 1).getStatus());
                numberOfTasks--;
                System.out.println("    Now you have " + numberOfTasks + " tasks in the list.\n");
                listOfTasks.remove(deleteIndex - 1);
            } else {
                try {
                    if (!command.equals("todo") && !command.equals("event") && !command.equals("deadline")) {
                        throw new TaskException("    ☹ OOPS!!! I'm sorry, but I don't know what that means :-(\n");
                    }

                    String[] taskInformation = input.split(" ", 2);
                    if (taskInformation.length < 2) {
                        throw new TaskException(
                                "    ☹ OOPS!!! The description of a " + taskInformation[0] + " cannot be empty.\n");
                    }

                    if (command.equals("todo")) {
                        listOfTasks.add(new ToDo(taskInformation[1]));
                    } else {
                        String[] descriptionAndTime = taskInformation[1].split("/");
                        if (descriptionAndTime.length < 2) {
                            throw new TaskException(
                                    "    ☹ OOPS!!! The time of a " + taskInformation[0] + " cannot be empty.\n");
                        }
                        String description = descriptionAndTime[0];
                        String time = descriptionAndTime[1].split(" ", 2)[1];
                        if (command.equals("deadline")) {

                            listOfTasks.add(new Deadlines(description, time));
                        } else if (command.equals("event")) {

                            listOfTasks.add(new Event(description, time));
                        }
                    }

                    System.out.println("    Got it. I've added this task: ");
                    System.out.println("      " + listOfTasks.get(numberOfTasks).getStatus());
                    numberOfTasks++;
                    System.out.println("    Now you have " + numberOfTasks + " tasks in the list.\n");
                } catch (TaskException e) {
                    System.out.println(e.getMessage());
                }

            }

            input = sc.nextLine();
        }
        sc.close();
        System.out.println(goodbye);
    }
}
