package com.example.shopetapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class OwnerActivity extends AppCompatActivity {
    //definisikan object EditText dan Button
    EditText inputEmail, inputPassword;
    Button btnLogin, btnDaftar;

    //deklarasi instance FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        //inisialisasi FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //inisialisasi object ke komponen UI
        inputEmail      = findViewById(R.id.inputEmail);
        inputPassword   = findViewById(R.id.inputPassword);
        btnLogin        = findViewById(R.id.btnLogin);

        //setOnClickListener pada btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    public void loginUser(){
        //buat variabel lokal untuk menampung inputan dari Form
        String email    = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        //periksa inputan email, tampilkan toast jika kosong
        if(TextUtils.isEmpty(email)){
            Toast.makeText(OwnerActivity.this, "Email harus diisi", Toast.LENGTH_SHORT).show();
            inputEmail.setError("Email Owner Is Empty");
            return;
        }

        //periksa inputan password, tampilkan toast jika kosong
        if(TextUtils.isEmpty(password)){
            Toast.makeText(OwnerActivity.this, "Password harus diisi", Toast.LENGTH_SHORT).show();
            inputPassword.setError("Password Owner Is Empty");
            return;
        }

        //jika input email dan password oke, lanjutkan dengan FirebaseAuth
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(getApplicationContext(),AddadminActivity.class);
                            startActivity(a);
                            Toast.makeText(OwnerActivity.this, "Selamat Datang Owner", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OwnerActivity.this, "ANDA BUKAN OWNER DILARANG MASUK", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}