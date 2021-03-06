package com.example.intellify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edt_name, edt_city, edt_school, edt_email, edt_password;
    private Button btn_submit;
    private TextView txtLogin;

    private boolean isSigningUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_name = findViewById(R.id.edtname);
        edt_city = findViewById(R.id.edtcity);
        edt_school = findViewById(R.id.edtschool);
        edt_email = findViewById(R.id.edtEmail);
        edt_password = findViewById(R.id.edtPassword);

        btn_submit = findViewById(R.id.btnSubmit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.getText().toString().isEmpty() || edt_password.getText().toString().isEmpty()) {
                    if (isSigningUp && edt_name.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (isSigningUp) {
                    handlerSignup();
                } else {
                    handrlerLogin();
                }
            }
        });

        txtLogin = findViewById(R.id.textLoginInfo);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSigningUp) {
                    isSigningUp = false;
                    edt_name.setVisibility(View.GONE);
                    edt_city.setVisibility(View.GONE);
                    edt_school.setVisibility(View.GONE);
                    btn_submit.setText("Login");
                    txtLogin.setText("Don't have an account? Sign up");
                } else {
                    isSigningUp = true;
                    edt_name.setVisibility(View.VISIBLE);
                    edt_city.setVisibility(View.VISIBLE);
                    edt_school.setVisibility(View.VISIBLE);
                    btn_submit.setText("Sign up");
                    txtLogin.setText("Already have an account? Log in");
                }
            }
        });
    }

    private void handlerSignup() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edt_email.getText().toString(), edt_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(edt_name.getText().toString(), edt_city.getText().toString(), edt_school.getText().toString(), edt_email.getText().toString()));
                    Toast.makeText(MainActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handrlerLogin() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edt_email.getText().toString(), edt_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                     Intent intent = new Intent(MainActivity.this, Retrive_Data.class);
                     Toast.makeText(MainActivity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                      startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
