package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Seat {
    /*��λid*/
    private int seatId;
    public int getSeatId() {
        return seatId;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    /*����������*/
    private Room roomObj;
    public Room getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(Room roomObj) {
        this.roomObj = roomObj;
    }

    /*��λ���*/
    private String seatCode;
    public String getSeatCode() {
        return seatCode;
    }
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /*��ǰ״̬*/
    private SeatState seatStateObj;
    public SeatState getSeatStateObj() {
        return seatStateObj;
    }
    public void setSeatStateObj(SeatState seatStateObj) {
        this.seatStateObj = seatStateObj;
    }

}