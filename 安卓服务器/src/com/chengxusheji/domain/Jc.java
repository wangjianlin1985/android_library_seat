package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Jc {
    /*����id*/
    private int jcId;
    public int getJcId() {
        return jcId;
    }
    public void setJcId(int jcId) {
        this.jcId = jcId;
    }

    /*��������*/
    private String jcType;
    public String getJcType() {
        return jcType;
    }
    public void setJcType(String jcType) {
        this.jcType = jcType;
    }

    /*���ͱ���*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*�����û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*���÷���*/
    private float creditScore;
    public float getCreditScore() {
        return creditScore;
    }
    public void setCreditScore(float creditScore) {
        this.creditScore = creditScore;
    }

    /*����ʱ��*/
    private String jcTime;
    public String getJcTime() {
        return jcTime;
    }
    public void setJcTime(String jcTime) {
        this.jcTime = jcTime;
    }

}