package com.example.datn_2020.repository.model;

public class Message {
    private int idTrip;
    private String nameTrip;
    private int idUserSend;
    private String usernameSend;
    private String imageUrlSend;
    private String type;
    private String usernameReceive;
    private String time;
    private int idUserReceive;

    public Message() {
    }

    public Message(int idTrip, String nameTrip, int idUserSend, String usernameSend, String imageUrlSend, String type, String usernameReceive, String time, int idUserReceive) {
        this.idTrip = idTrip;
        this.nameTrip = nameTrip;
        this.idUserSend = idUserSend;
        this.usernameSend = usernameSend;
        this.imageUrlSend = imageUrlSend;
        this.type = type;
        this.usernameReceive = usernameReceive;
        this.time = time;
        this.idUserReceive = idUserReceive;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setNameTrip(String nameTrip) {
        this.nameTrip = nameTrip;
    }

    public void setIdUserSend(int idUserSend) {
        this.idUserSend = idUserSend;
    }

    public void setUsernameSend(String usernameSend) {
        this.usernameSend = usernameSend;
    }

    public void setImageUrlSend(String imageUrlSend) {
        this.imageUrlSend = imageUrlSend;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsernameReceive(String usernameReceive) {
        this.usernameReceive = usernameReceive;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIdUserReceive(int idUserReceive) {
        this.idUserReceive = idUserReceive;
    }
}
