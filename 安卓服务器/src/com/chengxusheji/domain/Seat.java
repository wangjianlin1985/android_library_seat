package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Seat {
    /*座位id*/
    private int seatId;
    public int getSeatId() {
        return seatId;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    /*所在阅览室*/
    private Room roomObj;
    public Room getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(Room roomObj) {
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
    private SeatState seatStateObj;
    public SeatState getSeatStateObj() {
        return seatStateObj;
    }
    public void setSeatStateObj(SeatState seatStateObj) {
        this.seatStateObj = seatStateObj;
    }

}