package com.example.invento;

import android.app.Application;

public class Global extends Application {
    public String  data, ID, name, mrp, addby, DateAdding, flag, EamilId, Maufacture, Expiry;
    public int val;

    public String getData(){
        return data;
    }

    public void setData(String  d){
        data=d;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getAddby() {
        return addby;
    }

    public void setAddby(String addby) {
        this.addby = addby;
    }

    public String getDateAdding() {
        return DateAdding;
    }

    public void setDateAdding(String dateAdding) {
        DateAdding = dateAdding;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getEamilId() {
        return EamilId;
    }

    public void setEamilId(String eamilId) {
        EamilId = eamilId;
    }

    public String getMaufacture() {
        return Maufacture;
    }

    public void setMaufacture(String maufacture) {
        Maufacture = maufacture;
    }

    public String getExpiry() {
        return Expiry;
    }

    public void setExpiry(String expiry) {
        Expiry = expiry;
    }
}
