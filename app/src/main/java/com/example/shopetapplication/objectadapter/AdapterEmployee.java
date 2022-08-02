package com.example.shopetapplication.objectadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopetapplication.R;

import java.util.List;

public class AdapterEmployee extends RecyclerView.Adapter<AdapterEmployee.ViewHolder> {
    Context context;
    List<employee> list;

    OnCallBack onCallBack;

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public AdapterEmployee(Context context, List<employee> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_employee_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Untuk menampilkan data di card nanti
        holder.teksViuNama.setText(list.get(position).getEmp_name());
        holder.teksViuRole.setText("Role        : "+list.get(position).getEmp_role());
        holder.teksViuId.setText(list.get(position).getEmp_id());
        holder.teksViuNoPhone.setText("Phone    : "+list.get(position).getEmp_nophone());
        holder.teksViuAddress.setText("Address : "+list.get(position).getEmp_address());

        //Menambahkan fungsi button untuk hapus dan edit
        holder.tblHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBack.onTblHapus(list.get(position));
            }
        });

        holder.tblEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBack.onTblEdit(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView teksViuNama, teksViuRole, teksViuNoPhone, teksViuAddress, teksViuId;
        ImageButton tblHapus, tblEdit;

        public ViewHolder(View view) {
            super(view);

            teksViuNama = view.findViewById(R.id.teks_viu_name);
            teksViuRole = view.findViewById(R.id.teks_viu_role);
            teksViuNoPhone = view.findViewById(R.id.teks_viu_noP);
            teksViuAddress = view.findViewById(R.id.teks_viu_address);
            teksViuId = view.findViewById(R.id.teks_viu_id);

            tblHapus = itemView.findViewById(R.id.tbl_hapus);
            tblEdit = itemView.findViewById(R.id.tbl_edit);

        }
    }

    public interface OnCallBack{
        void onTblHapus(employee employee);
        void onTblEdit(employee employee);
    }
}
