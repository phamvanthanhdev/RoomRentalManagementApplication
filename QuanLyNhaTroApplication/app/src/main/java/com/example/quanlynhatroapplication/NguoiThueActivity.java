package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.quanlynhatroapplication.Adapter.NguoiThueAdapter;
import com.example.quanlynhatroapplication.Adapter.NguoiThueFragmentAdapter;
import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.NguoiThue;
import com.example.quanlynhatroapplication.Class.Room;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NguoiThueActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataNguoiThue;
    DatabaseReference dataNha;
    DatabaseReference dataPhong;
    List<String> tenNhaList = new ArrayList<>();
    List<String> tenPhongList = new ArrayList<>();
    List<NguoiThue> nguoiThueList = new ArrayList<>();
    RecyclerView rvNguoiThue;
    NguoiThueAdapter nguoiThueAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_thue);

        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");

        rvNguoiThue = findViewById(R.id.recyclerViewNguoiThue);

        database = FirebaseDatabase.getInstance();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvNguoiThue.setLayoutManager(layoutManager);

        nguoiThueAdapter = new NguoiThueAdapter(this, nguoiThueList, tenNhaList, tenPhongList);
        rvNguoiThue.setAdapter(nguoiThueAdapter);
    }

    @Override
    protected void onResume() {
        LayDuLieu();
        super.onResume();
    }

    private void LayDuLieu(){
        nguoiThueList.clear();
        dataNha.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                House house = snapshot.getValue(House.class);
                dataPhong =  database.getReference("Nha/"+house.getId()+"/Phong");
                dataPhong.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Room room = snapshot.getValue(Room.class);

                        dataNguoiThue = database.getReference("Nha/"+house.getId()+"/Phong/"+room.getId()+"/NguoiThue");
                        dataNguoiThue.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                NguoiThue nguoiThue = snapshot.getValue(NguoiThue.class);
                                nguoiThueList.add(nguoiThue);
                                tenNhaList.add(house.getTenNha());
                                tenPhongList.add(room.getTenPhong());
                                nguoiThueAdapter.notifyDataSetChanged();
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
}