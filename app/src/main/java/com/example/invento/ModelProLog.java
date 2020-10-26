package com.example.invento;

public class ModelProLog {
    private String Date_of_Modify, Modify_by;

    private ModelProLog(){}

    private ModelProLog(String Date_of_Modify, String Modify_by){
        this.Date_of_Modify = Date_of_Modify;
        this.Modify_by = Modify_by;
    }

    public String getDate_of_Modify() {
        return Date_of_Modify;
    }

    public void setDate_of_Modify(String date_of_Modify) {
        Date_of_Modify = date_of_Modify;
    }

    public String getModify_by() {
        return Modify_by;
    }

    public void setModify_by(String modify_by) {
        Modify_by = modify_by;
    }
}
