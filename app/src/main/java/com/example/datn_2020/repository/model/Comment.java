package com.example.datn_2020.repository.model;

public class Comment {
    private int idPlace;
    private int idUserSend;
    private int idUserReceive;
    private String usernameAdminPost;
    private String usernameSend;
    private String namePlace;
    private String Content;
    private String time;
    private String type;

    public void setUsernameAdminPost(String usernameAdminPost) {
        this.usernameAdminPost = usernameAdminPost;
    }

    public void setUsernameSend(String usernameSend) {
        this.usernameSend = usernameSend;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public void setIdUserSend(int idUserSend) {
        this.idUserSend = idUserSend;
    }

    public void setIdUserReceive(int idUserReceive) {
        this.idUserReceive = idUserReceive;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }
}
