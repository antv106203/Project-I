package com.example.citizenmanagement.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainTamTruCell {
    private final StringProperty maGiayTamTru;
    private final StringProperty hoVaTen;
    private final StringProperty maNhanKhau;

    private final StringProperty noiThuongTru;
    private final StringProperty tuNgay;
    private final StringProperty denNgay;

    private  final StringProperty soCCCD ;
    private final StringProperty ngaySinh;
    private final StringProperty lyDo;
    public MainTamTruCell(String hovaten,String magiaytamtru,String manhankhau,String noithuongtru,String tuNgay, String denngay,String socccd, String ngaysinh,String lydo){
        this.hoVaTen = new SimpleStringProperty(this,"hoVaTen",hovaten);
        this.maGiayTamTru = new SimpleStringProperty(this,"maGiayTamTru",magiaytamtru);
        this.maNhanKhau = new SimpleStringProperty(this,"maNhanKhau",manhankhau);
        this.noiThuongTru = new SimpleStringProperty(this,"noiThuongTru",noithuongtru);
        this.tuNgay = new SimpleStringProperty(this,"tuNgay",tuNgay);
        this.denNgay = new SimpleStringProperty(this,"denNgay",denngay);
        this.soCCCD = new SimpleStringProperty(this,"soCCCD", socccd);
        this.ngaySinh = new SimpleStringProperty(this,"ngaySinh",ngaysinh);
        this.lyDo = new SimpleStringProperty(this,"lyDo",lydo);
    }

    public StringProperty getHoVaTen(){
        return this.hoVaTen;
    }

    public StringProperty getMaGiayTamTru() {
        return this.maGiayTamTru;
    }

    public StringProperty getMaNhanKhau() {
        return this.maNhanKhau;
    }

    public StringProperty getNoiThuongTru() {
        return this.noiThuongTru;
    }

    public StringProperty getTuNgay() {
        return this.tuNgay;
    }

    public StringProperty getDenNgay() {
        return this.denNgay;
    }

    public StringProperty getSoCCCD(){
        return this.soCCCD;
    }
    public StringProperty getLyDo(){
        return  this.lyDo;
    }
    public StringProperty getNgaySinh(){
        return this.ngaySinh;
    }
}
