package com.mobileserver.domain;

public class UserInfo {
    /*�û���*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*�û�����*/
    private int userTypeObj;
    public int getUserTypeObj() {
        return userTypeObj;
    }
    public void setUserTypeObj(int userTypeObj) {
        this.userTypeObj = userTypeObj;
    }

    /*���ڰ༶*/
    private String classObj;
    public String getClassObj() {
        return classObj;
    }
    public void setClassObj(String classObj) {
        this.classObj = classObj;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*��������*/
    private java.sql.Timestamp birthDate;
    public java.sql.Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(java.sql.Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*�û���Ƭ*/
    private String userPhoto;
    public String getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*����*/
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*��ͥ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*�Ƿ������*/
    private String blackFlag;
    public String getBlackFlag() {
        return blackFlag;
    }
    public void setBlackFlag(String blackFlag) {
        this.blackFlag = blackFlag;
    }

    /*���÷�*/
    private float creditScore;
    public float getCreditScore() {
        return creditScore;
    }
    public void setCreditScore(float creditScore) {
        this.creditScore = creditScore;
    }

    /*ע��ʱ��*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

}