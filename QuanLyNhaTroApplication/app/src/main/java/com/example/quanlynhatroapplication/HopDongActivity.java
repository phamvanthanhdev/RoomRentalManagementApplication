package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ListHopDongAdapter;
import com.example.quanlynhatroapplication.Adapter.ListServiceAdapter;
import com.example.quanlynhatroapplication.Adapter.ServiceAdapter;
import com.example.quanlynhatroapplication.Adapter.ServiceAdapter2;
import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.HopDong;
import com.example.quanlynhatroapplication.Class.Service;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HopDongActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataHopDong;
    //DatabaseReference dataDV;
    DatabaseReference dataNguoiThue;
    RecyclerView rvHopDong;
    List<HopDong> hopDongList = new ArrayList<>();
    ListHopDongAdapter hopDongAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hop_dong);

        database = FirebaseDatabase.getInstance();
        dataHopDong = database.getReference("HopDong");
        //dataDV = database.getReference("DichVu");
        rvHopDong = findViewById(R.id.recyclerViewHopDong);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvHopDong.setLayoutManager(layoutManager);

        //LayDuLieuHopDong();
        hopDongAdapter = new ListHopDongAdapter(this, hopDongList);
        rvHopDong.setAdapter(hopDongAdapter);

    }

    @Override
    protected void onResume() {
        LayDuLieuHopDong();
        super.onResume();
    }

    private void LayDuLieuHopDong() {
        hopDongList.clear();
        dataHopDong.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HopDong hopDong = snapshot.getValue(HopDong.class);
                hopDongList.add(hopDong);
                hopDongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openDialogChinhSuaHopDong(HopDong hopDong){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_hopdong);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(false);

        AutoCompleteTextView completeNguoiDaiDien = dialog.findViewById(R.id.autoCompleteNguoiDaiDien);
        TextInputEditText edtTenNha = dialog.findViewById(R.id.edtTenNha);
        TextInputEditText edtTenPhong = dialog.findViewById(R.id.edtTenPhong);
        TextInputEditText edtCamKetNguoiThue = dialog.findViewById(R.id.edtCamKetNguoiThue);
        TextInputEditText edtTienPhong = dialog.findViewById(R.id.edtTienPhong);
        TextInputEditText edtTienCoc = dialog.findViewById(R.id.edtTienCoc);
        TextInputEditText edtNgayBatDau = dialog.findViewById(R.id.edtNgayBatDauTinhTien);
        TextInputEditText edtNgayTao = dialog.findViewById(R.id.edtNgayTaoHopDong);
        TextInputEditText edtKyHan = dialog.findViewById(R.id.edtKiHanThanhToan);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);
        Button btnLuu = dialog.findViewById(R.id.buttonLuu);

        RecyclerView rvDichVu = dialog.findViewById(R.id.recyclerViewDichVu);

        rvDichVu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Service> dichVuList = hopDong.getDichVu();
        ServiceAdapter2 serviceAdapter2 = new ServiceAdapter2(this, dichVuList);
        rvDichVu.setAdapter(serviceAdapter2);

        dataNguoiThue = database.getReference("Nha/"+hopDong.getIdNha()+"/Phong/"+hopDong.getIdPhong()+"/NguoiThue");
        ArrayAdapter<String> adapterNguoiDaiDien;
        List<String> tenNguoiDaiDien;

        tenNguoiDaiDien = new ArrayList<>();
        LayTenNguoiThue(tenNguoiDaiDien);
        adapterNguoiDaiDien = new ArrayAdapter<>(this, R.layout.list_item_drop_down, tenNguoiDaiDien);
        Toast.makeText(this, "size" + tenNguoiDaiDien.size(), Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        completeNguoiDaiDien.setText(hopDong.getHoTen());
        completeNguoiDaiDien.setAdapter(adapterNguoiDaiDien);
        edtTenPhong.setText(hopDong.getTenPhong());
        edtTenNha.setText(hopDong.getTenNha());
        edtTienPhong.setText(String.valueOf(hopDong.getTienPhong()));
        edtNgayTao.setText(simpleDateFormat.format(calendar.getTime()));
        edtCamKetNguoiThue.setText(String.valueOf(hopDong.getCamKetNguoiThue()));
        edtTienCoc.setText(String.valueOf(hopDong.getTienCoc()));
        edtNgayBatDau.setText(hopDong.getNgayBatDauTinhTien());
        edtKyHan.setText(hopDong.getKyHanThanhToan());

        setEventDialog(edtNgayBatDau, btnHuy, dialog);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTenNguoiDaiDien = completeNguoiDaiDien.getText().toString().trim();
                String camKetNguoiThue = edtCamKetNguoiThue.getText().toString().trim();
                String tienPhong = edtTienPhong.getText().toString().trim();
                String tienCoc = edtTienCoc.getText().toString().trim();
                String ngayBatDau = edtNgayBatDau.getText().toString().trim();
                String ngayTao = edtNgayTao.getText().toString().trim();
                String kyHan = edtKyHan.getText().toString().trim();
                if(camKetNguoiThue.equals("") ||
                        tienPhong.equals("") || tienCoc.equals("") || ngayBatDau.equals("") ||
                        ngayTao.equals("") || kyHan.equals("")){
                    showAlertDialog(HopDongActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin.");
                }else{
                    hopDong.setCamKetNguoiThue(Integer.parseInt(camKetNguoiThue));
                    hopDong.setTienPhong(tienPhong);
                    hopDong.setTienCoc(tienCoc);
                    hopDong.setNgayBatDauTinhTien(ngayBatDau);
                    hopDong.setNgayKyHopDong(ngayTao);
                    hopDong.setKyHanThanhToan(kyHan);
                    hopDong.setHoTen(hoTenNguoiDaiDien);
                    //hopDong.setDichVu(dichVuList);

                    String hopDongKey = hopDong.getId();
                    // Cập nhật thông tin
                    Map<String, Object> updateHopDong = new HashMap<>();
                    updateHopDong.put("camKetNguoiThue", hopDong.getCamKetNguoiThue());
                    updateHopDong.put("tienPhong", hopDong.getTienPhong());
                    updateHopDong.put("tienCoc", hopDong.getTienCoc());
                    updateHopDong.put("ngayBatDauTinhTien", hopDong.getNgayBatDauTinhTien());
                    updateHopDong.put("kyHanThanhToan", hopDong.getKyHanThanhToan());
                    updateHopDong.put("hoTen", hopDong.getHoTen());
                    updateHopDong.put("dichVu", hopDong.getDichVu());

                    dataHopDong.child(hopDongKey).updateChildren(updateHopDong, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error!=null){
                                showAlertDialog(HopDongActivity.this, "Thông báo", "Lỗi cập nhật hợp đồng.");
                            }else{
                                Toast.makeText(HopDongActivity.this, "Cập nhật hợp đồng thành công", Toast.LENGTH_SHORT).show();
                                //finish();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        dialog.show();
    }


    public void openDialogXoaHopDong(HopDong hopDong){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc muốn xóa hợp đồng phòng " + hopDong.getTenPhong()+ " nhà " + hopDong.getTenNha() + " ?")
                .setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String hopDongKey = hopDong.getId();
                                dataHopDong.child(hopDongKey).removeValue();

                                hopDongList.remove(hopDong);
                                hopDongAdapter.notifyDataSetChanged();
                                Toast.makeText(HopDongActivity.this, "Xóa hợp đồng thành công", Toast.LENGTH_SHORT).show();

                                dialogInterface.dismiss();
                            }
                        }
                )
                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void LayTenNguoiThue(List<String> tenNguoiDaiDien) {
        dataNguoiThue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tenNguoiDaiDien.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    tenNguoiDaiDien.add(item.child("hoTen").getValue().toString());
                    //Log.d("AAA", item.child("hoTen").getValue().toString());
                }
                //adapterNguoiDaiDien.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setEventDialog(TextInputEditText edtNgayBatDau, Button btnHuy, Dialog dialog){
        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay(edtNgayBatDau);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void ChonNgay(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(HopDongActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year,month, dayOfMonth);
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // Đóng hộp thoại khi nhấn OK
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}