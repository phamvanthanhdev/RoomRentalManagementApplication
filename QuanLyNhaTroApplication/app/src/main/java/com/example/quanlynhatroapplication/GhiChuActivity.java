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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.GhiChuAdapter;
import com.example.quanlynhatroapplication.Adapter.ListServiceAdapter;
import com.example.quanlynhatroapplication.Class.GhiChu;
import com.example.quanlynhatroapplication.Class.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GhiChuActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataGhiChu;
    ImageView btnBack;
    RecyclerView rvGhiChu;
    GhiChuAdapter ghiChuAdapter;
    List<GhiChu> ghiChuList = new ArrayList<>();
    ImageView btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghi_chu);

        database = FirebaseDatabase.getInstance();
        dataGhiChu = database.getReference("GhiChu");

        AnhXa();



        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvGhiChu.setLayoutManager(layoutManager);

        ghiChuAdapter = new GhiChuAdapter(this, ghiChuList);
        rvGhiChu.setAdapter(ghiChuAdapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogThemGhiChu();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        docDLGhiChu();
    }

    private void LayDuLieuGhiChu() {
        ghiChuList.clear();
        dataGhiChu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GhiChu ghiChu = snapshot.getValue(GhiChu.class);
                ghiChuList.add(ghiChu);
                ghiChuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void docDLGhiChu(){
        dataGhiChu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ghiChuList.clear();
                for (DataSnapshot item:snapshot.getChildren()) {
                    GhiChu ghiChu = new GhiChu();
                    ghiChu.setId(item.child("id").getValue().toString());
                    ghiChu.setThoiGian(item.child("thoiGian").getValue().toString());
                    ghiChu.setMauSac(item.child("mauSac").getValue().toString());
                    ghiChu.setTieuDe(item.child("tieuDe").getValue().toString());
                    ghiChu.setNoiDung(item.child("noiDung").getValue().toString());
                    ghiChuList.add(ghiChu);
                }
                ghiChuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void AnhXa() {
        btnBack = findViewById(R.id.btnBack);
        rvGhiChu = findViewById(R.id.recyclerViewGhiChu);
        btnThem = findViewById(R.id.btnThem);
    }

    public void openDiaLogThemGhiChu(){
        Log.d("AAA", ghiChuList.get(0).toString());
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_ghichu);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        dialog.setCancelable(true);

        final String[] mauDuocChon = {""};

        EditText edtTieuDe = dialog.findViewById(R.id.edtTieuDe);
        EditText edtNoiDung = dialog.findViewById(R.id.edtNoiDung);

        TextView txtLavender = dialog.findViewById(R.id.txtLavender);
        TextView txtVang = dialog.findViewById(R.id.txtVang);
        TextView txtXanhLa = dialog.findViewById(R.id.txtXanhLa);
        TextView txtXanhDuong = dialog.findViewById(R.id.txtXanhDuong);
        TextView txtDo = dialog.findViewById(R.id.txtDo);

        Button btnHuy = dialog.findViewById(R.id.buttonHuy);
        Button btnLuu = dialog.findViewById(R.id.buttonLuu);

        txtLavender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetMau(txtLavender, txtVang, txtXanhLa,txtXanhDuong, txtDo);
                txtLavender.setBackgroundResource(R.color.blue_grey);
                mauDuocChon[0] = "Lavender";
            }
        });

        txtVang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetMau(txtLavender, txtVang, txtXanhLa,txtXanhDuong, txtDo);
                txtVang.setBackgroundResource(R.color.blue_grey);
                mauDuocChon[0] = "Vàng";
            }
        });

        txtXanhLa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetMau(txtLavender, txtVang, txtXanhLa,txtXanhDuong, txtDo);
                txtXanhLa.setBackgroundResource(R.color.blue_grey);
                mauDuocChon[0] = "Xanh Lá";
            }
        });

        txtXanhDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetMau(txtLavender, txtVang, txtXanhLa,txtXanhDuong, txtDo);
                txtXanhDuong.setBackgroundResource(R.color.blue_grey);
                mauDuocChon[0] = "Xanh Dương";
            }
        });

        txtDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetMau(txtLavender, txtVang, txtXanhLa,txtXanhDuong, txtDo);
                txtDo.setBackgroundResource(R.color.blue_grey);
                mauDuocChon[0] = "Đỏ";
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieuDe = edtTieuDe.getText().toString().trim();
                String noiDung = edtNoiDung.getText().toString().trim();
                String thoiGian = LayThoiGianHienTai();
                if(thoiGian.equals("")){
                    showAlertDialog(GhiChuActivity.this, "Thông báo", "Vui lòng chọn màu.");
                }else if(tieuDe.equals("") || noiDung.equals("")){
                    showAlertDialog(GhiChuActivity.this, "Thông báo", "Vui lòng nhập đủ thông tin.");
                }else{
                    GhiChu ghiChu = new GhiChu();
                    ghiChu.setNoiDung(noiDung);
                    ghiChu.setTieuDe(tieuDe);
                    ghiChu.setMauSac(mauDuocChon[0]);
                    ghiChu.setThoiGian(thoiGian);
                    ghiChu.setId(dataGhiChu.push().getKey());
                    dataGhiChu.child(ghiChu.getId()).setValue(ghiChu, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error==null){
                                Toast.makeText(GhiChuActivity.this, "Thêm ghi chú thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else{
                                showAlertDialog(GhiChuActivity.this, "Thông báo", "Lỗi thêm ghi chú.");
                            }
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    public void openDialogSuaGhiChu(){

    }

    public void openDiaLogXoaGhiChu(){

    }

    private void ResetMau(TextView txtLavender, TextView txtVang, TextView txtXanhLa, TextView txtXanhDuong, TextView txtDo){
        txtLavender.setBackgroundResource(R.color.lavender);
        txtVang.setBackgroundResource(R.color.vang);
        txtXanhLa.setBackgroundResource(R.color.xanhla);
        txtXanhDuong.setBackgroundResource(R.color.xanhduong);
        txtDo.setBackgroundResource(R.color.maudo);
    }

    private String LayThoiGianHienTai() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        int giay = calendar.get(Calendar.SECOND);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        calendar.set(nam, thang + 1, ngay, gio, phut, giay);
        String dateTimeFormat = simpleDateFormat.format(calendar.getTime());

        return dateTimeFormat;
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