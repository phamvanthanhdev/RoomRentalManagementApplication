package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;
import java.util.List;

public class HopDong implements Serializable {
    private String id;
    private String hoTen;
    private int camKetNguoiThue;
    private String idNha;
    private String tenNha;
    private String idPhong;
    private String tenPhong;
    private String tienPhong;
    private String tienCoc;
    private String ngayBatDauTinhTien;
    private String ngayKyHopDong;
    private String kyHanThanhToan;
    private List<Service> dichVu;

    public HopDong() {
    }

    public HopDong(String id, String hoTen, int camKetNguoiThue, String idNha, String tenNha,
                   String idPhong, String tenPhong, String tienPhong, String tienCoc,
                   String ngayBatDauTinhTien, String ngayKyHopDong, String kyHanThanhToan, List<Service> dichVu) {
        this.id = id;
        this.hoTen = hoTen;
        this.camKetNguoiThue = camKetNguoiThue;
        this.idNha = idNha;
        this.tenNha = tenNha;
        this.idPhong = idPhong;
        this.tenPhong = tenPhong;
        this.tienPhong = tienPhong;
        this.tienCoc = tienCoc;
        this.ngayBatDauTinhTien = ngayBatDauTinhTien;
        this.ngayKyHopDong = ngayKyHopDong;
        this.kyHanThanhToan = kyHanThanhToan;
        this.dichVu = dichVu;
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

    public int getCamKetNguoiThue() {
        return camKetNguoiThue;
    }

    public void setCamKetNguoiThue(int camKetNguoiThue) {
        this.camKetNguoiThue = camKetNguoiThue;
    }

    public String getIdNha() {
        return idNha;
    }

    public void setIdNha(String idNha) {
        this.idNha = idNha;
    }

    public String getTenNha() {
        return tenNha;
    }

    public void setTenNha(String tenNha) {
        this.tenNha = tenNha;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(String tienPhong) {
        this.tienPhong = tienPhong;
    }

    public String getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(String tienCoc) {
        this.tienCoc = tienCoc;
    }

    public String getNgayBatDauTinhTien() {
        return ngayBatDauTinhTien;
    }

    public void setNgayBatDauTinhTien(String ngayBatDauTinhTien) {
        this.ngayBatDauTinhTien = ngayBatDauTinhTien;
    }

    public String getNgayKyHopDong() {
        return ngayKyHopDong;
    }

    public void setNgayKyHopDong(String ngayKyHopDong) {
        this.ngayKyHopDong = ngayKyHopDong;
    }

    public String getKyHanThanhToan() {
        return kyHanThanhToan;
    }

    public void setKyHanThanhToan(String kyHanThanhToan) {
        this.kyHanThanhToan = kyHanThanhToan;
    }

    public List<Service> getDichVu() {
        return dichVu;
    }

    public void setDichVu(List<Service> dichVu) {
        this.dichVu = dichVu;
    }
}
