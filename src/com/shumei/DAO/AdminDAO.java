package com.shumei.DAO;

import com.shumei.pojo.Admin;

public interface AdminDAO {
    Admin getAdminUser(String username, String password);
}
