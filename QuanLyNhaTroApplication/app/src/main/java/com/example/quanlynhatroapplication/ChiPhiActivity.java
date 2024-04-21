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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ListChiPhiAdapter;
import com.example.quanlynhatroapplication.Adapter.ListServiceAdapter;
import com.example.quanlynhatroapplication.Class.ChiPhi;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiPhiActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataChiPhi;
    RecyclerView rvChiPhi;
    List<ChiPhi> chiPhiList;
    ListChiPhiAdapter listChiPhiAdapter;
    ImageView btnThemChiPhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_phi);

        database = FirebaseDatabase.getInstance();
        dataChiPhi = database.getReference("ChiPhi");

        rvChiPhi = findViewById(R.id.recyclerViewChiPhi);
        btnThemChiPhi = findViewById(R.id.btnThemChiPhi);
        chiPhiList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvChiPhi.setLayoutManager(layoutManager);

        listChiPhiAdapter = new ListChiPhiAdapter(this, chiPhiList);
        rvChiPhi.setAdapter(listChiPhiAdapter);

        btnThemChiPhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openThemDichVuDialog(Gravity.CENTER);
            }
        });
    }

    @Override
    protected void onResume() {
        docDLDichVu();
        super.onResume();
    }

    private void openThemDichVuDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_chiphi);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        EditText edtTenChiPhi = dialog.findViewById(R.id.edtTenChiPhi);
        EditText edtGiaChiPhi = dialog.findViewById(R.id.edtGiaChiPhi);
        EditText edtNoiDung = dialog.findViewById(R.id.edtNoiDung);
        EditText edtThang = dialog.findViewById(R.id.edtThang);
        Button btnHuy = dialog.findViewById(R.id.buttonHuyDichVu);
        Button btnThem = dialog.findViewById(R.id.buttonThemDichVu);

        edtThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonThang(edtThang);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiPhi = edtTenChiPhi.getText().toString().trim();
                String giaChiPhi = edtGiaChiPhi.getText().toString().trim();
                String noiDung = edtNoiDung.getText().toString().trim();
                String thang = edtThang.getText().toString().trim();
                if(tenChiPhi.equals("") || giaChiPhi.equals("") || noiDung.equals("") || thang.equals("")){
                    showAlertDialog(ChiPhiActivity.this, "Thông báo", "Vui lòng nhập đủ thông tin.");
                }else{
                    ChiPhi chiPhi = new ChiPhi();
                    chiPhi.setId(dataChiPhi.push().getKey());
                    chiPhi.setTen(tenChiPhi);
                    chiPhi.setNoiDung(noiDung);
                    chiPhi.setSoTien(Integer.parseInt(giaChiPhi));
                    chiPhi.setThang(thang);
                    dataChiPhi.child(chiPhi.getId()).setValue(chiPhi, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error!=null){
                                showAlertDialog(ChiPhiActivity.this, "Thông báo", "Lỗi thêm chi phí.");
                            }else{
                                showAlertDialog(ChiPhiActivity.this, "Thông báo", "Thêm chi phí thành công.");
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        dialog.show();
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

    public void docDLDichVu(){
        dataChiPhi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chiPhiList.clear();
                for (DataSnapshot item:snapshot.getChildren()) {
                    ChiPhi chiPhi = new ChiPhi();
                    chiPhi.setId(item.child("id").getValue().toString());
                    chiPhi.setTen(item.child("ten").getValue().toString());
                    chiPhi.setSoTien(Integer.parseInt(item.child("soTien").getValue().toString()));
                    chiPhi.setNoiDung(item.child("noiDung").getValue().toString());
                    chiPhi.setThang(item.child("thang").getValue().toString());
                    chiPhiList.add(chiPhi);
                }
                listChiPhiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void EditDichVu(ChiPhi chiPhi){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_chiphi);

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

        EditText edtTenChiPhi = dialog.findViewById(R.id.edtTenChiPhi);
        EditText edtGiaChiPhi = dialog.findViewById(R.id.edtGiaChiPhi);
        EditText edtNoiDung = dialog.findViewById(R.id.edtNoiDung);
        EditText edtThang = dialog.findViewById(R.id.edtThang);
        Button btnHuy = dialog.findViewById(R.id.buttonHuyDichVu);
        Button btnThem = dialog.findViewById(R.id.buttonThemDichVu);

        edtTenChiPhi.setText(chiPhi.getTen());
        edtGiaChiPhi.setText(String.valueOf(chiPhi.getSoTien()));
        edtNoiDung.setText(chiPhi.getNoiDung());
        edtThang.setText(chiPhi.getThang());

        edtThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonThang(edtThang);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiPhi = edtTenChiPhi.getText().toString().trim();
                String giaChiPhi = edtGiaChiPhi.getText().toString().trim();
                String noiDung = edtNoiDung.getText().toString().trim();
                String thang = edtThang.getText().toString().trim();
                if(tenChiPhi.equals("") || giaChiPhi.equals("") || noiDung.equals("") || thang.equals("")){
                    showAlertDialog(ChiPhiActivity.this, "Thông báo", "Vui lòng nhập đủ thông tin.");
                }else{
                    String chiPhiKey = chiPhi.getId();

                    // Cập nhật thông tin dichvu
                    Map<String, Object> updateChiPhi = new HashMap<>();
                    updateChiPhi.put("ten", tenChiPhi);
                    updateChiPhi.put("soTien", Integer.parseInt(giaChiPhi));
                    updateChiPhi.put("noiDung", noiDung);
                    updateChiPhi.put("thang", thang);

                    dataChiPhi.child(chiPhiKey).updateChildren(updateChiPhi, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error!=null){
                                showAlertDialog(ChiPhiActivity.this, "Thông báo", "Lỗi cập nhật chi phí.");
                            }else{
                                for (ChiPhi cp: chiPhiList) {
                                    if(cp.getId().equals(chiPhiKey)) {
                                        cp.setTen(tenChiPhi);
                                        cp.setSoTien(Integer.parseInt(giaChiPhi));
                                        cp.setNoiDung(noiDung);
                                        cp.setThang(thang);
                                    }
                                }
                                listChiPhiAdapter.notifyDataSetChanged();
                                showAlertDialog(ChiPhiActivity.this, "Thông báo", "Cập nhật chi phí thành công.");
                                dialog.dismiss();
                            }
                        }
                    });

                }
            }
        });

        dialog.show();
    }

    public void XoaDichVu(ChiPhi chiPhi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChiPhiActivity.this);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc muốn xóa chi phí " + chiPhi.getTen() + " ?")
                .setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String chiPhiKey = chiPhi.getId();
                                dataChiPhi.child(chiPhiKey).removeValue();

                                chiPhiList.remove(chiPhi);
                                listChiPhiAdapter.notifyDataSetChanged();
                                Toast.makeText(ChiPhiActivity.this, "Xóa chi phí thành công", Toast.LENGTH_SHORT).show();

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

    private void ChonThang(EditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ChiPhiActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}