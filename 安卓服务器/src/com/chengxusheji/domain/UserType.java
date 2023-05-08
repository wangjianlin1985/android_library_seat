package com.chengxusheji.domain;

import java.sql.Timestamp;
public class UserType {
    /*用户类型id*/
    private int userTypeId;
    public int getUserTypeId() {
        return userTypeId;
    }
    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    /*用户类型名称*/
    private String userTypeName;
    public String getUserTypeName() {
        return userTypeName;
    }
    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

}