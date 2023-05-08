package com.mobileserver.domain;

public class SelectSeat {
    /*选座id*/
    private int selectId;
    public int getSelectId() {
        return selectId;
    }
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    /*座位编号*/
    private int seatObj;
    public int getSeatObj() {
        return seatObj;
    }
    public void setSeatObj(int seatObj) {
        this.seatObj = seatObj;
    }

    /*选座用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*选座开始时间*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*选座结束时间*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*选座状态*/
    private String seatState;
    public String getSeatState() {
        return seatState;
    }
    public void setSeatState(String seatState) {
        this.seatState = seatState;
    }

}