package com.example.quanlynhatroapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlynhatroapplication.Adapter.ServiceAdapter2;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.R;

import java.util.ArrayList;
import java.util.List;


public class RoomDetailFragment extends Fragment {
    Room room;
    RecyclerView rvDichVu;
    List<Service> dichVuList;
    ServiceAdapter2 serviceAdapter2;
    ImageView imgHinh;
    TextView txtTenPhong, txtGiaPhong, txtDienTich, txtTangSo, txtSoPhongNgu, txtSoPhongKhach, txtNguoiThueToiDa, txtTienCoc, txtMoTa, txtLuuY;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_room_detail, container, false);

        AnhXa(view);

        rvDichVu.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        Bundle bundle = getArguments();
        if (bundle != null) {
            room = (Room) bundle.getSerializable("room");
        }

        dichVuList = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dichVuList = room.getDichVuPhong();
        serviceAdapter2 = new ServiceAdapter2(view.getContext(), dichVuList);
        rvDichVu.setAdapter(serviceAdapter2);
        Glide.with(view.getContext()).load(room.getLinkHinh()).into(imgHinh);
        txtTenPhong.setText(room.getTenPhong());
        txtGiaPhong.setText(room.getGiaPhong() +"/tháng");
        txtDienTich.setText(room.getDienTich()+" m2");
        txtTangSo.setText(String.valueOf(room.getTangSo()));
        txtSoPhongNgu.setText(String.valueOf(room.getSoPhongNgu()));
        txtSoPhongKhach.setText(String.valueOf(room.getSoPhongKhach()));
        txtNguoiThueToiDa.setText(room.getGioiHanNguoiThue()+" người");
        txtTienCoc.setText(room.getTienCoc()+"đ");
        txtMoTa.setText(room.getMoTa());
        txtLuuY.setText(room.getLuuY());
    }

    public void AnhXa(View view){
        rvDichVu = view.findViewById(R.id.recyclerViewDichVu);
        imgHinh = view.findViewById(R.id.imageViewHinh);
        txtTenPhong = view.findViewById(R.id.textViewTenPhong);
        txtGiaPhong = view.findViewById(R.id.textViewGiaPhong);
        txtDienTich = view.findViewById(R.id.textViewDienTich);
        txtTangSo = view.findViewById(R.id.textViewTangSo);
        txtSoPhongNgu = view.findViewById(R.id.textViewSoPhongNgu);
        txtSoPhongKhach = view.findViewById(R.id.textViewSoPhongKhach);
        txtNguoiThueToiDa = view.findViewById(R.id.textViewSoNguoiThueToiDa);
        txtTienCoc = view.findViewById(R.id.textViewTienCoc);
        txtMoTa = view.findViewById(R.id.textViewMota);
        txtLuuY = view.findViewById(R.id.textViewLuuY);
    }

}