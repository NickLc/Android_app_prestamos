package com.example.prestamos.entity;

public class Collect {
    public String dni;
    public Double amountCollect;
    public Double amountLend;

    public Collect(){
        this.dni = "";
        this.amountCollect = 0.0;
        this.amountLend = 0.0;
    }

    public Collect(String dni, Double amountCollect, Double amountLend){
        this.dni = dni;
        this.amountCollect = amountCollect;
        this.amountLend = amountLend;
    }
}
