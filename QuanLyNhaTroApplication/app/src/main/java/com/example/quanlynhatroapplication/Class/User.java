package com.example.quanlynhatroapplication.Class;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String hoTen;
    private String sdt;
    private String matKhau;

    public User() {
    }

    public User(String email, String hoTen, String sdt, String matKhau) {
        this.email = email;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.matKhau = matKhau;
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

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}
