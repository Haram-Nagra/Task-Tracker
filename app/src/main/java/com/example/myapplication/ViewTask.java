package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ViewTask extends AppCompatActivity {

    // Declare UI elements
    TextView taskInfo1, taskInfo2, taskInfo3, taskInfo4, taskInfo5, taskInfo6;
    MyDBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task);

        dbHelper = new MyDBHelper(this);  // Initialize dbHelper

        // Initialize UI elements
        taskInfo1 = findViewById(R.id.taskInfo1);
        taskInfo2 = findViewById(R.id.taskInfo2);
        taskInfo3 = findViewById(R.id.taskInfo3);
        taskInfo4 = findViewById(R.id.taskInfo4);
        taskInfo5 = findViewById(R.id.taskInfo5);
        taskInfo6 = findViewById(R.id.taskInfo6);

        // Display task information in TextViews
        displayTaskInformation();
    }

    private void displayTaskInformation1() {
        // Replace with your logic to fetch tasks from the database
        // For testing, I'll use dummy user ID
        int userId = 1;

        List<MyDBHelper.Task> tasks = dbHelper.fetchTasksFromDatabase(userId);

        if (tasks.isEmpty()) {
            // No tasks found
            taskInfo1.setText("No tasks available.");
            taskInfo2.setVisibility(View.GONE);
        } else {
            // Display the first task in taskInfo1 and the second task in taskInfo2
            if (tasks.size() > 0) {
                MyDBHelper.Task task1 = tasks.get(0);
                String taskInfoText1 = createTaskInfoString(task1);
                taskInfo1.setText(taskInfoText1);
            }

            if (tasks.size() > 1) {
                MyDBHelper.Task task2 = tasks.get(1);
                String taskInfoText2 = createTaskInfoString(task2);
                taskInfo2.setText(taskInfoText2);
            }
        }
    }

    private String createTaskInfoString(MyDBHelper.Task task) {
        // Build the task information string
        return "Task Title: " + task.getTaskTitle() + "\n" +
                "Description: " + task.getDescription() + "\n" +
                "Due Date: " + task.getDueDate() + "\n" +
                "Category: " + task.getCategoryName();
    }

    private void displayTaskInformation() {
        // Replace with your logic to fetch tasks from the database
        // For testing, I'll use dummy task information
        String taskTitle1 = "Task1";
        String taskDescription1 = "Trying to do Golang stuff.";
        String dueDate1 = "2024-02-08";
        String category1 = "In Progress";

        // Build the task information string
        String taskInfoText1 = "Task Title: " + taskTitle1 + "\n" +
                "Description: " + taskDescription1 + "\n" +
                "Due Date: " + dueDate1 + "\n" +
                "Category: " + category1;

        String taskTitle2 = "Task2";
        String taskDescription2 = "Making a software that allows you to window-shop clothes via home that is virtual try on";
        String dueDate2 = "2024-01-18";
        String category2 = "In Progress";

        // Build the task information string
        String taskInfoText2 = "Task Title: " + taskTitle2 + "\n" +
                "Description: " + taskDescription2 + "\n" +
                "Due Date: " + dueDate2 + "\n" +
                "Category: " + category2;
        String taskTitle3 = "Task3";
        String taskDescription3 = "Doing work for web application with react and js.";
        String dueDate3 = "2024-02-01";
        String category3 = "Not Completed";

        // Build the task information string
        String taskInfoText3 = "Task Title: " + taskTitle3 + "\n" +
                "Description: " + taskDescription3 + "\n" +
                "Due Date: " + dueDate3 + "\n" +
                "Category: " + category3;
        String taskTitle4 = "Task4";
        String taskDescription4 = "Create wireframes and design the overall layout of the website";
        String dueDate4 = "2024-02-28";
        String category4 = "Completed";

        // Build the task information string
        String taskInfoText4 = "Task Title: " + taskTitle4 + "\n" +
                "Description: " + taskDescription4 + "\n" +
                "Due Date: " + dueDate4 + "\n" +
                "Category: " + category4;
        String taskTitle5 = "Task5";
        String taskDescription5 = "Improve the speed and performance of the website.";
        String dueDate5 = "2024-02-18";
        String category5 = "Completed";

        // Build the task information string
        String taskInfoText5 = "Task Title: " + taskTitle5 + "\n" +
                "Description: " + taskDescription5 + "\n" +
                "Due Date: " + dueDate5 + "\n" +
                "Category: " + category5;
        String taskTitle6 = "Tracker Task";
        String taskDescription6 = "I am currently doing java development and thus making an app called task tracker.";
        String dueDate6 = "2024-02-28";
        String category6 = "Completed";

        // Build the task information string
        String taskInfoText6 = "Task Title: " + taskTitle6 + "\n" +
                "Description: " + taskDescription6 + "\n" +
                "Due Date: " + dueDate6 + "\n" +
                "Category: " + category6;

        // Set the task information in the TextViews
        taskInfo1.setText(taskInfoText1);
        taskInfo2.setText(taskInfoText2);
        taskInfo3.setText(taskInfoText3);
        taskInfo4.setText(taskInfoText4);
        taskInfo5.setText(taskInfoText5);
        taskInfo6.setText(taskInfoText6);

    }


    public void backbutton(View view) {
        setContentView(R.layout.dashboard);
    }
}





