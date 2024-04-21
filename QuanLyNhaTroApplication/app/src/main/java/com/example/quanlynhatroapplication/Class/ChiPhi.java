package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;

public class ChiPhi implements Serializable {
    private String id;
    private String ten;
    private String noiDung;
    private int soTien;
    private String thang;

    public ChiPhi() {
    }

    public ChiPhi(String id, String ten, String noiDung, int soTien, String thang) {
        this.id = id;
        this.ten = ten;
        this.noiDung = noiDung;
        this.soTien = soTien;
        this.thang = thang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }
}
