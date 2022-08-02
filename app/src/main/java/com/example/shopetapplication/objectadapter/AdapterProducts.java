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

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {
    Context context;
    List<products> list;

    OnCallBack onCallBack;

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public AdapterProducts(Context context, List<products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_product_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Untuk menampilkan data di card nanti
        holder.teksViuNama.setText(list.get(position).getProd_name());
        holder.teksViuPrice.setText("Price   : "+""+"Rp "+list.get(position).getProd_price());
        holder.teksViuType.setText("Type   : "+""+list.get(position).getProd_type());
        holder.teksViuStock.setText("Stock : "+""+list.get(position).getProd_stock());
        holder.teksViuId.setText(list.get(position).getProd_id());

        //Menambahkan fungsi untuk hapus dan edit
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

        TextView teksViuNama, teksViuPrice, teksViuType, teksViuStock, teksViuId;
        ImageButton tblHapus, tblEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            teksViuNama = itemView.findViewById(R.id.teks_viu_name);
            teksViuPrice = itemView.findViewById(R.id.teks_viu_price);
            teksViuType = itemView.findViewById(R.id.teks_viu_type);
            teksViuStock = itemView.findViewById(R.id.teks_viu_stock);
            teksViuId = itemView.findViewById(R.id.teks_viu_id);

            tblHapus = itemView.findViewById(R.id.tbl_hapus);
            tblEdit = itemView.findViewById(R.id.tbl_edit);
        }
    }

    public interface OnCallBack{
        void onTblHapus(products products);
        void onTblEdit(products products);
    }
}
