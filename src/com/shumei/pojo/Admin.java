package com.shumei.pojo;

import java.sql.Timestamp;

public class Admin {
    private int id;           // 原来是 adminID，改成 id
    private String username;
    private String password;
    private String nickname;
    private Timestamp createTime;

    public Admin() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }
}