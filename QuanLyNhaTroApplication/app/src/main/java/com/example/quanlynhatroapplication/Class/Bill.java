package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;

public class Bill implements Serializable {
    private String id;
    private String thang;
    private String ngayThanhToan;
    private String hanThanhToan;
    private int tienPhong;
    private int tienDichVu;
    private String ghiChu;
    private int trangThai;

    public Bill() {
    }

    public Bill(String id, String thang, String ngayThanhToan, String hanThanhToan, int tienPhong, int tienDichVu, String ghiChu, int trangThai) {
        this.id = id;
        this.thang = thang;
        this.ngayThanhToan = ngayThanhToan;
        this.hanThanhToan = hanThanhToan;
        this.tienPhong = tienPhong;
        this.tienDichVu = tienDichVu;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getHanThanhToan() {
        return hanThanhToan;
    }

    public void setHanThanhToan(String hanThanhToan) {
        this.hanThanhToan = hanThanhToan;
    }

    public int getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(int tienPhong) {
        this.tienPhong = tienPhong;
    }

    public int getTienDichVu() {
        return tienDichVu;
    }

    public void setTienDichVu(int tienDichVu) {
        this.tienDichVu = tienDichVu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", thang='" + thang + '\'' +
                ", ngayThanhToan='" + ngayThanhToan + '\'' +
                ", hanThanhToan='" + hanThanhToan + '\'' +
                ", tienPhong=" + tienPhong +
                ", tienDichVu=" + tienDichVu +
                ", ghiChu='" + ghiChu + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
