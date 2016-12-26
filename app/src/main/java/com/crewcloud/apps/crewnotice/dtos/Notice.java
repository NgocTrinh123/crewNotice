package com.crewcloud.apps.crewnotice.dtos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tunglam on 12/15/16.
 */

public class Notice {

    @SerializedName("NoticeNo")
    private int noticeNo;

    @SerializedName("UserNo")
    private int userNo;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("PositionName")
    private String positionName;

    @SerializedName("DateCreated")
    private String dateCreated;

    @SerializedName("RepresentativeImageUrl")
    private String representativeImageUrl;

    @SerializedName("Title")
    private String title;

    @SerializedName("DivisionName")
    private String divisionName;

    @SerializedName("DivisionNo")
    private int divisionNo;

    @SerializedName("Important")
    private boolean important;

    @SerializedName("CountRead")
    private int countRead;

    @SerializedName("CountComment")
    private int countComment;


    public Notice() {
    }

    public int getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(int noticeNo) {
        this.noticeNo = noticeNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRepresentativeImageUrl() {
        return representativeImageUrl;
    }

    public void setRepresentativeImageUrl(String representativeImageUrl) {
        this.representativeImageUrl = representativeImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getDivisionNo() {
        return divisionNo;
    }

    public void setDivisionNo(int divisionNo) {
        this.divisionNo = divisionNo;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public int getCountRead() {
        return countRead;
    }

    public void setCountRead(int countRead) {
        this.countRead = countRead;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }
}
