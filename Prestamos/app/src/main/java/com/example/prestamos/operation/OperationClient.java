package com.example.prestamos.operation;

import com.example.prestamos.entity.Client;
import com.example.prestamos.entity.MessageDataBase;


public class OperationClient {

    public OperationClient(){

    }

    public Client search(Client client){
        Client client2;
        if("123".equals(client.dni)){
            client2 =  new Client(client.dni, "Peter Castle");
        } else if ("122".equals(client.dni)){
            client2 =  new Client(client.dni, "Keiko Fujimori");
        } else {
            client2 = new Client("", "");
        }
        return client2;
    };

    public MessageDataBase update(Client client){


        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(client.dni)){
            String message = "El cliente "+client.dni+" no se puede actualizar";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Cliente actualizado");
        }

        return messageDB;
    };

    public MessageDataBase insert(Client client){

        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(client.dni)){
            String message = "El cliente "+client.dni+" ya existe";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Cliente creado");
        }

        return messageDB;
    };

    public MessageDataBase delete(Client client){


        /*Code Here*/
        MessageDataBase messageDB;

        if("123".equals(client.dni)){
            String message = "El cliente "+client.dni+" tiene un prestamo";
            messageDB = new MessageDataBase("err", message);
        } else {
            messageDB = new MessageDataBase("success", "Cliente eliminado");
        }
        return  messageDB;
    };


}
