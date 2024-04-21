package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlynhatroapplication.Adapter.ListHouseAdapter;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListHouseActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataNha;
    ImageView btnThemNha;
    RecyclerView rvHouse;
    List<House> nhaList;
    ListHouseAdapter houseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_house);

        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");
        nhaList = new ArrayList<>();

        AnhXa();
        //LayDuLieuNha();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvHouse.setLayoutManager(layoutManager);

        houseAdapter = new ListHouseAdapter(this, nhaList);
        rvHouse.setAdapter(houseAdapter);

        btnThemNha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListHouseActivity.this, AddHouseActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        LayDuLieuNha();
        super.onResume();
    }

    private void LayDuLieuNha() {

        dataNha.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhaList.clear();
                for(DataSnapshot item:snapshot.getChildren()) {
                    House house = new House();
                    house.setId(item.child("id").getValue().toString());
                    house.setLinkHinh(item.child("linkHinh").getValue().toString());
                    house.setTenNha(item.child("tenNha").getValue().toString());
                    house.setSoTang(Integer.parseInt(item.child("soTang").getValue().toString()));
                    house.setMoTa(item.child("moTa").getValue().toString());
                    house.setDiaChi(item.child("diaChi").getValue().toString());
                    house.setTinhThanh(item.child("tinhThanh").getValue().toString());
                    house.setQuanHuyen(item.child("quanHuyen").getValue().toString());
                    house.setGioMoCua(item.child("gioMoCua").getValue().toString());
                    house.setGioDongCua(item.child("gioDongCua").getValue().toString());
                    house.setSoNgayChuyenDi(Integer.parseInt(item.child("soNgayChuyenDi").getValue().toString()));
                    house.setGhiChu(item.child("ghiChu").getValue().toString());

                    nhaList.add(house);
                }
                houseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void AnhXa() {
        btnThemNha = findViewById(R.id.btnThemNha);
        rvHouse= findViewById(R.id.rvHouse);
    }
}