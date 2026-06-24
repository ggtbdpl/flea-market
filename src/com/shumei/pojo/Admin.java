package com.shumei.pojo;

public class Admin {
    int adminID;
    String username;
    String password;
    int role;

    public Admin(){}

    public Admin(String username, String password, int role){
        this.password=password;
        this.username=username;
        this.role=role;
    }
    //初始化


    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
