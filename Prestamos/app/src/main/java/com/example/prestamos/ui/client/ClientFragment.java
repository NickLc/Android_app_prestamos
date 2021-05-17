package com.example.prestamos.ui.client;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prestamos.R;
import com.example.prestamos.entity.Client;
import com.example.prestamos.entity.MessageDataBase;
import com.example.prestamos.operation.OperationClient;

public class ClientFragment extends Fragment {

    String mode = "Init";
    TextView tvMessage;
    Button btnSubmit;
    ImageButton btnSearch;
    ImageButton btnUpdate;
    ImageButton btnInsert;
    ImageButton btnDelete;

    EditText etDni;

    /*Update_Here*/
    EditText etNames;
    /**/

    OperationClient operationClient = new OperationClient();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_client, container, false);

        tvMessage = root.findViewById(R.id.tvMessage);
        btnSubmit = root.findViewById(R.id.btnSubmit);
        btnSearch = root.findViewById(R.id.btnSearch);
        btnInsert = root.findViewById(R.id.btnInsert);
        btnUpdate = root.findViewById(R.id.btnUpdate);
        btnDelete = root.findViewById(R.id.btnDelete);

        etDni = root.findViewById(R.id.etDni);
        /*Update_Here*/
        etNames = root.findViewById(R.id.etNames);
        /**/

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        enableSubmit(false);
        enableForm(false);
        enableKeyForm(false);

        resetStatusMode(mode);

        root.findViewById(R.id.btnSearch).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                settingOnButton("Search");
            }
        });

        root.findViewById(R.id.btnInsert).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                settingOnButton("Insert");
            }
        });

        root.findViewById(R.id.btnUpdate).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                settingOnButton("Update");
            }
        });

        root.findViewById(R.id.btnDelete).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                settingOnButton("Delete");
            }
        });

        root.findViewById(R.id.btnSubmit).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                /*Update_Here*/
                Client client = getDataForm();
                MessageDataBase messageDataBase = new MessageDataBase();

                switch (mode){
                    case "Search":
                        client = operationClient.search(client);
                        if("".equals(client.dni)){
                            messageDataBase = new MessageDataBase("err", "No se encontra al cliente");
                            cleanKeyForm();
                            btnDelete.setEnabled(false);
                            btnUpdate.setEnabled(false);
                        } else {
                            blockAllForm();
                            setDataForm(client);
                            messageDataBase = new MessageDataBase("success", "Cliente encontrado");
                            btnDelete.setEnabled(true);
                            btnUpdate.setEnabled(true);
                        }
                        break;
                    case "Insert":
                        messageDataBase = operationClient.insert(client);
                        if("err".equals(messageDataBase.status)){
                            cleanKeyForm();
                        }
                        blockAllForm();
                        break;
                    case "Update":
                        messageDataBase = operationClient.update(client);
                        blockAllForm();
                        break;
                    case "Delete":
                        messageDataBase = operationClient.delete(client);
                        blockAllForm();
                        break;
                }
                showMessageOperation(messageDataBase);
            }
        });

        return root;
    }
    /*Update_Here*/
    public Client getDataForm(){
        Client client = new Client();
        client.dni = etDni.getText().toString().trim();
        client.names = etNames.getText().toString().trim();
        return client;
    }
    /*Update_Here*/
    public void setDataForm(Client client){
        etDni.setText(client.dni);
        etNames.setText(client.names);
    }

    public void cleanKeyForm(){
        etDni.setText("");
    }
    /*Update_Here*/
    public void cleanForm(){
        etNames.setText("");
    }

    public void enableKeyForm(boolean status){
        etDni.setEnabled(status);
    }
    /*Update_Here*/
    public void enableForm(boolean status){
        etNames.setEnabled(status);
    }

    public void enableSubmit(boolean status){
        btnSubmit.setEnabled(status);
    }

    public void blockAllForm(){
        enableKeyForm(false);
        enableForm(false);
        enableSubmit(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    public void showMessageOperation(MessageDataBase messageDataBase){
        String status = messageDataBase.status;
        String colorText = "#004BFA";
        switch (status){
            case "mode":
                colorText = "#004BFA";
                break;
            case "success":
                colorText = "#36E000";
                break;
            case "err":
                colorText = "#E0001B";
                break;
        }
        tvMessage.setTextColor(Color.parseColor(colorText));
        String messageText = status +": "+ messageDataBase.message;
        tvMessage.setText(messageText);
    }

    public void settingOnButton(String modeInit){
        mode = modeInit;
        resetStatusMode(mode);
        enableSubmit(true);
        switch (mode){
            case "Search":
                cleanKeyForm();
                cleanForm();
                enableKeyForm(true);
                enableForm(false);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                break;
            case "Insert":
                cleanKeyForm();
                cleanForm();
                enableKeyForm(true);
                enableForm(true);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                break;
            case "Update":
                enableKeyForm(false);
                enableForm(true);
                btnDelete.setEnabled(false);
                break;
            case "Delete":
                enableKeyForm(false);
                enableForm(false);
                btnUpdate.setEnabled(false);
                break;
        }
    }

    public void resetStatusMode(String mode){

        MessageDataBase messageDataBase = new MessageDataBase("mode", mode);
        showMessageOperation(messageDataBase);
        btnUpdate.setBackgroundColor(Color.TRANSPARENT);
        btnDelete.setBackgroundColor(Color.TRANSPARENT);
        btnInsert.setBackgroundColor(Color.TRANSPARENT);
        btnSearch.setBackgroundColor(Color.TRANSPARENT);

        switch (mode){
            case "Search":
                btnSearch.setBackgroundColor(Color.GREEN);
                break;
            case "Insert":
                btnInsert.setBackgroundColor(Color.parseColor("#EB842A"));
                break;
            case "Update":
                btnUpdate.setBackgroundColor(Color.BLUE);
                break;
            case "Delete":
                btnDelete.setBackgroundColor(Color.RED);
                break;
        }
    }
}