package com.example.invento;

public class adddelete_model {

    private String Date, Item_Added, Item_Deleted;

    private adddelete_model(){};

    private adddelete_model(String Date, String Item_Added, String Item_Deleted){
        this.Date = Date;
        this.Item_Added = Item_Added;
        this.Item_Deleted = Item_Deleted;
    }

    public String getItem_Added() {
        return Item_Added;
    }

    public void setItem_Added(String item_Added) {
        Item_Added = item_Added;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getItem_Deleted() {
        return Item_Deleted;
    }

    public void setItem_Deleted(String item_Deleted) {
        Item_Deleted = item_Deleted;
    }
}
