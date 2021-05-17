package com.example.prestamos.operation;

import com.example.prestamos.entity.Collect;
import com.example.prestamos.entity.Lend;
import com.example.prestamos.entity.MessageDataBase;

public class OperationLend {

    public OperationLend(){

    }

    public Lend search(Lend lend){
        Lend lend2;
        if("123".equals(lend.dni)){
            lend2 =  new Lend(lend.dni, 1200.00, 200.00);
        } else if ("122".equals(lend.dni)){
            lend2 =  new Lend(lend.dni, 1400.00, 200.00);
        } else {
            lend2 = new Lend("", 0.00, 0.00);
        }
        return lend2;
    };

    public MessageDataBase update(Lend lend){


        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(lend.dni)){
            String message = "El cliente "+lend.dni+" no se puede actualizar";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Prestamo actualizado");
        }
        return messageDB;
    };

    public MessageDataBase insert(Lend lend){

        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(lend.dni)){
            String message = "El cliente "+lend.dni+" ya existe";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Prestamo asignado");
        }

        return messageDB;
    };

    public MessageDataBase delete(Lend lend){

        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(lend.dni)){
            String message = "El cliente "+lend.dni+" tiene un prestamo";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Cliente eliminado");
        }
        return  messageDB;
    };

}
