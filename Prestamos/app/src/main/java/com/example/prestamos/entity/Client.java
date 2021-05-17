package com.example.prestamos.entity;

public class Client {
    public String dni;
    public String names;

    public Client(){
        this.dni = "";
        this.names = "";
    }

    public Client(String dni, String names){
        this.dni = dni;
        this.names = names;
    }

}
