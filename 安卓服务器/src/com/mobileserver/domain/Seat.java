package com.mobileserver.domain;

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
    private int roomObj;
    public int getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(int roomObj) {
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
    private int seatStateObj;
    public int getSeatStateObj() {
        return seatStateObj;
    }
    public void setSeatStateObj(int seatStateObj) {
        this.seatStateObj = seatStateObj;
    }

}