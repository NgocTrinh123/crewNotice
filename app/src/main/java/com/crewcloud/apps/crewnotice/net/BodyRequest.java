package com.crewcloud.apps.crewnotice.net;

/**
 * Created by tunglam on 12/23/16.
 */
public class BodyRequest {
    private String timeZoneOffset;
    private String languageCode;
    private String sessionId;

    private int divisionNo;
    private String searchText;
    private boolean importantOnly;
    private String countOfArticles;
    private int anchorNoticeNo;
    private int noticeNo;

    public BodyRequest(String timeZoneOffset, String languageCode, String sessionId) {
        this.timeZoneOffset = timeZoneOffset;
        this.languageCode = languageCode;
        this.sessionId = sessionId;
    }

    public void setDivisionNo(int divisionNo) {
        this.divisionNo = divisionNo;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setImportantOnly(boolean importantOnly) {
        this.importantOnly = importantOnly;
    }

    public void setCountOfArticles(String countOfArticles) {
        this.countOfArticles = countOfArticles;
    }

    public void setAnchorNoticeNo(int anchorNoticeNo) {
        this.anchorNoticeNo = anchorNoticeNo;
    }

    public void setNoticeNo(int noticeNo) {
        this.noticeNo = noticeNo;
    }
}
