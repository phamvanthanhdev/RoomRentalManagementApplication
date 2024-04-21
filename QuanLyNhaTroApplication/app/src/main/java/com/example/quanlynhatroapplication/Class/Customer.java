package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;

public class Customer implements Serializable {
    private String id;
    private String email;
    private String hoTen;
    private String sdt;
    private String matKhau;
    private int trangThaiThue;

    public Customer() {
    }

    public Customer(String id, String email, String hoTen, String sdt, String matKhau, int trangThaiThue) {
        this.id = id;
        this.email = email;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.matKhau = matKhau;
        this.trangThaiThue = trangThaiThue;
    }

    public int getTrangThaiThue() {
        return trangThaiThue;
    }

    public void setTrangThaiThue(int trangThaiThue) {
        this.trangThaiThue = trangThaiThue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
