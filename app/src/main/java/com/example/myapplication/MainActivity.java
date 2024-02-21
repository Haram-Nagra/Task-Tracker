package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    RadioButton radioButtonLogin, radioButtonRegister; // Change 'View' to 'RadioButton'
    MyDBHelper dbHelper; // Declare dbHelper as a member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDBHelper(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioButtonLogin = findViewById(R.id.radioButtonLogin);
        radioButtonRegister = findViewById(R.id.radioButtonRegister);

        long userId = dbHelper.registerUser("haram@gmail.com", "1234");
    }

    public void loginbutton(View view) {
        // Get email and password from user input
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (radioButtonLogin.isChecked()) { // Use 'isChecked()' for RadioButton
            // Perform login using dbHelper
            int userId = dbHelper.loginUser(email, password);

            // Check the result of the login attempt
            if (userId != -1) {
                // Successful login
                Toast.makeText(this, "Login successful. UserID: " + userId, Toast.LENGTH_SHORT).show();
                startDashboardActivity(); // Navigate to dashboard
            } else {
                // Failed login
                Toast.makeText(this, "Login failed. Invalid email or password.", Toast.LENGTH_SHORT).show();
            }
        } else if (radioButtonRegister.isChecked()) { // Use 'isChecked()' for RadioButton
            // Register the user using dbHelper
            long userId = dbHelper.registerUser(email, password);

            // Check the result of the registration attempt
            if (userId != -1) {
                // Successful registration
                Toast.makeText(this, "Registration successful. UserID: " + userId, Toast.LENGTH_SHORT).show();
                startDashboardActivity(); // Navigate to dashboard
            } else {
                // Registration failed
                Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // No radio button selected (should not happen in a properly designed UI)
            Toast.makeText(this, "Please select either Login or Register.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startDashboardActivity() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish(); // Optional: Close the current activity if needed
    }

    // ... Other methods

}
