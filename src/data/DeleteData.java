/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jamyers
 */
public class DeleteData {
    private Connection conn;
    private Statement statement;
    private ResultSet resultSet;
    
    public void deleteAppointment(int appointmentId) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "DELETE FROM appointments WHERE ID = " + appointmentId + ";";
        
        statement = conn.createStatement();
        statement.execute(sql);
    }
}
