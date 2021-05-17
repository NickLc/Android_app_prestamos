package com.example.prestamos.ui.lend;

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
import com.example.prestamos.entity.Lend;
import com.example.prestamos.entity.MessageDataBase;
import com.example.prestamos.operation.OperationLend;

public class LendFragment extends Fragment {

    String mode = "Init";
    TextView tvMessage;
    Button btnSubmit;
    ImageButton btnSearch;
    ImageButton btnUpdate;
    ImageButton btnInsert;
    ImageButton btnDelete;

    /*Form*/
    EditText etDni;

    /*Update_Here*/
    EditText etAmountLend;
    EditText etAmountCollect;
    /**/

    OperationLend operationLend = new OperationLend();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_lend, container, false);

        tvMessage = root.findViewById(R.id.tvMessage);

        btnSubmit = root.findViewById(R.id.btnSubmit);
        btnSearch = root.findViewById(R.id.btnSearch);
        btnInsert = root.findViewById(R.id.btnInsert);
        btnUpdate = root.findViewById(R.id.btnUpdate);
        btnDelete = root.findViewById(R.id.btnDelete);

        etDni = root.findViewById(R.id.etDni);
        /*Update_Here*/
        etAmountLend = root.findViewById(R.id.etAmountLend);
        etAmountCollect = root.findViewById(R.id.etAmountCollectLend);

        etAmountCollect.setEnabled(false);
        /**/


        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnInsert.setEnabled(false);
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
                Lend lend = getDataForm();
                MessageDataBase messageDataBase = new MessageDataBase();

                switch (mode){
                    case "Search":
                        lend = operationLend.search(lend);
                        if("".equals(lend.dni)){
                            messageDataBase = new MessageDataBase("err", "No se encontra al cliente");
                            cleanKeyForm();
                            btnInsert.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setEnabled(false);
                        } else {
                            blockAllForm();
                            setDataForm(lend);
                            if("".equals(lend.amountLent)){
                                btnInsert.setEnabled(true);
                                messageDataBase = new MessageDataBase("success", "El cliente no tiene un prestamo asignado");
                            } else {
                                btnInsert.setEnabled(false);
                                messageDataBase = new MessageDataBase("success", "El cliente tiene un prestamo asignado");
                            }

                            btnDelete.setEnabled(true);
                            btnUpdate.setEnabled(true);
                        }
                        break;
                    case "Insert":
                        messageDataBase = operationLend.insert(lend);
                        if("err".equals(messageDataBase.status)){
                            cleanKeyForm();
                        }
                        blockAllForm();
                        break;
                    case "Update":
                        messageDataBase = operationLend.update(lend);
                        blockAllForm();
                        break;
                    case "Delete":
                        messageDataBase = operationLend.delete(lend);
                        blockAllForm();
                        break;
                }
                showMessageOperation(messageDataBase);
            }
        });

        return root;
    }

    /*Update_Here*/
    public Lend getDataForm(){
        Lend lend = new Lend();
        lend.dni = etDni.getText().toString().trim();

        String amountLend = etAmountLend.getText().toString().trim();
        lend.amountLent = amountLend.isEmpty() ? 0.0 : Double.parseDouble(amountLend);

        String amountCollect = etAmountCollect.getText().toString().trim();
        lend.amountCollect = amountCollect.isEmpty() ? 0.0 : Double.parseDouble(amountCollect);

        return lend;
    }
    /*Update_Here*/
    public void setDataForm(Lend lend){
        etDni.setText(lend.dni);
        etAmountLend.setText(Double.toString(lend.amountLent));
        etAmountCollect.setText(Double.toString(lend.amountCollect));

    }

    public void cleanKeyForm(){
        etDni.setText("");
    }
    /*Update_Here*/
    public void cleanForm(){
        etAmountLend.setText("");
        etAmountCollect.setText("");
    }

    public void enableKeyForm(boolean status){
        etDni.setEnabled(status);
    }
    /*Update_Here*/
    public void enableForm(boolean status){
        etAmountLend.setEnabled(status);
    }

    public void enableSubmit(boolean status){
        btnSubmit.setEnabled(status);
    }

    public void blockAllForm(){
        enableKeyForm(false);
        enableForm(false);
        enableSubmit(false);
        btnInsert.setEnabled(false);
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
                btnInsert.setEnabled(false);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                break;
            case "Insert":
                cleanForm();
                enableKeyForm(false);
                enableForm(true);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                break;
            case "Update":
                enableKeyForm(false);
                enableForm(true);
                btnInsert.setEnabled(false);
                btnDelete.setEnabled(false);
                break;
            case "Delete":
                enableKeyForm(false);
                enableForm(false);
                btnInsert.setEnabled(false);
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
