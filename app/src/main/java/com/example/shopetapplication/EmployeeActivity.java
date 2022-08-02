package com.example.shopetapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopetapplication.objectadapter.AdapterEmployee;
import com.example.shopetapplication.objectadapter.employee;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeActivity extends AppCompatActivity {

    //Deklarasi
    FloatingActionButton tblData;
    RecyclerView recyclerView;

    //Instance Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference empRef = database.getReference("employee");

    List<employee> list = new ArrayList<>();
    AdapterEmployee adapterEmployee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        tblData = findViewById(R.id.tbl_data);
        recyclerView = findViewById(R.id.resaikel_viu);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Tambahkan Fungsi Tambah Data
        tblData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTambahData();
            }
        });

        //Tambahkan Fungsi Baca Data
        bacaData();
    }


    //----------------------------------- Metode Baca Data ----------------------------------//
    private void bacaData() {
        empRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //untuk membersihkan database dulu
                list.clear();
                //Untuk menampilkan
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    employee value = snapshot.getValue(employee.class);
                    list.add(value);
                }
                adapterEmployee = new AdapterEmployee(EmployeeActivity.this, list);
                recyclerView.setAdapter(adapterEmployee);

                //Fungsi untuk delete dan update
                setClick();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
    //---------------------------------------------------------------------------------------//



    //--------------------------------- Metode Set Click ------------------------------------//
    private void setClick() {
        adapterEmployee.setOnCallBack(new AdapterEmployee.OnCallBack() {
            @Override
            public void onTblHapus(employee employee) {
                //Membuat Fungsi Hapus
                hapusData(employee);
            }

            @Override
            public void onTblEdit(employee employee) {
                showDialogEditData(employee);
            }
        });
    }
    //---------------------------------------------------------------------------------------//



    //----------------------------- Metode Dialog Edit Data  --------------------------------//
    private void showDialogEditData(employee employee) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Untuk Memanggil Tambah_data Layout (Pop Up form untuk add data)
        dialog.setContentView(R.layout.form_employee_layout);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);

        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_keluar);

        tblKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        EditText txtName = dialog.findViewById(R.id.txt_name);
        EditText txtRole = dialog.findViewById(R.id.txt_role);
        EditText txtPhone = dialog.findViewById(R.id.txt_noPhone);
        EditText txtAddress = dialog.findViewById(R.id.txt_address);

        TextView tvTitle = dialog.findViewById(R.id.tv_title);

        Button tblTambah = dialog.findViewById(R.id.tbl_tambah);

        //Untuk Membaca kemudian di get lagi kedalam Lyout Form
        txtName.setText(employee.getEmp_name());
        txtRole.setText(employee.getEmp_role());
        txtPhone.setText(employee.getEmp_nophone());
        txtAddress.setText(employee.getEmp_address());

        //Untuk Mengganti Tulisan Header dan Button Pada Layout Form
        tblTambah.setText("Simpan");
        tvTitle.setText("Edit Employee");


        //Validasi Jika Form Kosong
        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtName.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtName.setError("Name Is Empty");
                }
                else if (TextUtils.isEmpty(txtRole.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtRole.setError("Role Is Empty");
                }
                else if (TextUtils.isEmpty(txtPhone.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtPhone.setError("No Phone Is Empty");
                }
                else if (TextUtils.isEmpty(txtAddress.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtAddress.setError("Address Is Empty");
                }
                else {
                    editDataEmp(employee,
                            (txtName.getText().toString()),
                            (txtRole.getText().toString()),
                            (txtPhone.getText().toString()),
                            (txtAddress.getText().toString())
                    );
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    //---------------------------------------------------------------------------------------//



    //-------------------------------- Metode Edit Data  -----------------------------------//
    private void editDataEmp(employee employee, String newName, String newRole, String newPhone, String newAddress) {
        empRef.child(employee.getEmp_id()).child("emp_name").setValue(newName);
        empRef.child(employee.getEmp_id()).child("emp_role").setValue(newRole);
        empRef.child(employee.getEmp_id()).child("emp_nophone").setValue(newPhone);
        empRef.child(employee.getEmp_id()).child("emp_address").setValue(newAddress).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Data Telah Berhasil Di Update",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //---------------------------------------------------------------------------------------//



    //--------------------------------- Methode Hapus Data ---------------------------------//
    private void hapusData(employee employee) {
        //Menghapus berdasarkan Employee Id sebagai Key Value
        empRef.child(employee.getEmp_id()).removeValue(new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(),"Employee Data "+ employee.getEmp_name()+" Telah Dihapus",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //---------------------------------------------------------------------------------------//



    //---------------------------- Metode Dialog Tambah Data --------------------------------//
    private void showDialogTambahData() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Untuk Memanggil Tambah_data Layout (Pop Up form untuk add data)
        dialog.setContentView(R.layout.form_employee_layout);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);

        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_keluar);

        tblKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        EditText txtName = dialog.findViewById(R.id.txt_name);
        EditText txtRole = dialog.findViewById(R.id.txt_role);
        EditText txtPhone = dialog.findViewById(R.id.txt_noPhone);
        EditText txtAddress = dialog.findViewById(R.id.txt_address);

        Button tblTambah = dialog.findViewById(R.id.tbl_tambah);

        //Validasi Jika Form Kosong
        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtName.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtName.setError("Name Is Empty");
                }
                else if (TextUtils.isEmpty(txtRole.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtRole.setError("Role Is Empty");
                }
                else if (TextUtils.isEmpty(txtPhone.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtPhone.setError("No Phone Is Empty");
                }
                else if (TextUtils.isEmpty(txtAddress.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtAddress.setError("Address Is Empty");
                }
                else {
                    simpanData(
                            (txtName.getText().toString()),
                            (txtRole.getText().toString()),
                            (txtPhone.getText().toString()),
                            (txtAddress.getText().toString())
                    );
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    //---------------------------------------------------------------------------------------//



    //--------------------------------- Methode Simpan Data ---------------------------------//
    private void simpanData(String name, String role, String phone, String address) {
        String emp_id = "emp"+empRef.push().getKey();
        employee employee = new employee(emp_id,name,role,phone,address);

        empRef.child(emp_id).setValue(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //---------------------------------------------------------------------------------------//


}