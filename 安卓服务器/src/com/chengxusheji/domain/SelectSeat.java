package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SelectSeat {
    /*ѡ��id*/
    private int selectId;
    public int getSelectId() {
        return selectId;
    }
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    /*��λ���*/
    private Seat seatObj;
    public Seat getSeatObj() {
        return seatObj;
    }
    public void setSeatObj(Seat seatObj) {
        this.seatObj = seatObj;
    }

    /*ѡ���û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*ѡ����ʼʱ��*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*ѡ������ʱ��*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*ѡ��״̬*/
    private String seatState;
    public String getSeatState() {
        return seatState;
    }
    public void setSeatState(String seatState) {
        this.seatState = seatState;
    }

}