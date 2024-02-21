package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class CreateTask extends AppCompatActivity {

    // Declare UI elements
    TextInputEditText editTextTaskTitle, editTextDescription, editTextDueDate;
    Spinner spinnerCategory;
    Button btnSave;
    MyDBHelper dbHelper;
    // Other declarations...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        dbHelper = new MyDBHelper(this);

        // Initialize UI elements
        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSave);

        // Set up a click listener for the "Save" button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(); // Call the method to save the task
            }
        });

        // Other setup...
    }

    // Other methods...

    // Method to save the task
    private void saveTask() {
        // Get task details from UI elements
        String title = editTextTaskTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String dueDate = editTextDueDate.getText().toString();
        String selectedCategory = spinnerCategory.getSelectedItem().toString();

        // For testing purposes, using a hardcoded user ID
        int userId = 1;

        // Retrieve the category ID based on the selected category name
        int categoryId = getCategoryIdByName(selectedCategory);

        // Insert the task into the database
        dbHelper.insertTask(title, description, dueDate, userId, categoryId);

        // Optionally, show a message indicating that the task has been saved
        Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show();
        startDashboardActivity();
    }

    // Method to retrieve category ID based on category name
    private int getCategoryIdByName(String categoryName) {
        // Hardcoded mapping of category names to IDs
        switch (categoryName) {
            case "Select a category":
                return 1;
            case "Completed":
                return 2;
            case "In Progress":
                return 3;
            case "Not Started":
                return 4;
            default:
                // Handle the case where the category name is not recognized
                return -1; // Or throw an exception, log an error, etc.
        }
    }
    private void startDashboardActivity() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish(); // Optional: Close the current activity if needed
    }

}
