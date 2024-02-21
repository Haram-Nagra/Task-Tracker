package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_tracker_db";
    private static final int DATABASE_VERSION = 2;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (UserID INTEGER PRIMARY KEY AUTOINCREMENT, Email TEXT UNIQUE, Password TEXT);");
        db.execSQL("CREATE TABLE Task (TaskID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Description TEXT, DueDate DATE,UserID INTEGER, CategoryID INTEGER, FOREIGN KEY (UserID) REFERENCES User(UserID), FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID));");
        db.execSQL("CREATE TABLE Category (CategoryID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT);");

        // Insert predefined categories
        insertCategory(db, "Completed");
        insertCategory(db, "In Progress");
        insertCategory(db, "Not Started");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User;");
        db.execSQL("DROP TABLE IF EXISTS Task;");
        db.execSQL("DROP TABLE IF EXISTS Category;");
        onCreate(db);
    }


    // Insert a new user into the User table with a randomly generated reset token
    public void insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("Password", password);
        // Inserting Row
        db.insert("User", null, values);
        db.close(); // Closing database connection
    }

    public long registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("Password", password);

        // Inserting Row
        long newRowId = db.insert("User", null, values);

        db.close(); // Closing database connection

        return newRowId;
    }

    public int loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;

        // Using parameterized query to prevent SQL injection
        String query = "SELECT UserID FROM User WHERE Email = ? AND Password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            int userIdColumnIndex = cursor.getColumnIndex("UserID");

            // Check if the column index is valid (greater than or equal to 0)
            if (userIdColumnIndex >= 0) {
                userId = cursor.getInt(userIdColumnIndex);
            } else {
                // Log an error or handle the situation where the column is not found
                Log.e("LoginUser", "Column 'UserID' not found in the cursor");
            }
        }

        cursor.close();
        db.close();

        return userId;
    }

    public void insertTask(String title, String description, String dueDate, int userId, int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Description", description);
        values.put("DueDate", dueDate);
        values.put("UserID", userId);
        values.put("CategoryID", categoryId);

        // Insert the task into the Task table
        db.insert("Task", null, values);

        db.close(); // Closing database connection
    }

    private void insertCategory(SQLiteDatabase db, String categoryName) {
        ContentValues values = new ContentValues();
        values.put("Name", categoryName);
        db.insert("Category", null, values);
    }
    public List<Task> fetchTasksFromDatabase(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> tasks = new ArrayList<>();

        // Construct the query with a JOIN clause to fetch category names
        String query = "SELECT Task.*, Category.Name AS CategoryName " +
                "FROM Task " +
                "INNER JOIN Category ON Task.CategoryID = Category.CategoryID " +
                "WHERE Task.UserID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        // Iterate through the cursor and create Task objects
        while (cursor.moveToNext()) {
            int taskId = cursor.getInt(cursor.getColumnIndexOrThrow("TaskID"));
            String taskTitle = cursor.getString(cursor.getColumnIndexOrThrow("Title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
            String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("DueDate"));
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));

            Task task = new Task(taskId, taskTitle, description, dueDate, userId, categoryName) {
                @Override
                public void run() {

                }
            };
            tasks.add(task);
        }

        cursor.close();
        return tasks;
    }

    public abstract class Task {
        private int taskId;
        private String taskTitle;
        private String description;
        private String dueDate;
        private int userId;
        private String categoryName;

        // Constructor
        public Task(int taskId, String taskTitle, String description, String dueDate, int userId, String categoryName) {
            this.taskId = taskId;
            this.taskTitle = taskTitle;
            this.description = description;
            this.dueDate = dueDate;
            this.userId = userId;
            this.categoryName = categoryName;
        }

        // Getters
        public int getTaskId() {
            return taskId;
        }

        public String getTaskTitle() {
            return taskTitle;
        }

        public String getDescription() {
            return description;
        }

        public String getDueDate() {
            return dueDate;
        }

        public int getUserId() {
            return userId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public abstract void run();
    }
    public List<String> fetchTaskCategories(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> taskCategories = new ArrayList<>();

        // Construct the query to fetch task categories for a specific user
        String query = "SELECT DISTINCT Category.Name FROM Task " +
                "JOIN Category ON Task.CategoryID = Category.CategoryID " +
                "WHERE Task.UserID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        // Iterate through the cursor and add task categories to the list
        while (cursor.moveToNext()) {
            String category = cursor.getString(0);
            taskCategories.add(category);
        }

        cursor.close();
        db.close();
        return taskCategories;
    }


}