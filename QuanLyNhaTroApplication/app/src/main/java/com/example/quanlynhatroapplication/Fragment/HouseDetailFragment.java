package com.example.quanlynhatroapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.R;

public class HouseDetailFragment extends Fragment {
    House house;
    TextView txtTenNha, txtDiaChi, txtSoTang, txtSoPhong, txtGioMoCua, txtGioDongCua, txtSoNgayChuyenDi, txtMoTa, txtGhiChu;
    ImageView imgHinh;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_house_detail, container, false);

        txtTenNha = view.findViewById(R.id.textViewTenNha);
        txtDiaChi = view.findViewById(R.id.textViewDiaChi);
        txtSoTang = view.findViewById(R.id.textViewTangSo);
        txtSoPhong = view.findViewById(R.id.textViewDienTich);
        txtGioMoCua = view.findViewById(R.id.textViewGioMoCua);
        txtGioDongCua = view.findViewById(R.id.textViewGioDongCua);
        txtSoNgayChuyenDi = view.findViewById(R.id.textViewSoNgayChuyenDi);
        txtMoTa = view.findViewById(R.id.textViewMota);
        txtGhiChu = view.findViewById(R.id.textViewGhiChu);

        imgHinh = view.findViewById(R.id.imageViewHinh);

        //Nhận đối tượng
        Bundle bundle = getArguments();
        if (bundle != null) {
            house = (House) bundle.getSerializable("house");

        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(view).load(house.getLinkHinh()).into(imgHinh);
        txtTenNha.setText(house.getTenNha());
        txtDiaChi.setText(house.getDiaChi()+ ", " + house.getQuanHuyen() +", "+ house.getTinhThanh());
        txtSoTang.setText(String.valueOf(house.getSoTang()));
        txtGioMoCua.setText(house.getGioMoCua());
        txtGioDongCua.setText(house.getGioDongCua());
        txtSoNgayChuyenDi.setText(String.valueOf(house.getSoNgayChuyenDi()));
        txtMoTa.setText(house.getMoTa());
        txtGhiChu.setText(house.getGhiChu());
    }
}