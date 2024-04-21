package com.example.quanlynhatroapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlynhatroapplication.Adapter.HouseDetailAdaper;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Fragment.HouseDetailFragment;
import com.example.quanlynhatroapplication.Fragment.RoomListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class HouseDetailActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView txtTenNha;
    ImageView btnEditHouse;
    FirebaseDatabase database;
    DatabaseReference dataNha;
    House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        database = FirebaseDatabase.getInstance();
        dataNha = database.getReference("Nha");

        AnhXa();

        Intent intent = getIntent();
        house = (House) intent.getSerializableExtra("house");
        txtTenNha.setText(house.getTenNha());

        tabLayout.setupWithViewPager(viewPager);
        HouseDetailAdaper adaper = new HouseDetailAdaper(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


        Bundle bundle = new Bundle();
        bundle.putSerializable("house", (Serializable) house);

        RoomListFragment roomListFragment = new RoomListFragment();
        roomListFragment.setArguments(bundle);

        adaper.addFragment(roomListFragment, "DANH SÁCH PHÒNG");

        HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
        houseDetailFragment.setArguments(bundle);

        adaper.addFragment(houseDetailFragment, "CHI TIẾT NHÀ");

        viewPager.setAdapter(adaper);

        btnEditHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HouseDetailActivity.this, EditHouseActivity.class);
                intent1.putExtra("house", house);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LayDuLieuNha();
    }

    private void LayDuLieuNha() {
        dataNha.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                House newHouse = snapshot.getValue(House.class);
                if(house.getId().equals(newHouse.getId())){
                    house.setLinkHinh(newHouse.getLinkHinh());
                    house.setTenNha(newHouse.getTenNha());
                    house.setSoTang(newHouse.getSoTang());
                    house.setMoTa(newHouse.getMoTa());
                    house.setDiaChi(newHouse.getDiaChi());
                    house.setTinhThanh(newHouse.getTinhThanh());
                    house.setQuanHuyen(newHouse.getQuanHuyen());
                    house.setGioMoCua(newHouse.getGioMoCua());
                    house.setGioDongCua(newHouse.getGioDongCua());
                    house.setSoNgayChuyenDi(newHouse.getSoNgayChuyenDi());
                    house.setGhiChu(newHouse.getGhiChu());
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


    private void AnhXa() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        txtTenNha = findViewById(R.id.textViewTenNha);
        btnEditHouse = findViewById(R.id.btnThem);
    }

}