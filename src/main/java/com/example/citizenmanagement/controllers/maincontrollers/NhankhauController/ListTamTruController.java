package com.example.citizenmanagement.controllers.maincontrollers.NhankhauController;

import com.example.citizenmanagement.models.List_nhan_khau;
import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.MainTamTruCell;
import com.example.citizenmanagement.models.Model;
import com.example.citizenmanagement.views.List_nhan_khau_factory;
import com.example.citizenmanagement.views.MainTamTruCellFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListTamTruController implements Initializable {
    public ListView<MainTamTruCell> list_tam_tru;
    public Button Them_tam_tru_btn;

    public TextField nhapvao_fld;


    public ChoiceBox timkiemtamtru_choicebox = new ChoiceBox<>();
    public ChoiceBox chedoxem_choicebox;
    ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        khoitaochedoxem();
        capnhat2();

        chedoxem_choicebox.setOnAction(event ->{
            if(chedoxem_choicebox.getValue() == "Tất cả"){
                capnhat2();
            }
            else if(chedoxem_choicebox.getValue() == "Còn hiệu lực"){
                showDanhSachTamTruConHieuLuc();
            }
            else if(chedoxem_choicebox.getValue() == "Hết hiệu lực"){
                showDanhSachTamTruHetHieuLuc();
            }
        });

        Them_tam_tru_btn.setOnAction(event -> onTamtru());

        list_tam_tru.setCellFactory(param -> new MainTamTruCellFactory());

        list_tam_tru.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                Model.setNhanKhauTamTruDuocChon(list_tam_tru.getSelectionModel().getSelectedItem());
                Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.XEM_CHI_TIET_TAM_TRU);

            }
        });
    }

    private void onTamtru() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.TAM_TRU);
    }

    public void capnhat2() {
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getDanhSachTamTru();
        list_tam_tru.getItems().clear();
        try{
            if(resultSet.isBeforeFirst()){
                while (resultSet.next()) {
                    String hovaten = resultSet.getString("HOTEN");
                    String magiaytamtru = resultSet.getString("MAGIAYTAMTRU");
                    String manhankhau = resultSet.getString("MANHANKHAU");
                    String noithuongtru = resultSet.getString("NOITHUONGTRU");
                    String tungay = resultSet.getString("TUNGAY");
                    String denngay = resultSet.getString("DENNGAY");
                    String soccd = resultSet.getString("SOCANCUOC");
                    String lydo = resultSet.getString("LYDO");
                    String ngaysinh = resultSet.getString("NGAYSINH");
                    list_tam_tru.getItems().add(new MainTamTruCell(hovaten,magiaytamtru,manhankhau,noithuongtru,tungay,denngay,soccd,ngaysinh,lydo));
                }
            }
        }
        catch(Exception e) {
            System.out.println("Lỗi ở NhanKhaumenucontroller");
        }
        list_tam_tru.setCellFactory(param-> new MainTamTruCellFactory());
    }

    public void khoitaochedoxem(){
        chedoxem_choicebox.getItems().addAll("Tất cả","Còn hiệu lực","Hết hiệu lực");
        chedoxem_choicebox.setValue("Tất cả");
    }

    public void showDanhSachTamTruConHieuLuc() {
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getDanhSachTamTruConHieuLuc();
        list_tam_tru.getItems().clear();
        try{
            if(resultSet.isBeforeFirst()){
                while (resultSet.next()) {
                    String hovaten = resultSet.getString("HOTEN");
                    String magiaytamtru = resultSet.getString("MAGIAYTAMTRU");
                    String manhankhau = resultSet.getString("MANHANKHAU");
                    String noithuongtru = resultSet.getString("NOITHUONGTRU");
                    String tungay = resultSet.getString("TUNGAY");
                    String denngay = resultSet.getString("DENNGAY");
                    String soccd = resultSet.getString("SOCANCUOC");
                    String lydo = resultSet.getString("LYDO");
                    String ngaysinh = resultSet.getString("NGAYSINH");
                    list_tam_tru.getItems().add(new MainTamTruCell(hovaten,magiaytamtru,manhankhau,noithuongtru,tungay,denngay,soccd,ngaysinh,lydo));
                }
            }
        }
        catch(Exception e) {
            System.out.println("Lỗi ở ListTamTruController");
        }
        list_tam_tru.setCellFactory(param-> new MainTamTruCellFactory());
    }

    public void showDanhSachTamTruHetHieuLuc() {
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getDanhSachTamTruHetHieuLuc();
        list_tam_tru.getItems().clear();
        try{
            if(resultSet.isBeforeFirst()){
                while (resultSet.next()) {
                    String hovaten = resultSet.getString("HOTEN");
                    String magiaytamtru = resultSet.getString("MAGIAYTAMTRU");
                    String manhankhau = resultSet.getString("MANHANKHAU");
                    String noithuongtru = resultSet.getString("NOITHUONGTRU");
                    String tungay = resultSet.getString("TUNGAY");
                    String denngay = resultSet.getString("DENNGAY");
                    String soccd = resultSet.getString("SOCANCUOC");
                    String lydo = resultSet.getString("LYDO");
                    String ngaysinh = resultSet.getString("NGAYSINH");
                    list_tam_tru.getItems().add(new MainTamTruCell(hovaten,magiaytamtru,manhankhau,noithuongtru,tungay,denngay,soccd,ngaysinh,lydo));
                }
            }
        }
        catch(Exception e) {
            System.out.println("Lỗi ở ListTamTruController");
        }
        list_tam_tru.setCellFactory(param-> new MainTamTruCellFactory());
    }
}
