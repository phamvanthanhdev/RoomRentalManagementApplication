package com.example.quanlynhatroapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.quanlynhatroapplication.Adapter.ListRoomAdapter;
import com.example.quanlynhatroapplication.Adapter.ListServiceAdapter;
import com.example.quanlynhatroapplication.AddRoomActivity;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.R;
import com.example.quanlynhatroapplication.RoomDetailActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RoomListFragment extends Fragment {
    DatabaseReference mData;
    RecyclerView rvRoom;
    ListRoomAdapter roomAdapter;
    List<Room> roomList;
    List<Room> roomAllList;
    SearchView svRoom;
    ImageView btnThemPhong;
    House house;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_room__list_, container, false);

        mData = FirebaseDatabase.getInstance().getReference();

        rvRoom = view.findViewById(R.id.rvRoom);
        svRoom = view.findViewById(R.id.searchViewRoom);
        btnThemPhong = view.findViewById(R.id.imageViewThemPhong);
        roomList = new ArrayList<>();
        roomAllList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            house = (House) bundle.getSerializable("house");
        }

        //layDuLieuPhong();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 1);
        rvRoom.setLayoutManager(layoutManager);

        roomAdapter = new ListRoomAdapter(view.getContext(), roomList, house.getId());
        rvRoom.setAdapter(roomAdapter);


        setEvent();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        layDuLieuPhong();
    }

    public void layDuLieuPhong(){
        roomList.clear();
        roomAllList.clear();
        mData.child("Nha").child(house.getId()).child("Phong")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Room room = snapshot.getValue(Room.class);
                        roomList.add(room);
                        roomAllList.add(room);
                        roomAdapter.notifyDataSetChanged();
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

    public void setEvent(){
        btnThemPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddRoomActivity.class);
                intent.putExtra("house", house);
                startActivity(intent);
            }
        });

        svRoom.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                roomList.clear();
                if(newText.equals("")){
                    roomList.addAll(roomAllList);
                    roomAdapter.notifyDataSetChanged();
                }else{
                    for (Room room: roomAllList) {
                        if(room.getTenPhong().contains(newText)){
                            roomList.add(room);
                        }
                    }
                }
                roomAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}