package com.example.quanlynhatroapplication.Adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Fragment.BillFragment;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewholder> {
    BillFragment context;
    List<Bill> items;
    House house;
    Room room;


    public BillAdapter(BillFragment context, List<Bill> items, House house, Room room){
        this.context = context;
        this.items = items;
        this.house = house;
        this.room = room;
    }

    @NonNull
    @Override
    public BillAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View inflate = LayoutInflater.from(context.getContext()).inflate(R.layout.viewholder_bill, parent, false);
        return new BillAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.viewholder holder, int position) {
        Bill bill = items.get(position);
        holder.txtThang.setText(bill.getThang());
        if(bill.getTrangThai() == 0) {
            holder.txtTrangThai.setText("Chưa thanh toán");
            holder.layoutBill.setBackgroundResource(R.drawable.bg_bill);
        } else if (bill.getTrangThai() == 1) {
            holder.txtTrangThai.setText("Đã thanh toán");
            holder.layoutBill.setBackgroundResource(R.drawable.bg_bill_2);
        }
        holder.txtTongTien.setText((bill.getTienPhong() + bill.getTienDichVu())+"đ");
        holder.txtTienPhong.setText(bill.getTienPhong() +"đ");
        holder.txtTienDichVu.setText(bill.getTienDichVu() +"đ");
        holder.txtTenNha.setText(house.getTenNha());
        holder.txtTenPhong.setText(room.getTenPhong());
        holder.txtHanThanhToan.setText(bill.getHanThanhToan());
        holder.txtNgayThanhToan.setText(bill.getNgayThanhToan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog(bill);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtThang, txtTrangThai, txtTongTien, txtTenNha, txtTenPhong, txtTienPhong, txtTienDichVu, txtHanThanhToan, txtNgayThanhToan;
        ConstraintLayout layoutBill;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtThang =  itemView.findViewById(R.id.textViewThang);
            txtTrangThai =  itemView.findViewById(R.id.textViewTrangThai);
            txtTongTien =  itemView.findViewById(R.id.textViewTongTien);
            txtTienPhong =  itemView.findViewById(R.id.textViewTienPhong);
            txtTienDichVu =  itemView.findViewById(R.id.textViewTienDichVu);
            txtHanThanhToan =  itemView.findViewById(R.id.textViewHanThanhToan);
            txtNgayThanhToan =  itemView.findViewById(R.id.textViewNgayThanhToan);
            txtTenNha =  itemView.findViewById(R.id.textViewTenNha);
            txtTenPhong =  itemView.findViewById(R.id.textViewTenPhong);
            layoutBill =  itemView.findViewById(R.id.layoutBill);
        }
    }

    private void showBottomDialog(Bill bill) {

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
                context.openDialogChinhSuaHoaDon(context.getContext(), bill);
            }
        });

        layoutChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Chinh sua hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogChinhSuaHoaDon(context.getContext(), bill);
            }
        });

        layoutXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Xoa hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogXoaHoaDon(context.getContext(), bill);
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
