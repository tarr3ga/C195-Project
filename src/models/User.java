/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author jamyers
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private boolean active;
    private Timestamp createDate;
    private int createdBy;
    private Timestamp lastUpdate;
    private int updatedBy;
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; } 
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }

    public int getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(int updatedBy) { this.updatedBy = updatedBy; }

    public User(int userId, String username, String password, boolean active, Timestamp createDate, 
            int createdBy, Timestamp lastUpdate, int updatedBy) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }
    
    
}
