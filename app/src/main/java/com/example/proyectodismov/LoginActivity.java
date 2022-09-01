package com.example.proyectodismov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button btnLogin;
    private TextView textRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login);
        textRegister = findViewById(R.id.text_register);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void checkauth(){
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        FirebaseUser userauth = mAuth.getCurrentUser();
        if(user.isEmpty()){
            email.setError("Introduzca un correo");
        }
        if(pass.isEmpty()){
            password.setError("Introduzca una contraseña");
        }
        if(!userauth.isEmailVerified()){
            Toast.makeText(LoginActivity.this, "Favor de autenticar su correo", Toast.LENGTH_SHORT).show();
        }
        else{
            login();
        }
    }
    private void login() {
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        FirebaseUser uuserauth = mAuth.getCurrentUser();
            mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Inicio de sesion fallido" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
