package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;
import java.util.List;

public class House implements Serializable {
    private String id;
    private String linkHinh;
    private String tenNha;
    private int soTang;
    private String moTa;
    private String diaChi;
    private String tinhThanh;
    private String quanHuyen;
    private String gioMoCua;
    private String gioDongCua;
    private int soNgayChuyenDi;
    private String ghiChu;
    List<Room> danhSachPhong;

    public House() {
    }

    public House(String id, String linkHinh, String tenNha, int soTang, String moTa, String diaChi, String tinhThanh, String quanHuyen,
                 String gioMoCua, String gioDongCua, int soNgayChuyenDi, String ghiChu, List<Room> danhSachPhong) {
        this.id = id;
        this.linkHinh = linkHinh;
        this.tenNha = tenNha;
        this.soTang = soTang;
        this.moTa = moTa;
        this.diaChi = diaChi;
        this.tinhThanh = tinhThanh;
        this.quanHuyen = quanHuyen;
        this.gioMoCua = gioMoCua;
        this.gioDongCua = gioDongCua;
        this.soNgayChuyenDi = soNgayChuyenDi;
        this.ghiChu = ghiChu;
        this.danhSachPhong = danhSachPhong;
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

    public String getTenNha() {
        return tenNha;
    }

    public void setTenNha(String tenNha) {
        this.tenNha = tenNha;
    }

    public int getSoTang() {
        return soTang;
    }

    public void setSoTang(int soTang) {
        this.soTang = soTang;
    }


    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getGioMoCua() {
        return gioMoCua;
    }

    public void setGioMoCua(String gioMoCua) {
        this.gioMoCua = gioMoCua;
    }

    public String getGioDongCua() {
        return gioDongCua;
    }

    public void setGioDongCua(String gioDongCua) {
        this.gioDongCua = gioDongCua;
    }

    public int getSoNgayChuyenDi() {
        return soNgayChuyenDi;
    }

    public void setSoNgayChuyenDi(int soNgayChuyenDi) {
        this.soNgayChuyenDi = soNgayChuyenDi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<Room> getDanhSachPhong() {
        return danhSachPhong;
    }

    public void setDanhSachPhong(List<Room> danhSachPhong) {
        this.danhSachPhong = danhSachPhong;
    }
}
