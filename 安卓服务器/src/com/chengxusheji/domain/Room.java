package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Room {
    /*ÔÄÀÀÊÒid*/
    private int roomId;
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*ÔÄÀÀÊÒÃû³Æ*/
    private String roomName;
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*ÔÄÀÀÊÒÕÕÆ¬*/
    private String roomPhoto;
    public String getRoomPhoto() {
        return roomPhoto;
    }
    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    /*ÔÄÀÀÊÒÎ»ÖÃ*/
    private String roomPlace;
    public String getRoomPlace() {
        return roomPlace;
    }
    public void setRoomPlace(String roomPlace) {
        this.roomPlace = roomPlace;
    }

    /*×Ü×ùÎ»Êı*/
    private int seatNum;
    public int getSeatNum() {
        return seatNum;
    }
    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

}