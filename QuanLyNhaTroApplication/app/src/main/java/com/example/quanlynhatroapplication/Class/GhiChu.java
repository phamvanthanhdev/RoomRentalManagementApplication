package com.example.quanlynhatroapplication.Class;

public class GhiChu {
    private String id;
    private String tieuDe;
    private String mauSac;
    private String noiDung;
    private String thoiGian;

    public GhiChu() {
    }

    public GhiChu(String id, String tieuDe, String mauSac, String noiDung, String thoiGian) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.mauSac = mauSac;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    @Override
    public String toString() {
        return "GhiChu{" +
                "id='" + id + '\'' +
                ", tieuDe='" + tieuDe + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                '}';
    }
}
