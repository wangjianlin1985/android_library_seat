package com.mobileclient.domain;

import java.io.Serializable;

public class ClassInfo implements Serializable {
    /*班级编号*/
    private String classNo;
    public String getClassNo() {
        return classNo;
    }
    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    /*班级名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*成立日期*/
    private java.sql.Timestamp bornDate;
    public java.sql.Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(java.sql.Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*班主任*/
    private String mainTeacher;
    public String getMainTeacher() {
        return mainTeacher;
    }
    public void setMainTeacher(String mainTeacher) {
        this.mainTeacher = mainTeacher;
    }

    /*班级备注*/
    private String classMemo;
    public String getClassMemo() {
        return classMemo;
    }
    public void setClassMemo(String classMemo) {
        this.classMemo = classMemo;
    }

}