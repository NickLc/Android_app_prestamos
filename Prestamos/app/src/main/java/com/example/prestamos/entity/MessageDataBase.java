package com.example.prestamos.entity;

public class MessageDataBase {
    public String status;
    public String message;

    public MessageDataBase(){
        this.status = "mode";
        this.message = "Init";
    }

    public MessageDataBase(String status, String message){
        this.status = status;
        this.message = message;
    }
}
