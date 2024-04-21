package com.example.quanlynhatroapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.R;
import com.example.quanlynhatroapplication.ServiceActivity;

import java.util.List;

public class ListServiceAdapter extends RecyclerView.Adapter<ListServiceAdapter.viewholder> {
    ServiceActivity context;
    List<Service> items;

    public ListServiceAdapter(ServiceActivity context, List<Service> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListServiceAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_service, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListServiceAdapter.viewholder holder, int position) {
        holder.txtTenDV.setText(items.get(position).getTenDichVu());
        holder.txtGia.setText(items.get(position).getGia()+" đồng");
        holder.txtDonVi.setText("Đơn vị: " + items.get(position).getDonVi());

        Glide.with(context)
                .load(items.get(position).getLinkHinh())
                .into(holder.imgHinh);

//        holder.imgHinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.EditDichVu(items.get(position));
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.EditDichVu(items.get(position));
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.XoaDichVu(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtTenDV, txtGia, txtDonVi;
        ImageView imgHinh, btnXoa;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtTenDV = itemView.findViewById(R.id.textViewTenPhong);
            txtGia = itemView.findViewById(R.id.textViewGiaTien);
            txtDonVi = itemView.findViewById(R.id.textViewDonVi);

            imgHinh = itemView.findViewById(R.id.imageViewHinhDV);
            btnXoa = itemView.findViewById(R.id.btnXoa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //context.startActivity(new Intent(context, HouseDetailActivity.class));
                }
            });
        }


    }
}
