package com.chengxusheji.domain;

import java.sql.Timestamp;
public class UserType {
    /*�û�����id*/
    private int userTypeId;
    public int getUserTypeId() {
        return userTypeId;
    }
    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    /*�û���������*/
    private String userTypeName;
    public String getUserTypeName() {
        return userTypeName;
    }
    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

}