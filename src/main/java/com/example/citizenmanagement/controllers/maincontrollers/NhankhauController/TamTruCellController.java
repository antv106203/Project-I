package com.example.citizenmanagement.controllers.maincontrollers.NhankhauController;

import com.example.citizenmanagement.models.MainTamTruCell;
import com.example.citizenmanagement.models.MainTamVangCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TamTruCellController implements Initializable {
    @FXML
    private Text denngay_text;

    @FXML
    private Text hovaten_text;

    @FXML
    private Text magiaytamtru_text;

    @FXML
    private Text manhankhau_text;

    @FXML
    private Text noisinh_text;

    @FXML
    private Text tungay_text;
    private final MainTamTruCell tamTruCell;
    public TamTruCellController(MainTamTruCell mainTamTruCell){
        this.tamTruCell = mainTamTruCell;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hovaten_text.setText(String.valueOf(tamTruCell.getHoVaTen().get()));
        magiaytamtru_text.setText(String.valueOf(tamTruCell.getMaGiayTamTru().get()));
        manhankhau_text.setText(String.valueOf(tamTruCell.getMaNhanKhau().get()));
        noisinh_text.setText(String.valueOf(tamTruCell.getNoiThuongTru().get()));
        tungay_text.setText(String.valueOf(tamTruCell.getTuNgay().get()));
        denngay_text.setText(String.valueOf(tamTruCell.getDenNgay().get()));
    }
}
