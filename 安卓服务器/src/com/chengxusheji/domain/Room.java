package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Room {
    /*������id*/
    private int roomId;
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*����������*/
    private String roomName;
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*��������Ƭ*/
    private String roomPhoto;
    public String getRoomPhoto() {
        return roomPhoto;
    }
    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    /*������λ��*/
    private String roomPlace;
    public String getRoomPlace() {
        return roomPlace;
    }
    public void setRoomPlace(String roomPlace) {
        this.roomPlace = roomPlace;
    }

    /*����λ��*/
    private int seatNum;
    public int getSeatNum() {
        return seatNum;
    }
    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

}