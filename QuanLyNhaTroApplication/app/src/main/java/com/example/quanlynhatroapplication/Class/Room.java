package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {
    private String id;
    private String linkHinh;
    private String tenPhong;
    private int giaPhong;
    private int tangSo;
    private int soPhongNgu;
    private int soPhongKhach;
    private double dienTich;
    private int gioiHanNguoiThue;
    private int tienCoc;
    private List<String> gioiTinh;
    private List<Service> dichVuPhong;
    private String moTa;
    private String luuY;
    //private List<Bill> hoaDon;


    public Room() {
    }

    public Room(String id, String linkHinh, String tenPhong, int giaPhong, int tangSo, int soPhongNgu,
                int soPhongKhach, double dienTich, int gioiHanNguoiThue, int tienCoc,
                List<String> gioiTinh, List<Service> dichVuPhong, String moTa, String luuY) {
        this.id = id;
        this.linkHinh = linkHinh;
        this.tenPhong = tenPhong;
        this.giaPhong = giaPhong;
        this.tangSo = tangSo;
        this.soPhongNgu = soPhongNgu;
        this.soPhongKhach = soPhongKhach;
        this.dienTich = dienTich;
        this.gioiHanNguoiThue = gioiHanNguoiThue;
        this.tienCoc = tienCoc;
        this.gioiTinh = gioiTinh;
        this.dichVuPhong = dichVuPhong;
        this.moTa = moTa;
        this.luuY = luuY;
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

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public int getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(int giaPhong) {
        this.giaPhong = giaPhong;
    }

    public int getTangSo() {
        return tangSo;
    }

    public void setTangSo(int tangSo) {
        this.tangSo = tangSo;
    }

    public int getSoPhongNgu() {
        return soPhongNgu;
    }

    public void setSoPhongNgu(int soPhongNgu) {
        this.soPhongNgu = soPhongNgu;
    }

    public int getSoPhongKhach() {
        return soPhongKhach;
    }

    public void setSoPhongKhach(int soPhongKhach) {
        this.soPhongKhach = soPhongKhach;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public int getGioiHanNguoiThue() {
        return gioiHanNguoiThue;
    }

    public void setGioiHanNguoiThue(int gioiHanNguoiThue) {
        this.gioiHanNguoiThue = gioiHanNguoiThue;
    }

    public int getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(int tienCoc) {
        this.tienCoc = tienCoc;
    }

    public List<String> getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(List<String> gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public List<Service> getDichVuPhong() {
        return dichVuPhong;
    }

    public void setDichVuPhong(List<Service> dichVuPhong) {
        this.dichVuPhong = dichVuPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLuuY() {
        return luuY;
    }

    public void setLuuY(String luuY) {
        this.luuY = luuY;
    }
}
