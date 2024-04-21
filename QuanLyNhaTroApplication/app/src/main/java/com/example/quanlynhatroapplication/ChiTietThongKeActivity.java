package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.quanlynhatroapplication.Adapter.ChiTietThongKeAdapter;
import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.ChiPhi;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChiTietThongKeActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataNha;
    DatabaseReference dataPhong;
    DatabaseReference dataHoaDon;
    DatabaseReference dataChiPhi;
    List<House> houseList = new ArrayList<>();
    List<Room> roomList = new ArrayList<>();
    List<Bill> billList = new ArrayList<>();
    List<ChiPhi> chiPhiList = new ArrayList<>();
    String thangBatDau, thangKetThuc;
    RecyclerView rvChiTiet;
    ChiTietThongKeAdapter chiTietAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thong_ke);

        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");
        dataChiPhi = database.getReference("ChiPhi");

        Intent intent = getIntent();
        thangBatDau = intent.getStringExtra("thangBatDau");
        thangKetThuc = intent.getStringExtra("thangKetThuc");

        LayDuLieu();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setChiTietThongKeRecycler();
            }
        }, 2000);


    }

    @Override
    protected void onResume() {
        //LayDuLieu();
        //LayDuLieuChiPhi();
        super.onResume();
    }

    private void setChiTietThongKeRecycler(){
        rvChiTiet = findViewById(R.id.recyclerViewChiTietThongKe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvChiTiet.setLayoutManager(layoutManager);
        chiTietAdapter = new ChiTietThongKeAdapter(this, billList, houseList, roomList);
        rvChiTiet.setAdapter(chiTietAdapter);
    }

    private void LayDuLieu(){
        dataNha.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                House house = snapshot.getValue(House.class);

                dataPhong =  database.getReference("Nha/"+house.getId()+"/Phong");
                dataPhong.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Room room = snapshot.getValue(Room.class);

                        dataHoaDon = database.getReference("Nha/"+house.getId()+"/Phong/"+room.getId()+"/HoaDon");
                        dataHoaDon.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                Bill bill = snapshot.getValue(Bill.class);
                                if(bill.getThang().trim().compareTo(thangBatDau.trim()) >= 0 && bill.getThang().trim().compareTo(thangKetThuc.trim()) <= 0){
                                    billList.add(bill);
                                    houseList.add(house);
                                    roomList.add(room);
                                }
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
}