package com.example.citizenmanagement.controllers.maincontrollers;

import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.Model;

import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ThongKeHoKhauController implements Initializable {
    public StackedBarChart thongKeHoKhau;

    public Button QuayLai2;

    public ChoiceBox chonthongkesonam;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        khoitaothongkesonam();
        showBieuDoNhanKhau((Integer) chonthongkesonam.getValue());
        System.out.println(Model.getInstance().getCitizenManager().getHoTen());
        QuayLai2.setOnAction(event -> onQuayLai1());
        chonthongkesonam.setOnAction(event -> {
            showBieuDoNhanKhau((Integer) chonthongkesonam.getValue());
        });

    }

    void showBieuDoNhanKhau(int sonam){
        thongKeHoKhau.getData().clear();
        int namhientai = Model.getInstance().getNamHienTai();
        final XYChart.Series<String,Number> series = new XYChart.Series<>();
        for(int i = 0 ; i <= sonam - 1 ; i++){
            series.getData().add(new XYChart.Data<>(Integer.toString(namhientai-i),Model.getInstance().getHoKhauOfNam(namhientai-i)));
        }

        thongKeHoKhau.getData().add(series);

    }

    public  void onQuayLai1(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.TRANG_CHU);
    }

    public void khoitaothongkesonam(){
        chonthongkesonam.getItems().addAll(5,6,7,8,9,10);
        chonthongkesonam.setValue(5);
    }
}
