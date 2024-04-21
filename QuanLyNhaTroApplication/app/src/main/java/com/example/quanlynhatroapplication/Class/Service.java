package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;

public class Service implements Serializable {
    private String id;
    private String tenDichVu;
    private int gia;
    private String donVi;
    private String linkHinh;

    public Service() {
    }

    public Service(String id,String tenDichVu, int gia, String donVi, String linkHinh) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.gia = gia;
        this.donVi = donVi;
        this.linkHinh = linkHinh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }
}
