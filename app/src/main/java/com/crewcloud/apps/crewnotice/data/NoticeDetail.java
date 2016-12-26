package com.crewcloud.apps.crewnotice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tunglam on 12/24/16.
 */

public class NoticeDetail {

    @SerializedName("NoticeNo")
    private int noticeNo;

    @SerializedName("RegUserNo")
    private int regUserNo;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("PositionName")
    private String positionName;

    @SerializedName("DepartName")
    private String departName;

    @SerializedName("Regdate")
    private String regdate;

    @SerializedName("ModUserNo")
    private int modUserNo;

    @SerializedName("ModDate")
    private String modDate;

    @SerializedName("Title")
    private String title;

    @SerializedName("DivisionNo")
    private int divisionNo;

    @SerializedName("Content")
    private String content;

    @SerializedName("StartDate")
    private String startDate;

    @SerializedName("EndDate")
    private String endDate;

    @SerializedName("Important")
    private boolean important;

    @SerializedName("IsShare")
    private boolean isShare;

    @SerializedName("IsAttach")
    private boolean isAttach;

    @SerializedName("TotalViews")
    private int totalView;

    @SerializedName("CurrentViews")
    private int currentViews;

    @SerializedName("IsContentImg")
    private boolean isContentImg;

    @SerializedName("Attachments")
    private List<Attachments> attaches;

    @SerializedName("Comments")
    private List<Comment> commentList;

    public NoticeDetail() {
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

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDivisionNo() {
        return divisionNo;
    }

    public void setDivisionNo(int divisionNo) {
        this.divisionNo = divisionNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        isShare = share;
    }

    public boolean isAttach() {
        return isAttach;
    }

    public void setAttach(boolean attach) {
        isAttach = attach;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }

    public int getCurrentViews() {
        return currentViews;
    }

    public void setCurrentViews(int currentViews) {
        this.currentViews = currentViews;
    }

    public boolean isContentImg() {
        return isContentImg;
    }

    public void setContentImg(boolean contentImg) {
        isContentImg = contentImg;
    }

    public List<Attachments> getAttaches() {
        return attaches;
    }

    public void setAttaches(List<Attachments> attaches) {
        this.attaches = attaches;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
