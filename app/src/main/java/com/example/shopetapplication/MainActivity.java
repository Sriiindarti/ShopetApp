package com.example.shopetapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnProd, btnEmp, btnAdm, btnLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProd = findViewById(R.id.product);
        btnEmp = findViewById(R.id.Employee);
        btnAdm = findViewById(R.id.addAdmin);
        btnLog = findViewById(R.id.logout);

        btnProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(),ProductActivity.class);
                startActivity(a);
            }
        });

        btnEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(getApplicationContext(),EmployeeActivity.class);
                startActivity(b);
            }
        });

        btnAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getApplicationContext(),OwnerActivity.class);
                startActivity(c);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(d);
                Toast.makeText(MainActivity.this, "Terimakasih, Untuk Kerjanya Hari ini", Toast.LENGTH_SHORT).show();
            }
        });
    }
}