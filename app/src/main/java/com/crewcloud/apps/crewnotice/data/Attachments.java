package com.crewcloud.apps.crewnotice.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tunglam on 12/26/16.
 */

public class Attachments {
    @SerializedName("AttachNo")
    private int attachNo;

    @SerializedName("NoticeNo")
    private int noticeNo;

    @SerializedName("FileName")
    private String fileName;

    @SerializedName("FileLength")
    private int filelength;

    @SerializedName("FilePath")
    private String filepath;

    public Attachments() {
    }

    public int getAttachNo() {
        return attachNo;
    }

    public void setAttachNo(int attachNo) {
        this.attachNo = attachNo;
    }

    public int getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(int noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFilelength() {
        return filelength;
    }

    public void setFilelength(int filelength) {
        this.filelength = filelength;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
