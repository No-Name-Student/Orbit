
package com.example.orbit;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (username.getText().toString()!="" && password.getText().toString()!="")
                {
                    Intent Login =new Intent(Register.this,LogIn.class);
                    Login.putExtra("newname",username.getText().toString());
                    Login.putExtra("newpass",password.getText().toString());
                    startActivity(Login);
                    finish();
                }
            }
        });
    }
}