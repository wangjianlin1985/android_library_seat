package com.mobileclient.domain;

import java.io.Serializable;

public class Room implements Serializable {
    /*阅览室id*/
    private int roomId;
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*阅览室名称*/
    private String roomName;
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*阅览室照片*/
    private String roomPhoto;
    public String getRoomPhoto() {
        return roomPhoto;
    }
    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

    /*阅览室位置*/
    private String roomPlace;
    public String getRoomPlace() {
        return roomPlace;
    }
    public void setRoomPlace(String roomPlace) {
        this.roomPlace = roomPlace;
    }

    /*总座位数*/
    private int seatNum;
    public int getSeatNum() {
        return seatNum;
    }
    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

}