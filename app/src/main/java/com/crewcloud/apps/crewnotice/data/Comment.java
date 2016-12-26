package com.crewcloud.apps.crewnotice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tunglam on 12/16/16.
 */

public class Comment {
    @SerializedName("CommentNo")
    private int commentNo;

    @SerializedName("NoticeNo")
    private int noticeNo;

    @SerializedName("RegUserNo")
    private int regUserNo;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("PositionName")
    private String positionName;

    @SerializedName("RegDate")
    private String regDate;

    @SerializedName("Content")
    private String content;

    @SerializedName("ModUserNo")
    private int modUserNo;

    @SerializedName("ModDate")
    private String modDate;

    @SerializedName("reply")
    private List<Reply> lstReply;

    public Comment() {
    }

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public int getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(int noticeNo) {
        this.noticeNo = noticeNo;
    }

    public int getRegUserNo() {
        return regUserNo;
    }

    public void setRegUserNo(int regUserNo) {
        this.regUserNo = regUserNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getModUserNo() {
        return modUserNo;
    }

    public void setModUserNo(int modUserNo) {
        this.modUserNo = modUserNo;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public List<Reply> getLstReply() {
        return lstReply;
    }

    public void setLstReply(List<Reply> lstReply) {
        this.lstReply = lstReply;
    }
}

