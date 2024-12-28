package com.example.orbit;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LogIn extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.registerButton);
        Bundle extras = getIntent().getExtras();
        String regname = "user";
        String regpass = "1234";
        if (extras != null) {
        regname = extras.getString("newname");
        regpass = extras.getString("newpass");
        }
        String finalRegname = regname;
        String finalRegpass = regpass;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((username.getText().toString().equals("user") && password.getText().toString().equals("1234"))||((username.getText().toString().equals(finalRegname) && password.getText().toString().equals(finalRegpass)))) {
                    Toast.makeText(LogIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent menu = new Intent(LogIn.this,Settings.class);
                    startActivity(menu);
                } else {
                    Toast.makeText(LogIn.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(LogIn.this, Register.class);
                    startActivity(register);
                    finish();
                }
            }
        });
    }
}