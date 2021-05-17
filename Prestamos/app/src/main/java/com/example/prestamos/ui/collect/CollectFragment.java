package com.example.prestamos.ui.collect;

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
import com.example.prestamos.entity.Collect;
import com.example.prestamos.entity.MessageDataBase;
import com.example.prestamos.operation.OperationCollect;

public class CollectFragment extends Fragment  {

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
    EditText etAmountCollect;
    EditText etAmountLend;
    /**/

    OperationCollect operationCollect = new OperationCollect();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_collect, container, false);

        tvMessage = root.findViewById(R.id.tvMessage);

        btnSubmit = root.findViewById(R.id.btnSubmit);
        btnSearch = root.findViewById(R.id.btnSearch);
        btnInsert = root.findViewById(R.id.btnInsert);
        btnUpdate = root.findViewById(R.id.btnUpdate);
        btnDelete = root.findViewById(R.id.btnDelete);

        etDni = root.findViewById(R.id.etDni);
        /*Update_Here*/
        etAmountCollect = root.findViewById(R.id.etAmountCollect);
        etAmountLend = root.findViewById(R.id.etAmountLendCollect);

        etAmountLend.setEnabled(false);
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
                Collect collect = getDataForm();
                MessageDataBase messageDataBase = new MessageDataBase();

                switch (mode){
                    case "Search":
                        collect = operationCollect.search(collect);
                        if("".equals(collect.dni)){
                            messageDataBase = new MessageDataBase("err", "No se encontra al cliente");
                            cleanKeyForm();
                            btnInsert.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setEnabled(false);
                        } else {
                            blockAllForm();
                            setDataForm(collect);
                            messageDataBase = new MessageDataBase("success", "Cliente encontrado");
                            btnInsert.setEnabled(true);
                            btnDelete.setEnabled(true);
                            btnUpdate.setEnabled(true);
                        }
                        break;
                    case "Insert":
                        messageDataBase = operationCollect.insert(collect);
                        if("err".equals(messageDataBase.status)){
                            cleanKeyForm();
                        }
                        blockAllForm();
                        break;
                    case "Update":
                        messageDataBase = operationCollect.update(collect);
                        blockAllForm();
                        break;
                    case "Delete":
                        messageDataBase = operationCollect.delete(collect);
                        blockAllForm();
                        break;
                }
                showMessageOperation(messageDataBase);
            }
        });

        return root;
    }

    /*Update_Here*/
    public Collect getDataForm(){
        Collect collect = new Collect();
        collect.dni = etDni.getText().toString().trim();

        String amountCollect = etAmountCollect.getText().toString().trim();
        collect.amountCollect = amountCollect.isEmpty() ? 0.0 : Double.parseDouble(amountCollect);

        String amountLend = etAmountLend.getText().toString().trim();
        collect.amountLend = amountLend.isEmpty() ? 0.0 : Double.parseDouble(amountLend);

        return collect;
    }
    /*Update_Here*/
    public void setDataForm(Collect collect){
        etDni.setText(collect.dni);
        etAmountCollect.setText(Double.toString(collect.amountCollect));
        etAmountLend.setText(Double.toString(collect.amountLend));
    }

    public void cleanKeyForm(){
        etDni.setText("");
    }
    /*Update_Here*/
    public void cleanForm(){
        etAmountCollect.setText("");
    }

    public void enableKeyForm(boolean status){
        etDni.setEnabled(status);
    }
    /*Update_Here*/
    public void enableForm(boolean status){
        etAmountCollect.setEnabled(status);
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
