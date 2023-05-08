package com.mobileclient.domain;

import java.io.Serializable;

public class Seat implements Serializable {
    /*座位id*/
    private int seatId;
    public int getSeatId() {
        return seatId;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    /*所在阅览室*/
    private int roomObj;
    public int getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(int roomObj) {
        this.roomObj = roomObj;
    }

    /*座位编号*/
    private String seatCode;
    public String getSeatCode() {
        return seatCode;
    }
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /*当前状态*/
    private int seatStateObj;
    public int getSeatStateObj() {
        return seatStateObj;
    }
    public void setSeatStateObj(int seatStateObj) {
        this.seatStateObj = seatStateObj;
    }

}