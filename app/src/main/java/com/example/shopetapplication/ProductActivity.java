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

import com.example.shopetapplication.objectadapter.AdapterProducts;
import com.example.shopetapplication.objectadapter.products;
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

public class ProductActivity extends AppCompatActivity {

    //Deklarasi
    FloatingActionButton tblData;
    RecyclerView recyclerView;

    //Instance Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("products");

    List<products> list = new ArrayList<>();
    AdapterProducts adapterProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //untuk membersihkan database dulu
                list.clear();
                //Untuk menampilkan
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    products value = snapshot.getValue(products.class);
                    list.add(value);
                }
                adapterProducts = new AdapterProducts(ProductActivity.this, list);
                recyclerView.setAdapter(adapterProducts);

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
        adapterProducts.setOnCallBack(new AdapterProducts.OnCallBack() {
            @Override
            public void onTblHapus(products products) {
                //Membuat Fungsi Hapus
                hapusData(products);
            }

            @Override
            public void onTblEdit(products products) {
                showDialogEditData(products);
            }
        });
    }
    //---------------------------------------------------------------------------------------//



    //----------------------------- Metode Dialog Edit Data  --------------------------------//
    private void showDialogEditData(products products) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Untuk Memanggil Tambah_data Layout (Pop Up form untuk add data)
        dialog.setContentView(R.layout.form_data_layout);

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
        EditText txtPrice = dialog.findViewById(R.id.txt_price);
        EditText txtType = dialog.findViewById(R.id.txt_type);
        EditText txtStock = dialog.findViewById(R.id.txt_stock);

        TextView tvTitle = dialog.findViewById(R.id.tv_title);

        Button tblTambah = dialog.findViewById(R.id.tbl_tambah);

        //Untuk Membaca kemudian di get lagi kedalam Lyout Form
        txtName.setText(products.getProd_name());
        txtPrice.setText(products.getProd_price());
        txtType.setText(products.getProd_type());
        txtStock.setText(products.getProd_stock());

        //Untuk Mengganti Tulisan Header dan Button Pada Layout Form
        tblTambah.setText("Simpan");
        tvTitle.setText("Edit Products");


        //Validasi Jika Form Kosong
        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtName.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtName.setError("Name Is Empty");
                }
                else if (TextUtils.isEmpty(txtPrice.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtPrice.setError("Price Is Empty");
                }
                else if (TextUtils.isEmpty(txtType.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtType.setError("Type Is Empty");
                }
                else if (TextUtils.isEmpty(txtStock.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtStock.setError("Stock Is Empty");
                }
                else {
                    editData(products,
                            (txtName.getText().toString()),
                            (txtPrice.getText().toString()),
                            (txtType.getText().toString()),
                            (txtStock.getText().toString())
                    );
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    //---------------------------------------------------------------------------------------//



    //-------------------------------- Metode Edit Data  -----------------------------------//
    private void editData(products products, String newName, String newPrice, String newType, String newStock) {
        myRef.child(products.getProd_id()).child("prod_name").setValue(newName);
        myRef.child(products.getProd_id()).child("prod_price").setValue(newPrice);
        myRef.child(products.getProd_id()).child("prod_type").setValue(newType);
        myRef.child(products.getProd_id()).child("prod_stock").setValue(newStock).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Product Telah Berhasil Di Update",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //---------------------------------------------------------------------------------------//



    //--------------------------------- Methode Hapus Data ---------------------------------//
    private void hapusData(products products) {
        //Menghapus berdasarkan product Id sebagai Key Value
        myRef.child(products.getProd_id()).removeValue(new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(),"Product "+ products.getProd_name()+" Telah Dihapus",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //---------------------------------------------------------------------------------------//



    //---------------------------- Metode Dialog Tambah Data --------------------------------//
    private void showDialogTambahData() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Untuk Memanggil Tambah_data Layout (Pop Up form untuk add data)
        dialog.setContentView(R.layout.form_data_layout);

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
        EditText txtPrice = dialog.findViewById(R.id.txt_price);
        EditText txtType = dialog.findViewById(R.id.txt_type);
        EditText txtStock = dialog.findViewById(R.id.txt_stock);

        Button tblTambah = dialog.findViewById(R.id.tbl_tambah);

        //Validasi Jika Form Kosong
        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtName.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtName.setError("Name Is Empty");
                }
                else if (TextUtils.isEmpty(txtPrice.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtPrice.setError("Price Is Empty");
                }
                else if (TextUtils.isEmpty(txtType.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtType.setError("Type Is Empty");
                }
                else if (TextUtils.isEmpty(txtStock.getText())){
                    Toast.makeText(getApplicationContext(),"Silakkan Isi Data Terlebih Dahulu",Toast.LENGTH_SHORT).show();
                    txtStock.setError("Stock Is Empty");
                }
                else {
                    simpanData(
                            (txtName.getText().toString()),
                            (txtPrice.getText().toString()),
                            (txtType.getText().toString()),
                            (txtStock.getText().toString())
                    );
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    //---------------------------------------------------------------------------------------//



    //--------------------------------- Methode Simpan Data ---------------------------------//
    private void simpanData(String name, String price, String type, String stok) {
        String prod_id = "prod"+myRef.push().getKey();
        products products = new products(prod_id,name,price,type,stok);

        myRef.child(prod_id).setValue(products).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //---------------------------------------------------------------------------------------//

}