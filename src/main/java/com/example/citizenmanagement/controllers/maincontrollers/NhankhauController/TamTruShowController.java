package com.example.citizenmanagement.controllers.maincontrollers.NhankhauController;

import com.example.citizenmanagement.models.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TamTruShowController implements Initializable {

    @FXML
    private TextField CCCD_fld;

    @FXML
    private Button chinh_sua_btn;

    @FXML
    private Button confirm_chinh_sua_btn;

    @FXML
    private TextField denngay_fld;

    @FXML
    private TextField hovaten_fld;

    @FXML
    private Button khai_tu_btn;

    @FXML
    private TextField lydo_fld;

    @FXML
    private TextField magiaytamtru_fld;

    @FXML
    private TextField manhankhau_fld;

    @FXML
    private TextField ngaysinh_fld;

    @FXML
    private TextField noithuongtru_fld;

    @FXML
    private Button thoat_chinhsua_button;

    @FXML
    private TextField tungay_fld;
    private MainTamTruCell luaChon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        luaChon = Model.getNhanKhauTamTruDuocChon();
        hovaten_fld.setText(String.valueOf(luaChon.getHoVaTen().get()));
        CCCD_fld.setText(String.valueOf(luaChon.getSoCCCD().get()));
        magiaytamtru_fld.setText(String.valueOf(luaChon.getMaGiayTamTru().get()));
        manhankhau_fld.setText(String.valueOf(luaChon.getMaNhanKhau().get()));
        noithuongtru_fld.setText(String.valueOf(luaChon.getNoiThuongTru().get()));
        tungay_fld.setText(String.valueOf(luaChon.getTuNgay().get()));
        denngay_fld.setText(String.valueOf(luaChon.getDenNgay().get()));
        ngaysinh_fld.setText(String.valueOf(luaChon.getNgaySinh().get()));
        lydo_fld.setText(String.valueOf(luaChon.getLyDo().get()));
        thoat_chinhsua_button.setOnAction(event -> {
            Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.TAM_TRU_LIST);
        });

        khai_tu_btn.setOnAction(event -> xoaTamTru());


    }

    public void xoaTamTru(){
        int magiaytamtru = Integer.parseInt(magiaytamtru_fld.getText());
        Model.getInstance().getDatabaseConnection().xoaTamTru(magiaytamtru);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Xoá Thành Công");
        alert.setContentText("Thông tin đã được xoá");
        alert.showAndWait();
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.TAM_TRU_LIST);
    }








}
