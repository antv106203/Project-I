package com.example.citizenmanagement.views;

import com.example.citizenmanagement.controllers.maincontrollers.NhankhauController.TamTruCellController;
import com.example.citizenmanagement.controllers.maincontrollers.NhankhauController.TamVangCellController;
import com.example.citizenmanagement.models.MainTamTruCell;
import com.example.citizenmanagement.models.MainTamVangCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class MainTamTruCellFactory extends ListCell<MainTamTruCell> {
    protected void updateItem(MainTamTruCell mainTamTruCell, boolean empty){
        super.updateItem(mainTamTruCell,empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_citizen/hoKhau/TamTruCell.fxml"));
            //hoKhauCellControler HKcellControler = new hoKhauCellControler(mainHoKhauCell);
            loader.setController(new TamTruCellController(mainTamTruCell));
            setText(null);
            try {
                setGraphic(loader.load());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
