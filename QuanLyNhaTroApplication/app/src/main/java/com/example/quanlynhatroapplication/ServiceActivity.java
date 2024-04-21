package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ListServiceAdapter;
import com.example.quanlynhatroapplication.Class.Service;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataDV;
    RecyclerView rvDichVu;
    List<Service> dichVuList;
    ListServiceAdapter listServiceAdapter;
    ImageView btnThemDV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        database = FirebaseDatabase.getInstance();
        dataDV = database.getReference("DichVu");

        rvDichVu = findViewById(R.id.recyclerViewDichVu);
        btnThemDV = findViewById(R.id.btnThemDV);
        dichVuList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvDichVu.setLayoutManager(layoutManager);

        listServiceAdapter = new ListServiceAdapter(this, dichVuList);
        rvDichVu.setAdapter(listServiceAdapter);

        btnThemDV.setOnClickListener(new View.OnClickListener() {
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
        dialog.setContentView(R.layout.dialog_add_service);

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

        EditText edtTenDichVu = dialog.findViewById(R.id.edtTenDichVu);
        EditText edtGiaDichVu = dialog.findViewById(R.id.edtGiaDichVu);
        EditText edtDonViTinh = dialog.findViewById(R.id.edtDonViTinh);
        Button btnHuy = dialog.findViewById(R.id.buttonHuyDichVu);
        Button btnThem = dialog.findViewById(R.id.buttonThemDichVu);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDV = edtTenDichVu.getText().toString().trim();
                String giaDV = edtGiaDichVu.getText().toString().trim();
                String donViDV = edtDonViTinh.getText().toString().trim();
                if(tenDV.equals("") || giaDV.equals("") || donViDV.equals("")){
                    showAlertDialog(ServiceActivity.this, "Thông báo", "Vui lòng nhập đủ thông tin.");
                }else{
                    Service dichVu = new Service();
                    dichVu.setId(dataDV.push().getKey());
                    dichVu.setTenDichVu(tenDV);
                    dichVu.setGia(Integer.parseInt(giaDV));
                    dichVu.setDonVi(donViDV);
                    dichVu.setLinkHinh("https://firebasestorage.googleapis.com/v0/b/demo2024-9bfa7.appspot.com/o/service-icon-0.png?alt=media&token=c254db8a-ac89-40aa-921e-544755e9652c");
                    dataDV.child(dichVu.getId()).setValue(dichVu, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error!=null){
                                showAlertDialog(ServiceActivity.this, "Thông báo", "Lỗi thêm dịch vụ.");
                            }else{
                                showAlertDialog(ServiceActivity.this, "Thông báo", "Thêm dịch vụ thành công.");
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
                listServiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void EditDichVu(Service dichVu){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_service);

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

        EditText edtTenDichVu = dialog.findViewById(R.id.edtTenDichVu);
        EditText edtGiaDichVu = dialog.findViewById(R.id.edtGiaDichVu);
        EditText edtDonViTinh = dialog.findViewById(R.id.edtDonViTinh);
        Button btnHuy = dialog.findViewById(R.id.buttonHuyDichVu);
        Button btnThem = dialog.findViewById(R.id.buttonThemDichVu);

        edtTenDichVu.setText(dichVu.getTenDichVu());
        edtGiaDichVu.setText(String.valueOf(dichVu.getGia()));
        edtDonViTinh.setText(dichVu.getDonVi());

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDV = edtTenDichVu.getText().toString().trim();
                String giaDV = edtGiaDichVu.getText().toString().trim();
                String donViDV = edtDonViTinh.getText().toString().trim();
                if(tenDV.equals("") || giaDV.equals("") || donViDV.equals("")){
                    showAlertDialog(ServiceActivity.this, "Thông báo", "Vui lòng nhập đủ thông tin.");
                }else{
                    String dichVuKey = dichVu.getId();

                    // Cập nhật thông tin dichvu
                    Map<String, Object> updateDichVu = new HashMap<>();
                    updateDichVu.put("tenDichVu", tenDV);
                    updateDichVu.put("gia", Integer.parseInt(giaDV));
                    updateDichVu.put("donVi", donViDV);


                    dataDV.child(dichVuKey).updateChildren(updateDichVu, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error!=null){
                                showAlertDialog(ServiceActivity.this, "Thông báo", "Lỗi cập nhật dịch vụ.");
                            }else{
                                for (Service dv: dichVuList) {
                                    if(dv.getId().equals(dichVuKey)) {
                                        dv.setTenDichVu(tenDV);
                                        dv.setGia(Integer.parseInt(giaDV));
                                        dv.setDonVi(donViDV);
                                    }
                                }
                                listServiceAdapter.notifyDataSetChanged();
                                showAlertDialog(ServiceActivity.this, "Thông báo", "Cập nhật dịch vụ thành công.");
                                dialog.dismiss();
                            }
                        }
                    });

                }
            }
        });

        dialog.show();
    }

    public void XoaDichVu(Service service) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceActivity.this);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc muốn xóa dịch vụ " + service.getTenDichVu() + " ?")
                .setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dichVuKey = service.getId();
                        dataDV.child(dichVuKey).removeValue();

                        dichVuList.remove(service);
                        listServiceAdapter.notifyDataSetChanged();
                        Toast.makeText(ServiceActivity.this, "Xóa dịch vụ thành công", Toast.LENGTH_SHORT).show();

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
}