package com.example.invento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Displayscanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

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
        Display_details.BarId.setText(arrOfStr[0]);

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