package com.example.quanlynhatroapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.BillAdapter;
import com.example.quanlynhatroapplication.Adapter.ListHouseAdapter;
import com.example.quanlynhatroapplication.AddBillActivity;
import com.example.quanlynhatroapplication.AddNguoiThueActivity;
import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.R;
import com.example.quanlynhatroapplication.ServiceActivity;
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
import java.util.List;

public class BillFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference dataNha;
    DatabaseReference dataHoaDon;
    Room room;
    House house = new House();
    String houseId;
    ImageView btnThemHoaDon;
    RecyclerView rvBill;
    BillAdapter billAdapter;
    List<Bill> billList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        AnhXa(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            room = (Room) bundle.getSerializable("room");
            houseId = bundle.getString("houseId");
        }

        billList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");
        dataHoaDon = database.getReference("Nha/"+houseId+"/Phong/"+room.getId()+"/HoaDon");

        LayDuLieuNha();
        //Log.d("AAA", house.getTenNha());
        //LayDuLieuHoaDon();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
        rvBill.setLayoutManager(layoutManager);

        billAdapter = new BillAdapter(this, billList, house, room);
        rvBill.setAdapter(billAdapter);

        setEvent(view);

        return view;
    }

    @Override
    public void onResume() {
        LayDuLieuHoaDon();
        super.onResume();
    }

    private void LayDuLieuHoaDon() {
        dataHoaDon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                billList.clear();
                for(DataSnapshot item:snapshot.getChildren()) {
                    Bill bill = new Bill();
                    bill.setId(item.child("id").getValue().toString());
                    bill.setThang(item.child("thang").getValue().toString());
                    bill.setNgayThanhToan(item.child("ngayThanhToan").getValue().toString());
                    bill.setGhiChu(item.child("ghiChu").getValue().toString());
                    bill.setTienPhong(Integer.parseInt(item.child("tienPhong").getValue().toString()));
                    bill.setTienDichVu(Integer.parseInt(item.child("tienDichVu").getValue().toString()));
                    bill.setTrangThai(Integer.parseInt(item.child("trangThai").getValue().toString()));
                    bill.setHanThanhToan(item.child("hanThanhToan").getValue().toString());

                    billList.add(bill);
                }
                billAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openDialogChinhSuaHoaDon(Context context, Bill bill){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_bill);

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

        TextInputEditText edtTongTienDV, edtThang, edtHanThanhToan, edtTienPhong, edtGhiChu, edtTenPhong, edtNgayThanhToan;
        Switch switchTrangThai;
        Button btnLuu, btnHuy;
        edtTongTienDV = dialog.findViewById(R.id.edtTongTienDichVu);
        edtThang = dialog.findViewById(R.id.edtThang);
        edtHanThanhToan = dialog.findViewById(R.id.edtHanThanhToan);
        edtTienPhong = dialog.findViewById(R.id.edtTienPhong);
        edtGhiChu = dialog.findViewById(R.id.edtGhiChu);
        btnLuu = dialog.findViewById(R.id.buttonLuu);
        btnHuy = dialog.findViewById(R.id.buttonHuy);
        edtTenPhong = dialog.findViewById(R.id.edtTenPhong);
        edtNgayThanhToan = dialog.findViewById(R.id.edtNgayThanhToan);
        edtNgayThanhToan.setVisibility(View.GONE);
        switchTrangThai = dialog.findViewById(R.id.switchTrangThai);

        if(bill.getTrangThai() == 1){
            switchTrangThai.setChecked(true);
        }else if(bill.getTrangThai() == 0){
            switchTrangThai.setChecked(false);
        }
        edtTongTienDV.setText(String.valueOf(bill.getTienDichVu()));
        edtThang.setText(bill.getThang());
        edtHanThanhToan.setText(bill.getHanThanhToan());
        edtTienPhong.setText(String.valueOf(bill.getTienPhong()));
        edtGhiChu.setText(bill.getGhiChu());
        edtTenPhong.setText(room.getTenPhong());
        edtNgayThanhToan.setText(bill.getNgayThanhToan());

        setEventDialog(edtThang, edtHanThanhToan, edtNgayThanhToan, switchTrangThai, context);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

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
                        showAlertDialog(context, "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                    }else{
                        bill.setTrangThai(1);
                        bill.setThang(thang);
                        bill.setGhiChu(ghiChu);
                        bill.setTienPhong(Integer.parseInt(tienPhong));
                        bill.setTienDichVu(Integer.parseInt(tienDichVu));
                        bill.setGhiChu(ghiChu);
                        bill.setNgayThanhToan(ngayThanhToan);
                        bill.setHanThanhToan(hanThanhToan);

                        dataHoaDon.child(bill.getId()).setValue(bill, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error == null){
                                    Toast.makeText(context, "Cập nhật hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                    billAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(context, "Lỗi cập nhật hóa đơn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else{
                    if(thang.equals("") || hanThanhToan.equals("") || tienPhong.equals("") || tienDichVu.equals("") || ghiChu.equals("")){
                        showAlertDialog(context, "Thông báo", "Vui lòng nhập đầy đủ thông tin");
                    }else{
                        bill.setTrangThai(0);
                        bill.setThang(thang);
                        bill.setGhiChu(ghiChu);
                        bill.setTienPhong(Integer.parseInt(tienPhong));
                        bill.setTienDichVu(Integer.parseInt(tienDichVu));
                        bill.setGhiChu(ghiChu);
                        bill.setNgayThanhToan("");
                        bill.setHanThanhToan(hanThanhToan);

                        dataHoaDon.child(bill.getId()).setValue(bill, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error == null){
                                    Toast.makeText(context, "Cập nhật hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                    billAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(context, "Lỗi cập nhật hóa đơn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        dialog.show();
    }


    public void openDialogXoaHoaDon(Context context, Bill bill){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc muốn xóa hóa đơn tháng " + bill.getThang() + " ?")
                .setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String hoaDonKey = bill.getId();
                                dataHoaDon.child(hoaDonKey).removeValue();

                                billList.remove(bill);
                                billAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "Xóa dịch vụ thành công", Toast.LENGTH_SHORT).show();

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



    private void LayDuLieuNha() {
        dataNha.orderByChild("id")
                .equalTo(houseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()) {
                    House nha = item.getValue(House.class);
                    house.setId(nha.getId());
                    house.setTenNha(nha.getTenNha());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent(View view) {
        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddBillActivity.class);
                intent.putExtra("room", room);
                intent.putExtra("houseId", houseId);
                startActivity(intent);
            }
        });
    }

    private void AnhXa(View view) {
        btnThemHoaDon = view.findViewById(R.id.imageViewThemHoaDon);
        rvBill = view.findViewById(R.id.recyclerViewBill);
    }

    private void ChonNgay(TextInputEditText edt, SimpleDateFormat simpleDateFormat, Context context){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void setEventDialog(TextInputEditText edtThang, TextInputEditText edtHanThanhToan, TextInputEditText edtNgayThanhToan, Switch switchTrangThai, Context context){
        edtThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
                ChonNgay(edtThang, simpleDateFormat, context);
            }
        });

        edtHanThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ChonNgay(edtHanThanhToan, simpleDateFormat, context);
            }
        });

        edtNgayThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ChonNgay(edtNgayThanhToan, simpleDateFormat, context);
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