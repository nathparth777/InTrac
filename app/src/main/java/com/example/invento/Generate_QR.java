package com.example.invento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class Generate_QR extends AppCompatActivity {

    EditText Value, ValName, Weight, MRP, Total, Manufacture, Exp;
    Button Generate, Download;
    ImageView imageView;
    String savePath = Environment.getExternalStorageDirectory().toString();
    File myDir = new File(savePath + "/saved_barcode");
    Bitmap bitmap;
    ImageButton ManuButton, ExpButton;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__q_r);

        Value = findViewById(R.id.value);
        ValName = findViewById(R.id.valname);
        Generate = findViewById(R.id.generate);
        //Download = findViewById(R.id.download);
        imageView = findViewById(R.id.barcode);
        Weight = findViewById(R.id.weight);
        MRP = findViewById(R.id.mrp);
        Total = findViewById(R.id.total);
        Manufacture = findViewById(R.id.manufacture);
        Exp = findViewById(R.id.expiry);
        ManuButton = findViewById(R.id.manufactureButton);
        ExpButton = findViewById(R.id.expiryButton);

        ManuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Generate_QR.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Manufacture.setText(i2 +"/"+ (i1+1) +"/"+ i);
                    }
                },year, month, day);
                datePickerDialog.show();

            }
        });

        ExpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Generate_QR.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Exp.setText(i2 +"/"+ (i1+1) +"/"+ i);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = Value.getText().toString();
                String data2 = ValName.getText().toString();
                String data3 = Weight.getText().toString();
                String data4 = MRP.getText().toString();
                String data5 = Total.getText().toString();
                String data6 = Manufacture.getText().toString();
                String data7 = Exp.getText().toString();

                String data = (data1+"-"+data2+"-"+data3+"-"+data4+"-"+data5+"-"+data6+"-"+data7);

                if (!TextUtils.isEmpty(data1) && !TextUtils.isEmpty(data2) && !TextUtils.isEmpty(data3) && !TextUtils.isEmpty(data4) && !TextUtils.isEmpty(data5) && !TextUtils.isEmpty(data6) && !TextUtils.isEmpty(data7)) {
                    imageView.setVisibility(View.VISIBLE);
                    QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,550);
                    // Getting QR-Code as Bitmap
                    bitmap = qrgEncoder.getBitmap();
                    // Setting Bitmap to ImageView
                    imageView.setImageBitmap(bitmap);
                }
                else {
                    Toast.makeText(Generate_QR.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Generate_QR.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Do the file write
                    myDir.mkdirs();
                    String data = Value.getText().toString();
                    File file = new File (myDir, data);

                    try {
                        file.createNewFile();
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                   // QRGSaver qrgSaver = new QRGSaver();
                    //qrgSaver.save(String.valueOf(myDir), Value.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                } else {
                    // Request permission from the user
                    ActivityCompat.requestPermissions(Generate_QR.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }

            }
        });*/
    }
}