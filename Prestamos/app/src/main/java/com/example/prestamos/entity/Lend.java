package com.example.prestamos.entity;

public class Lend {
    public String dni;
    public Double amountLent;
    public Double amountCollect;

    public Lend(){
        this.dni = "";
        this.amountLent = 0.0;
        this.amountCollect = 0.0;
    }

    public Lend(String dni, Double amountLent, Double amountCollect){
        this.dni = dni;
        this.amountLent = amountLent;
        this.amountCollect = amountCollect;
    }
}
