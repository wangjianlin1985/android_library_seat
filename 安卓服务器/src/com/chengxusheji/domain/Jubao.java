package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Jubao {
    /*�ٱ�id*/
    private int jubaoId;
    public int getJubaoId() {
        return jubaoId;
    }
    public void setJubaoId(int jubaoId) {
        this.jubaoId = jubaoId;
    }

    /*�ٱ�����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*�ٱ�����*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*�ٱ��û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*�ٱ�ʱ��*/
    private String jubaoTime;
    public String getJubaoTime() {
        return jubaoTime;
    }
    public void setJubaoTime(String jubaoTime) {
        this.jubaoTime = jubaoTime;
    }

    /*����ظ�*/
    private String replyContent;
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

}