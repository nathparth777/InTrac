package com.example.invento;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView Scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Scanner = new ZXingScannerView(this);
        setContentView(Scanner);


    }

    @Override
    public void handleResult(Result result) {
        String str = result.getText().toString();
        String[] arrOfStr = str.split("-");
        Add_Item.BarId.setText(arrOfStr[0]);
        Add_Item.Name.setText(arrOfStr[1]);
        Add_Item.Weight.setText(arrOfStr[2]);
        Add_Item.MRP.setText(arrOfStr[3]);
        Add_Item.Totalitems.setText(arrOfStr[4]);
        Add_Item.Manufacture.setText(arrOfStr[5]);
        Add_Item.Expiry.setText(arrOfStr[6]);

        onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Scanner.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Scanner.setResultHandler(this);
        Scanner.startCamera();
    }
}
