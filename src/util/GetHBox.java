/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import data.FetchData;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.layout.HBox;
import models.Customer;

/**
 *
 * @author jamyers
 */
public class GetHBox {
    public HBox constructHBox(Object o) throws SQLException, IOException {
        HBox hbox = new HBox();
        
        FetchData data = new FetchData();
        
        if(o.getClass().getName().equals("Customer")){
            data.fetchData("Customer");
        }
        
        return hbox;
    }
}
