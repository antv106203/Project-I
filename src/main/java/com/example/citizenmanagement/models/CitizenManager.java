package com.example.citizenmanagement.models;

public class CitizenManager {
    private String hoTen;
    private String tenDangNhap;
    private String matKhau;
    private String soDienThoai;


    public CitizenManager() {
        this.hoTen = "";
        this.tenDangNhap = "";
        this.soDienThoai = "";
        this.matKhau = "";

    }

    public void setHoTen(String hoTen) {this.hoTen = hoTen;}

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHoTen() {return hoTen;}

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getSoDienThoai_hide() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            tmp.append('*');
        }
        tmp.append(this.soDienThoai.substring(7));

        return tmp.toString();
    }

}
