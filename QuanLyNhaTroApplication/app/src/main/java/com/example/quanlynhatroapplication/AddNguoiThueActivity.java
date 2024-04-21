package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ChonNguoiThueAdapter;
import com.example.quanlynhatroapplication.Class.Customer;
import com.example.quanlynhatroapplication.Class.NguoiThue;
import com.example.quanlynhatroapplication.Class.Room;
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

public class AddNguoiThueActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataKH;
    DatabaseReference dataNguoiThue;
    DatabaseReference dataSoLuongNguoiThue;
    TextInputEditText edtHoTen, edtSdt, edtEmail, edtTenPhong, edtNgaySinh, edtNoiSinh, edtCCCD, edtNgayCap, edtNoiCap;
    Customer customerChon;
    Dialog dialog;
    ImageView btnLuu;
    List<Customer> khachHangList;
    ChonNguoiThueAdapter nguoiThueAdapter;
    NguoiThue nguoiThue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nguoi_thue);

        Intent intent = getIntent();
        Room room = (Room) intent.getSerializableExtra("room");
        String houseId = intent.getStringExtra("houseId");

        database = FirebaseDatabase.getInstance();
        dataKH = database.getReference("KhachHang");
        dataNguoiThue = database.getReference("Nha/"+houseId+"/Phong/"+room.getId()+"/NguoiThue");
        dataSoLuongNguoiThue = database.getReference("SoLuongNguoiThue");

        khachHangList = new ArrayList<>();
        LayDuLieuKhachHang();
        nguoiThueAdapter = new ChonNguoiThueAdapter(this, khachHangList);

        AnhXa();
        edtTenPhong.setText(room.getTenPhong());

        setEvent();

    }

    @Override
    protected void onResume() {
        //LayDuLieuKhachHang();
        super.onResume();
    }

    private void LayDuLieuKhachHang() {
        dataKH.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khachHangList.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    Customer customer = new Customer();
                    customer.setId(item.child("id").getValue().toString());
                    customer.setHoTen(item.child("hoTen").getValue().toString());
                    customer.setSdt(item.child("sdt").getValue().toString());
                    customer.setEmail(item.child("email").getValue().toString());
                    customer.setTrangThaiThue(Integer.parseInt(item.child("trangThaiThue").getValue().toString()));
                    if(customer.getTrangThaiThue() == 0){
                        khachHangList.add(customer);
                    }
                }
                nguoiThueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ThemKhachHangMoi(Customer customer) {
        customer.setId(dataKH.push().getKey());
        dataKH.child(customer.getId()).setValue(customer);
    }

    private void setEvent() {
        edtHoTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChonNguoiThueDialog();
            }
        });
        edtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChonNguoiThueDialog();
            }
        });
        edtSdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChonNguoiThueDialog();
            }
        });

        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay(edtNgaySinh);
            }
        });

        edtNgayCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay(edtNgayCap);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaySinh = edtNgaySinh.getText().toString().trim();
                String noiSinh = edtNoiSinh.getText().toString().trim();
                String cccd = edtCCCD.getText().toString().trim();
                String ngayCap = edtNgayCap.getText().toString().trim();
                String noiCap = edtNoiCap.getText().toString().trim();
                if(customerChon == null){
                    showAlertDialog(AddNguoiThueActivity.this, "Thông báo", "Vui lòng chọn Người thuê phòng");
                }else if(ngaySinh.equals("") || noiSinh.equals("") || cccd.equals("") || ngayCap.equals("")|| noiCap.equals("")){
                    showAlertDialog(AddNguoiThueActivity.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                }else{
                    nguoiThue = new NguoiThue();
                    nguoiThue.setEmail(customerChon.getEmail());
                    nguoiThue.setHoTen(customerChon.getHoTen());
                    nguoiThue.setSoDienThoai(customerChon.getSdt());
                    nguoiThue.setCanCuocCongDan(cccd);
                    nguoiThue.setNgayCap(ngayCap);
                    nguoiThue.setNgaySinh(ngaySinh);
                    nguoiThue.setNoiSinh(noiSinh);
                    nguoiThue.setNoiCap(noiCap);
                    nguoiThue.setId(dataNguoiThue.push().getKey());
                    
                    dataNguoiThue.child(nguoiThue.getId()).setValue(nguoiThue, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error==null){
                                Toast.makeText(AddNguoiThueActivity.this, "Thêm người thuê thành công.", Toast.LENGTH_SHORT).show();
                                //Chuyen trang thai KhachHang thanh da thue
                                dataKH.child(customerChon.getId()).child("trangThaiThue").setValue(1);
                                // Tang so luong nguoi thue
                                TangSoLuongNguoiThue();
                                finish();
                            }else{
                                Toast.makeText(AddNguoiThueActivity.this, "Lỗi thêm người thuê", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void TangSoLuongNguoiThue() {
        Calendar calendar = Calendar.getInstance();
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-yyyy");
        calendar.set(nam,thang + 1, 0);
        String thangFormat = simpleDateFormat.format(calendar.getTime());
        final int[] soLuong = {0};
        dataSoLuongNguoiThue.child(thangFormat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    soLuong[0] = Integer.parseInt(snapshot.getValue().toString());
                }
                //Log.d("AAA", thangFormat + " : " + soLuong[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1s = 1000ms
                dataSoLuongNguoiThue.child(thangFormat).setValue(soLuong[0] + 1);
            }
        }, 1000);


    }

    private void AnhXa() {
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSdt = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        edtNoiSinh = findViewById(R.id.edtNoiSinh);
        edtCCCD = findViewById(R.id.edtCanCuocCongDan);
        edtNgayCap = findViewById(R.id.edtNgayCap);
        edtNoiCap = findViewById(R.id.edtNoiCap);
        btnLuu = findViewById(R.id.btnThem);
    }

    private void openChonNguoiThueDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chon_nguoithue);

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

        RecyclerView rvNguoiThue = dialog.findViewById(R.id.recyclerViewNguoiThue);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvNguoiThue.setLayoutManager(layoutManager);

        //nguoiThueAdapter = new ChonNguoiThueAdapter(this, khachHangList);
        rvNguoiThue.setAdapter(nguoiThueAdapter);

        ImageView btnHuy = dialog.findViewById(R.id.imageViewBack);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void chonNguoiThue(Customer customer){
        customerChon = customer;
        edtHoTen.setText(customerChon.getHoTen());
        edtEmail.setText(customerChon.getEmail());
        edtSdt.setText(customerChon.getSdt());
        dialog.dismiss();
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

    private void ChonNgay(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNguoiThueActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(year,month,dayOfMonth);
                        edt.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay
        );
        datePickerDialog.show();
    }
}