package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewholder> {
    Context context;
    List<Service> items;
    List<Service> itemsChecked;

    public ServiceAdapter(Context context, List<Service> items, List<Service> itemsChecked){
        this.context = context;
        this.items = items;
        this.itemsChecked = itemsChecked;
    }

    @NonNull
    @Override
    public ServiceAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_service_2, parent, false);
        return new ServiceAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.viewholder holder, int position) {
        Service service = items.get(position);
        holder.txtGiaDichVu.setText(service.getGia()+"đ/tháng");
        holder.cbTenDichVu.setText(service.getTenDichVu());

        holder.cbTenDichVu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) itemsChecked.add(service);
                if (!isChecked) itemsChecked.remove(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtGiaDichVu;
        CheckBox cbTenDichVu;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtGiaDichVu =  itemView.findViewById(R.id.textViewGiaDichVu);
            cbTenDichVu = itemView.findViewById(R.id.checkBoxTenDichVu);
        }
    }
}
