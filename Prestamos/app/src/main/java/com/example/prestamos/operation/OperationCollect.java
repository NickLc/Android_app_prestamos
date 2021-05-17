package com.example.prestamos.operation;

import com.example.prestamos.entity.Client;
import com.example.prestamos.entity.Collect;
import com.example.prestamos.entity.MessageDataBase;

public class OperationCollect {

    public OperationCollect(){

    }

    public Collect search(Collect collect){
        Collect collect2;
        if("123".equals(collect.dni)){
            collect2 =  new Collect(collect.dni, 200.00, 1200.00);
        } else if ("122".equals(collect.dni)){
            collect2 =  new Collect(collect.dni, 400.00, 1400.00);
        } else {
            collect2 = new Collect("", 0.00, 0.00);
        }
        return collect2;
    };

    public MessageDataBase update(Collect collect){


        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(collect.dni)){
            String message = "El cliente "+collect.dni+" no se puede actualizar";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Cliente actualizado");
        }
        return messageDB;
    };

    public MessageDataBase insert(Collect collect){

        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(collect.dni)){
            String message = "El cliente "+collect.dni+" ya existe";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Cliente creado");
        }

        return messageDB;
    };

    public MessageDataBase delete(Collect collect){

        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(collect.dni)){
            String message = "El cliente "+collect.dni+" tiene un prestamo";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Cliente eliminado");
        }
        return  messageDB;
    };

}
