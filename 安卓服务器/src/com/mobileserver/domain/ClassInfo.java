package com.mobileserver.domain;

public class ClassInfo {
    /*�༶���*/
    private String classNo;
    public String getClassNo() {
        return classNo;
    }
    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    /*�༶����*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*��������*/
    private java.sql.Timestamp bornDate;
    public java.sql.Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(java.sql.Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*������*/
    private String mainTeacher;
    public String getMainTeacher() {
        return mainTeacher;
    }
    public void setMainTeacher(String mainTeacher) {
        this.mainTeacher = mainTeacher;
    }

    /*�༶��ע*/
    private String classMemo;
    public String getClassMemo() {
        return classMemo;
    }
    public void setClassMemo(String classMemo) {
        this.classMemo = classMemo;
    }

}