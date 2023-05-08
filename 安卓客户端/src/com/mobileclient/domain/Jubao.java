package com.mobileclient.domain;

import java.io.Serializable;

public class Jubao implements Serializable {
    /*举报id*/
    private int jubaoId;
    public int getJubaoId() {
        return jubaoId;
    }
    public void setJubaoId(int jubaoId) {
        this.jubaoId = jubaoId;
    }

    /*举报标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*举报内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*举报用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*举报时间*/
    private String jubaoTime;
    public String getJubaoTime() {
        return jubaoTime;
    }
    public void setJubaoTime(String jubaoTime) {
        this.jubaoTime = jubaoTime;
    }

    /*管理回复*/
    private String replyContent;
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

}