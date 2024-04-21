package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ServiceAdapter3;
import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Class.Service;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBillActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataDV;
    DatabaseReference dataHoaDon;
    RecyclerView rvDichVu;
    List<Service> dichVuList;
    TextInputEditText edtTongTienDV, edtThang, edtHanThanhToan, edtTienPhong, edtGhiChu, edtTenPhong, edtNgayThanhToan;
    ImageView btnLuu;
    Switch switchTrangThai;
    ServiceAdapter3 serviceAdapter3;
    int tongTienDV = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        AnhXa();

        Intent intent = getIntent();
        Room room = (Room) intent.getSerializableExtra("room");
        String houseId = intent.getStringExtra("houseId");

        edtTienPhong.setText(String.valueOf(room.getGiaPhong()));
        edtTenPhong.setText(room.getTenPhong());
        edtTongTienDV.setText("0");
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        calendar.set(nam,thang, ngay);
        edtThang.setText(simpleDateFormat.format(calendar.getTime()));

        dichVuList = new ArrayList<>();
        rvDichVu = findViewById(R.id.recyclerViewDichVu);
        database = FirebaseDatabase.getInstance();
        dataDV = database.getReference("Nha/"+houseId+"/Phong/"+room.getId()+"/dichVuPhong");
        dataHoaDon = database.getReference("Nha/"+houseId+"/Phong/"+room.getId()+"/HoaDon");
        edtNgayThanhToan.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvDichVu.setLayoutManager(layoutManager);

        serviceAdapter3 = new ServiceAdapter3(this, dichVuList, edtTongTienDV);
        rvDichVu.setAdapter(serviceAdapter3);

        setEvent();
    }

    private void setEvent() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thang = edtThang.getText().toString().trim();
                String hanThanhToan = edtHanThanhToan.getText().toString().trim();
                String tienPhong = edtTienPhong.getText().toString().trim();
                String tienDichVu = edtTongTienDV.getText().toString().trim();
                String ghiChu = edtGhiChu.getText().toString().trim();
                String ngayThanhToan = edtNgayThanhToan.getText().toString().trim();


                if (switchTrangThai.isChecked()){
                    if(ngayThanhToan.equals("") || thang.equals("") || hanThanhToan.equals("") || tienPhong.equals("") || tienDichVu.equals("") || ghiChu.equals("")){
                        showAlertDialog(AddBillActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                    }else{
                        Bill bill = new Bill();
                        bill.setTrangThai(1);
                        bill.setThang(thang);
                        bill.setGhiChu(ghiChu);
                        bill.setTienPhong(Integer.parseInt(tienPhong));
                        bill.setTienDichVu(Integer.parseInt(tienDichVu));
                        bill.setGhiChu(ghiChu);
                        bill.setNgayThanhToan(ngayThanhToan);
                        bill.setHanThanhToan(hanThanhToan);
                        bill.setId(dataHoaDon.push().getKey());
                        dataHoaDon.child(bill.getId()).setValue(bill, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error == null){
                                    Toast.makeText(AddBillActivity.this, "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(AddBillActivity.this, "Lỗi thêm hóa đơn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else{
                    if(thang.equals("") || hanThanhToan.equals("") || tienPhong.equals("") || tienDichVu.equals("") || ghiChu.equals("")){
                        showAlertDialog(AddBillActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                    }else{
                        Bill bill = new Bill();
                        bill.setTrangThai(0);
                        bill.setThang(thang);
                        bill.setGhiChu(ghiChu);
                        bill.setTienPhong(Integer.parseInt(tienPhong));
                        bill.setTienDichVu(Integer.parseInt(tienDichVu));
                        bill.setGhiChu(ghiChu);
                        bill.setNgayThanhToan("");
                        bill.setHanThanhToan(hanThanhToan);
                        bill.setId(dataHoaDon.push().getKey());
                        dataHoaDon.child(bill.getId()).setValue(bill, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error == null){
                                    Toast.makeText(AddBillActivity.this, "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(AddBillActivity.this, "Lỗi thêm hóa đơn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        edtThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
                ChonNgay(edtThang, simpleDateFormat);
            }
        });

        edtHanThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ChonNgay(edtHanThanhToan, simpleDateFormat);
            }
        });

        edtNgayThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ChonNgay(edtNgayThanhToan, simpleDateFormat);
            }
        });

        switchTrangThai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchTrangThai.isChecked()){
                    edtNgayThanhToan.setVisibility(View.VISIBLE);
                }else {
                    edtNgayThanhToan.setVisibility(View.GONE);
                }
            }
        });
    }

    private void ChonNgay(TextInputEditText edt, SimpleDateFormat simpleDateFormat){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void AnhXa() {
        edtTongTienDV = findViewById(R.id.edtTongTienDichVu);
        edtThang = findViewById(R.id.edtThang);
        edtHanThanhToan = findViewById(R.id.edtHanThanhToan);
        edtTienPhong = findViewById(R.id.edtTienPhong);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        btnLuu = findViewById(R.id.btnThem);
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtNgayThanhToan = findViewById(R.id.edtNgayThanhToan);
        switchTrangThai = findViewById(R.id.switchTrangThai);
    }

    @Override
    protected void onResume() {
        docDLDichVu();
        super.onResume();
    }

    public void docDLDichVu(){
        dataDV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dichVuList.clear();
                for (DataSnapshot item:snapshot.getChildren()) {
                    Service dichVu = new Service();
                    dichVu.setId(item.child("id").getValue().toString());
                    dichVu.setDonVi(item.child("donVi").getValue().toString());
                    dichVu.setGia(Integer.parseInt(item.child("gia").getValue().toString()));
                    dichVu.setTenDichVu(item.child("tenDichVu").getValue().toString());
                    dichVu.setLinkHinh(item.child("linkHinh").getValue().toString());
                    dichVuList.add(dichVu);
                }
                serviceAdapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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