package com.example.quanlynhatroapplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.NguoiThue;
import com.example.quanlynhatroapplication.Fragment.GuestListFragment;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class NguoiThueFragmentAdapter extends RecyclerView.Adapter<NguoiThueFragmentAdapter.viewholder> {
    GuestListFragment context;
    List<NguoiThue> items;

    public NguoiThueFragmentAdapter(GuestListFragment context, List<NguoiThue> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public NguoiThueFragmentAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View inflate = LayoutInflater.from(context.getContext()).inflate(R.layout.viewholder_chon_nguoithue, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiThueFragmentAdapter.viewholder holder, int position) {
        holder.txtHoTen.setText(items.get(position).getHoTen());
        holder.txtEmail.setText(items.get(position).getEmail());
        holder.txtSoDienThoai.setText(items.get(position).getSoDienThoai());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtHoTen, txtSoDienThoai, txtEmail;
        ImageView imgHinh;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtHoTen = itemView.findViewById(R.id.textViewTenPhong);
            txtEmail = itemView.findViewById(R.id.textViewTang);
            txtSoDienThoai = itemView.findViewById(R.id.textViewSoNguoi);
            imgHinh = itemView.findViewById(R.id.imageViewHinh);
        }
    }

    private void showBottomDialog(NguoiThue nguoiThue) {

        final Dialog dialog = new Dialog(context.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_layout);

        LinearLayout layoutChiTiet = dialog.findViewById(R.id.layoutChiTiet);
        LinearLayout layoutChinhSua = dialog.findViewById(R.id.layoutChinhSua);
        LinearLayout layoutXoa = dialog.findViewById(R.id.layoutXoa);

        layoutChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Chi tiet hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogChinhSuaNguoiThue(context.getContext(), nguoiThue);
            }
        });

        layoutChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Chinh sua hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogChinhSuaNguoiThue(context.getContext(), nguoiThue);
            }
        });

        layoutXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Xoa hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogXoaNguoiThue(context.getContext(), nguoiThue);
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
