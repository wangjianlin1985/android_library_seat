package com.mobileserver.domain;

public class UserInfo {
    /*用户名*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*用户类型*/
    private int userTypeObj;
    public int getUserTypeObj() {
        return userTypeObj;
    }
    public void setUserTypeObj(int userTypeObj) {
        this.userTypeObj = userTypeObj;
    }

    /*所在班级*/
    private String classObj;
    public String getClassObj() {
        return classObj;
    }
    public void setClassObj(String classObj) {
        this.classObj = classObj;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*出生日期*/
    private java.sql.Timestamp birthDate;
    public java.sql.Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(java.sql.Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*用户照片*/
    private String userPhoto;
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*邮箱*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*是否黑名单*/
    private String blackFlag;
    public String getBlackFlag() {
        return blackFlag;
    }
    public void setBlackFlag(String blackFlag) {
        this.blackFlag = blackFlag;
    }

    /*信用分*/
    private float creditScore;
    public float getCreditScore() {
        return creditScore;
    }
    public void setCreditScore(float creditScore) {
        this.creditScore = creditScore;
    }

    /*注册时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

}