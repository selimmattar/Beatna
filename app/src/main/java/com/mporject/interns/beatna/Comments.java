package com.mporject.interns.beatna;

public class Comments {

    private String userCmt;
    private String dateCmt;
    private String commentCmt;

    public Comments() {}

    public Comments(String userCmt, String dateCmt, String commentCmt) {
        this.userCmt = userCmt;
        this.dateCmt = dateCmt;
        this.commentCmt = commentCmt;
    }

    public String getUserCmt() {
        return userCmt;
    }

    public void setUserCmt(String userCmt) {
        this.userCmt = userCmt;
    }

    public String getDateCmt() {
        return dateCmt;
    }

    public void setDateCmt(String dateCmt) {
        this.dateCmt = dateCmt;
    }

    public String getCommentCmt() {
        return commentCmt;
    }

    public void setCommentCmt(String commentCmt) {
        this.commentCmt = commentCmt;
    }
}
