package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class ServiceAdapter3 extends RecyclerView.Adapter<ServiceAdapter3.viewholder> {
    Context context;
    List<Service> items;
    EditText edtTongTienDV;
    int tongTienDV = 0;

    public ServiceAdapter3(Context context, List<Service> items, EditText edtTongTienDV){
        this.context = context;
        this.items = items;
        this.edtTongTienDV = edtTongTienDV;
    }

    @NonNull
    @Override
    public ServiceAdapter3.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_service_4, parent, false);
        return new ServiceAdapter3.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter3.viewholder holder, int position) {
        Service service = items.get(position);
        holder.txtGiaDichVu.setText(service.getGia()+"đ/"+service.getDonVi());
        holder.txtTenDichVu.setText(service.getTenDichVu());
        holder.txtThanhTien.setText("0đ/tháng");

        final int[] soLuongTruoc = new int[1];
        final int[] soLuongSau = new int[1];

        holder.edtSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().equals("")){
                    soLuongTruoc[0] = 0;
                }else {
                    soLuongTruoc[0] = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    soLuongSau[0] = 0;
                }else {
                    soLuongSau[0] = Integer.parseInt(s.toString());
                }
                int tienCongVao = (soLuongSau[0] - soLuongTruoc[0]) * service.getGia();
                int thanhTien = soLuongSau[0] * service.getGia();
                holder.txtThanhTien.setText(thanhTien+"đ/tháng");
                tongTienDV += tienCongVao;
                edtTongTienDV.setText(String.valueOf(tongTienDV));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtTenDichVu, txtGiaDichVu, txtThanhTien;
        EditText edtSoLuong;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtTenDichVu =  itemView.findViewById(R.id.textViewTenDichVu);
            txtGiaDichVu =  itemView.findViewById(R.id.textViewGiaDichVu);
            txtThanhTien =  itemView.findViewById(R.id.textViewThanhTien);
            edtSoLuong = itemView.findViewById(R.id.editTextSoLuong);
        }
    }
}
