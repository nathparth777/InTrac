package com.example.invento;

public class searchmodify_model2 {
    private String Date, Activity, After_modify_MRP, After_modify_Name, After_modify_Quantity, Before_modify_MRP, Before_modify_Name, Before_modify_Quantity;

    private searchmodify_model2(){};

    private searchmodify_model2(String Date,String Activity,String After_modify_MRP,String After_modify_Name,String After_modify_Quantity,String Before_modify_MRP,String Before_modify_Name,String Before_modify_Quantity){
       this.Activity = Activity;
       this.Date = Date;
       this.After_modify_MRP = After_modify_MRP;
       this.After_modify_Name = After_modify_Name;
       this.After_modify_Quantity = After_modify_Quantity;
       this.Before_modify_MRP = Before_modify_MRP;
       this.Before_modify_Name = Before_modify_Name;
       this.Before_modify_Quantity = Before_modify_Quantity;

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getAfter_modify_MRP() {
        return After_modify_MRP;
    }

    public void setAfter_modify_MRP(String after_modify_MRP) {
        After_modify_MRP = after_modify_MRP;
    }

    public String getAfter_modify_Name() {
        return After_modify_Name;
    }

    public void setAfter_modify_Name(String after_modify_Name) {
        After_modify_Name = after_modify_Name;
    }

    public String getAfter_modify_Quantity() {
        return After_modify_Quantity;
    }

    public void setAfter_modify_Quantity(String after_modify_Quantity) {
        After_modify_Quantity = after_modify_Quantity;
    }

    public String getBefore_modify_MRP() {
        return Before_modify_MRP;
    }

    public void setBefore_modify_MRP(String before_modify_MRP) {
        Before_modify_MRP = before_modify_MRP;
    }

    public String getBefore_modify_Name() {
        return Before_modify_Name;
    }

    public void setBefore_modify_Name(String before_modify_Name) {
        Before_modify_Name = before_modify_Name;
    }

    public String getBefore_modify_Quantity() {
        return Before_modify_Quantity;
    }

    public void setBefore_modify_Quantity(String before_modify_Quantity) {
        Before_modify_Quantity = before_modify_Quantity;
    }
}
