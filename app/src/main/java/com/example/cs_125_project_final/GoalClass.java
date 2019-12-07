package com.example.cs_125_project_final;

/**
 * Goal Class to store information about the Goals.
 */
public class GoalClass {
    /**
     * Goal Title
     */
    private String title;
    /**
     * Array of String of tasks.
     */
    private String[] tasks = new String[0];
    /**
     * Array of String of completed tasks.
     */
    private String[] completedTasks = new String[0];

    /**
     * empty constructor.
     */
    public GoalClass(){
    }

    /**
     * getter for title of Goal
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for tasks
     * @return String array of tasks
     */
    public String[] getTasks() {
        return tasks;
    }

    /**
     * getter for completed tasks
     * @return String array of completed tasks
     */
    public String[] getCompletedTasks() {
        return completedTasks;
    }

    /**
     * setter for title
     * @param s String title to set
     */
    public void setTitle(String s) {
        title = s;
    }

    /**
     * Function to add a task to the task array
     * @param arg String task to add to the array
     */
    public void addTask(String arg) {
        int length = tasks.length + 1;
        String[] temp = new String[length];
        for (int i = 0; i < length - 1; i++) {
            temp[i] = tasks[i];
        }
        temp[length - 1] = arg;
        tasks = temp;
    }
    /**
     * Function to add a task to the completed task array
     * @param arg String task to add to the array
     */
    public void addCompletedTask(String arg) {
        int length = completedTasks.length + 1;
        String[] temp = new String[length];
        for (int i = 0; i < length - 1; i++) {
            temp[i] = completedTasks[i];
        }
        temp[length - 1] = arg;
        completedTasks = temp;
    }

    /**
     * Functio to remove a task from the task array
     * @param arg String task to remove from the array
     */
    public void removeTask(String arg) {
        int length = tasks.length - 1;
        String[] temp = new String[length];
        int counter = 0;
        for (int i = 0; i < tasks.length; i++) {
            if(!tasks[i].equals(arg)){
                temp[counter] = tasks[i];
                counter++;
            }
        }
        tasks = temp;
    }
}
