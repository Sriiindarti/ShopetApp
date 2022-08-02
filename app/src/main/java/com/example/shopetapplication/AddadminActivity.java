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

import com.example.shopetapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddadminActivity extends AppCompatActivity {
    //definisikan object EditText dan Button
    EditText inputEmail, inputPassword;
    Button btnDaftar;

    //deklarasi instance FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addadmin);

        //inisialisasi FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //inisialisasi object ke komponen UI
        inputEmail      = findViewById(R.id.inputEmail);
        inputPassword   = findViewById(R.id.inputPassword);
        btnDaftar       = findViewById(R.id.btnDaftar);


        //setOnClickListener pada btnDaftar
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //panggil method registerUser
                registerUser();
            }
        });
    }

    public void registerUser(){
        //buat variabel lokal untuk menampung inputan dari Form
        String email    = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        //periksa inputan email, tampilkan toast jika kosong
        if(TextUtils.isEmpty(email)){
            Toast.makeText(AddadminActivity.this, "Email harus diisi", Toast.LENGTH_SHORT).show();
            inputPassword.setError("Email Is Empty");
            return;
        }

        //periksa inputan password, tampilkan toast jika kosong
        if(TextUtils.isEmpty(password)){
            Toast.makeText(AddadminActivity.this, "Password harus diisi", Toast.LENGTH_SHORT).show();
            inputPassword.setError("Password Is Empty");
            return;
        }
        //jika input email dan password oke, lanjutkan dengan FirebaseAuth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddadminActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddadminActivity.this, "Registrasi gagal, email sudah digunakan", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}