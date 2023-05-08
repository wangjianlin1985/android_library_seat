package com.mobileclient.domain;

import java.io.Serializable;

public class SeatState implements Serializable {
    /*状态id*/
    private int stateId;
    public int getStateId() {
        return stateId;
    }
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    /*状态名称*/
    private String stateName;
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}