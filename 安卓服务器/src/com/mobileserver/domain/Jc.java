package com.mobileserver.domain;

public class Jc {
    /*奖惩id*/
    private int jcId;
    public int getJcId() {
        return jcId;
    }
    public void setJcId(int jcId) {
        this.jcId = jcId;
    }

    /*奖惩类型*/
    private String jcType;
    public String getJcType() {
        return jcType;
    }
    public void setJcType(String jcType) {
        this.jcType = jcType;
    }

    /*奖惩标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*奖惩内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*奖惩用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*信用分数*/
    private float creditScore;
    public float getCreditScore() {
        return creditScore;
    }
    public void setCreditScore(float creditScore) {
        this.creditScore = creditScore;
    }

    /*奖惩时间*/
    private String jcTime;
    public String getJcTime() {
        return jcTime;
    }
    public void setJcTime(String jcTime) {
        this.jcTime = jcTime;
    }

}