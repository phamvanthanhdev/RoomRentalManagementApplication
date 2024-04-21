package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;

public class NguoiThue implements Serializable {
    private String id;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String ngaySinh;
    private String noiSinh;
    private String canCuocCongDan;
    private String ngayCap;
    private String noiCap;

    public NguoiThue() {
    }

    public NguoiThue(String id, String hoTen, String soDienThoai, String email, String ngaySinh, String noiSinh, String canCuocCongDan, String ngayCap, String noiCap) {
        this.id = id;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.noiSinh = noiSinh;
        this.canCuocCongDan = canCuocCongDan;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getCanCuocCongDan() {
        return canCuocCongDan;
    }

    public void setCanCuocCongDan(String canCuocCongDan) {
        this.canCuocCongDan = canCuocCongDan;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }
}
