package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.ChiPhi;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Class.Service;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ThongKeActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataNha;
    DatabaseReference dataPhong;
    DatabaseReference dataHoaDon;
    DatabaseReference dataSoLuongNguoiThue;
    DatabaseReference dataSoLuongYeuCau;
    DatabaseReference dataChiPhi;
    List<House> houseList = new ArrayList<>();
    List<Room> roomList = new ArrayList<>();
    List<Bill> billList = new ArrayList<>();
    List<ChiPhi> chiPhiList = new ArrayList<>();
    Map<String, Integer> soLuongNguoiThueMap = new LinkedHashMap<>();
    Map<String, Integer> soLuongYeuCauMap = new HashMap<>();
    int tienPhong = 0;
    int tienDichVu = 0;
    int soHDDaThanhToan = 0;
    int soHDConNo = 0;
    int tienChiPhi = 0;
    Button btnXemThongKe, btnXemBieuDo, btnBieuDoNguoiThue, btnBieuDoYeuCau, btnChiTiet;
    TextView txtTienPhong, txtTienDichVu, txtChiPhi, txtDoanhThu, txtSoHDDaThanhToan, txtSoHoaDonChuaThanhToan;
    ConstraintLayout layoutThongKeTien, layoutThongKeHoaDon;
    TextInputEditText edtThangBatDau, edtThangKetThuc;
    List<String> thangList = Arrays.asList("01-2024", "02-2024", "03-2024", "04-2024");
    List<String> thangTrongNam = Arrays.asList("01/2024", "02/2024", "03/2024", "04/2024", "05/2024", "06/2024", "07/2024", "08/2024", "09/2024", "10/2024", "11/2024", "12/2024");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");
        dataChiPhi = database.getReference("ChiPhi");
        dataSoLuongNguoiThue = database.getReference("SoLuongNguoiThue");
        dataSoLuongYeuCau = database.getReference("SoLuongYeuCau");


        AnhXa();
        setEvent();
    }

    private void setEvent() {
        edtThangBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonThang(edtThangBatDau);
            }
        });
        edtThangKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonThang(edtThangKetThuc);
            }
        });

        btnBieuDoNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBieuDoNguoiThue();
            }
        });
        btnBieuDoYeuCau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBieuDoYeuCauThue();
            }
        });
        btnXemThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thangBatDau = edtThangBatDau.getText().toString().trim();
                String thangKetThuc = edtThangKetThuc.getText().toString().trim();
                if(thangBatDau.equals("") || thangKetThuc.equals("")){
                    showAlertDialog(ThongKeActivity.this, "Thông báo", "Vui lòng cho thời gian thống kê.");
                }else if(thangKetThuc.compareTo(thangBatDau) < 0){
                    showAlertDialog(ThongKeActivity.this, "Thông báo","Vui lòng chọn thời gian hợp lệ");
                }
                else if(!thangTrongNam.contains(thangBatDau) || !thangTrongNam.contains(thangKetThuc)){
                    showAlertDialog(ThongKeActivity.this, "Thông báo","Vui lòng chọn thời gian trong năm");
                }
                else {
                    tienPhong = 0;
                    tienChiPhi = 0;
                    tienDichVu = 0;
                    soHDConNo = 0;
                    soHDDaThanhToan = 0;

                    for (Bill bill:billList) {
                        if(bill.getThang().compareTo(thangBatDau) >= 0 && bill.getThang().compareTo(thangKetThuc) <= 0){
                            tienPhong += bill.getTienPhong();
                            tienDichVu += bill.getTienDichVu();
                            if (bill.getTrangThai() == 0) soHDConNo += 1;
                            else soHDDaThanhToan += 1;
                        }
                    }

                    for (ChiPhi cp:chiPhiList) {
                        if(cp.getThang().compareTo(thangBatDau) >= 0 && cp.getThang().compareTo(thangKetThuc) <= 0){
                           tienChiPhi += cp.getSoTien();
                        }
                    }

                    openDialogBaoCao();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        LayDuLieu();
        LayDuLieuChiPhi();
        LayDuLieuSoLuongNguoiThue(thangList);
        LayDuLieuSoLuongYeuCau(thangList);
        super.onResume();
    }

    private void LayDuLieuChiPhi() {
        dataChiPhi.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChiPhi chiPhi = snapshot.getValue(ChiPhi.class);
                chiPhiList.add(chiPhi);
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

    private void openDialogBaoCao(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thongke);

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

        txtTienPhong = dialog.findViewById(R.id.textViewTienPhong);
        txtTienDichVu = dialog.findViewById(R.id.textViewTienDichVu);
        txtChiPhi = dialog.findViewById(R.id.textViewChiPhi);
        txtDoanhThu = dialog.findViewById(R.id.textViewDoanhThu);
        txtSoHDDaThanhToan = dialog.findViewById(R.id.textViewHDDaThanhToan);
        txtSoHoaDonChuaThanhToan = dialog.findViewById(R.id.textViewHDConNo);
        layoutThongKeHoaDon = dialog.findViewById(R.id.layoutThongKeHoaDon);
        layoutThongKeTien = dialog.findViewById(R.id.layoutThongKeTien);
        btnXemBieuDo = dialog.findViewById(R.id.buttonBieuDo);
        btnChiTiet = dialog.findViewById(R.id.buttonChiTiet);

        layoutThongKeHoaDon.setVisibility(View.VISIBLE);
        layoutThongKeTien.setVisibility(View.VISIBLE);
        txtTienPhong.setText("+ " + tienPhong + " đ");
        txtTienDichVu.setText("+ " + tienDichVu + " đ");
        txtChiPhi.setText("- " + tienChiPhi + " đ");
        txtDoanhThu.setText((tienPhong + tienDichVu - tienChiPhi) + " đ");
        txtSoHDDaThanhToan.setText(soHDDaThanhToan + " hóa đơn");
        txtSoHoaDonChuaThanhToan.setText(soHDConNo + " hóa đơn");

        btnXemBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBieuDo();
            }
        });

        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongKeActivity.this, ChiTietThongKeActivity.class);
                String thangBatDau = edtThangBatDau.getText().toString().trim();
                String thangKetThuc = edtThangKetThuc.getText().toString().trim();
                intent.putExtra("thangBatDau", thangBatDau);
                intent.putExtra("thangKetThuc", thangKetThuc);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void openDialogBieuDo(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bieudo);

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

        LineChart lineChart = dialog.findViewById(R.id.line_chart);
        List<String> xValue = new ArrayList<>();

        String thangBatDau = edtThangBatDau.getText().toString().trim();
        String thangKetThuc = edtThangKetThuc.getText().toString().trim();

        Description description = new Description();
        description.setText("Thống kê từ " + thangBatDau + " đến " + thangKetThuc);
        description.setTextSize(16f);

        description.setPosition(1100f, 60f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        //xValue = Arrays.asList("01/2024", "02/2024", "03/2024", "04/2024", "05/2024", "06/2024", "07/2024", "08/2024", "09/2024", "10/2024", "11/2024", "12/2024");
        //xValue = Arrays.asList("01/2024", "02/2024", "03/2024", "04/2024", "05/2024", "06/2024");

        if(!thangTrongNam.contains(thangBatDau) || !thangTrongNam.contains(thangKetThuc)){
            showAlertDialog(this, "Thông báo","Vui lòng chọn thời gian trong năm");
            return;
        }

        for (String thang: thangTrongNam) {
            if(thang.compareTo(thangBatDau.trim()) >= 0 && thang.compareTo(thangKetThuc.trim()) <= 0){
                xValue.add(thang);
            }
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelCount(12);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(10000000f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
        lineChart.animateY(2000);

        List<Entry> entriesTongThu = new ArrayList<>();
        List<Entry> entriesChiPhi = new ArrayList<>();
        List<Entry> entriesDoanhThu = new ArrayList<>();

        for (int i = 0; i < xValue.size(); i++) {
            String thang = xValue.get(i);
            int TongThu = 0;
            for (Bill bill: billList) {
                if(bill.getThang().equals(thang)){
                    TongThu = bill.getTienPhong() + bill.getTienDichVu();
                }
            }
            entriesTongThu.add(new Entry(i, TongThu));
            int ChiPhi = 0;
            for (ChiPhi cp: chiPhiList) {
                if(cp.getThang().equals(thang)){
                   ChiPhi = cp.getSoTien();
                }
            }
            entriesChiPhi.add(new Entry(i, ChiPhi));

            entriesDoanhThu.add(new Entry(i, (TongThu - ChiPhi)));
        }

        LineDataSet dataSet1 = new LineDataSet(entriesTongThu, "Tổng thu");
        dataSet1.setColor(Color.BLUE);
        dataSet1.setValueTextSize(12);

        LineDataSet dataSet2 = new LineDataSet(entriesChiPhi, "Chi phí");
        dataSet2.setColor(Color.RED);
        dataSet2.setValueTextSize(12);

        LineDataSet dataSet3 = new LineDataSet(entriesDoanhThu, "Doanh thu");
        dataSet3.setColor(Color.GREEN);
        dataSet3.setValueTextSize(12);

        LineData lineData = new LineData(dataSet1, dataSet2, dataSet3);

        lineChart.setData(lineData);

        lineChart.invalidate();


        dialog.show();
    }

    private void openDialogBieuDoNguoiThue(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bieudo_nguoithue);

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

        BarChart barChart = dialog.findViewById(R.id.barChar);
        barChart.getAxisRight().setDrawLabels(false);

        List<BarEntry> entries = new ArrayList<>();
        List<String> xValues = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String,Integer> entry : soLuongNguoiThueMap.entrySet()){
            xValues.add(entry.getKey());
            entries.add(new BarEntry(count, entry.getValue()));
            count++;
        }

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(20f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
        barChart.animateY(2000);


        BarDataSet dataSet = new BarDataSet(entries, "Tháng");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(16f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        //barChart.getDescription().setEnabled(false);
        Description description = new Description();
        description.setText("Thống kê gia tăng số lượng người thuê");
        description.setTextSize(16f);
        description.setPosition(1100f, 60f);
        barChart.setDescription(description);

        barChart.invalidate();

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);


        dialog.show();
    }

    private void openDialogBieuDoYeuCauThue(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bieudo_yeucauthue);

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

        PieChart pieChart = dialog.findViewById(R.id.pieChar);

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : soLuongYeuCauMap.entrySet()){
            int entryValueLength = entry.getValue().toString().length();
            entries.add(new PieEntry(Integer.parseInt(entry.getValue()+""), entry.getKey()));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "Tháng");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        //pieChart.getDescription().setEnabled(false);
        Description description = new Description();
        description.setText("Thống kê số lượng yêu cầu thuê phòng");
        description.setTextSize(16f);
        description.setPosition(1100f, 60f);
        pieChart.setDescription(description);

        pieChart.animateY(1000);
        pieChart.invalidate();


        dialog.show();
    }

    private void AnhXa() {
        //btnXemBieuDo = findViewById(R.id.buttonXemBieuDo);
        btnXemThongKe = findViewById(R.id.buttonXemThongKe);
        /*txtTienPhong = findViewById(R.id.textViewTienPhong);
        txtTienDichVu = findViewById(R.id.textViewTienDichVu);
        txtChiPhi = findViewById(R.id.textViewChiPhi);
        txtDoanhThu = findViewById(R.id.textViewDoanhThu);
        txtSoHDDaThanhToan = findViewById(R.id.textViewHDDaThanhToan);
        txtSoHoaDonChuaThanhToan = findViewById(R.id.textViewHDConNo);
        layoutThongKeHoaDon = findViewById(R.id.layoutThongKeHoaDon);
        layoutThongKeTien = findViewById(R.id.layoutThongKeTien);
        layoutThongKeHoaDon.setVisibility(View.INVISIBLE);
        layoutThongKeTien.setVisibility(View.INVISIBLE);*/
        edtThangBatDau = findViewById(R.id.edtNgayBatDau);
        edtThangKetThuc = findViewById(R.id.edtNgayKetThuc);

        btnBieuDoNguoiThue = findViewById(R.id.buttonBieuDoNguoiThue);
        btnBieuDoYeuCau= findViewById(R.id.buttonBieuDoYeuCau);
    }

    private void ChonThang(TextInputEditText edt){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ThongKeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
                edt.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void LayDuLieu(){
        dataNha.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                House house = snapshot.getValue(House.class);
                houseList.add(house);
                dataPhong =  database.getReference("Nha/"+house.getId()+"/Phong");
                dataPhong.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Room room = snapshot.getValue(Room.class);
                        roomList.add(room);
                        dataHoaDon = database.getReference("Nha/"+house.getId()+"/Phong/"+room.getId()+"/HoaDon");
                        dataHoaDon.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                Bill bill = snapshot.getValue(Bill.class);
                                billList.add(bill);
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

    private void LayDuLieuSoLuongNguoiThue(List<String> thangList){
        soLuongNguoiThueMap.clear();
        for (String thang: thangList) {
            dataSoLuongNguoiThue.child(thang).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int soLuong = 0;
                    if (snapshot.getValue() != null) {
                        soLuong = Integer.parseInt(snapshot.getValue().toString());
                    }
                    soLuongNguoiThueMap.put(thang, soLuong);
                    //Log.d("AAA", thang + ": " + soLuong);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void LayDuLieuSoLuongYeuCau(List<String> thangList){
        soLuongYeuCauMap.clear();
        for (String thang: thangList) {
            dataSoLuongYeuCau.child(thang).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int soLuong = 0;
                    if (snapshot.getValue() != null) {
                        soLuong = Integer.parseInt(snapshot.getValue().toString());
                    }
                    soLuongYeuCauMap.put(thang, soLuong);
                    //Log.d("AAA", thang + ": " + soLuong);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

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