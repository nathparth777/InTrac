package com.example.invento;

public class Model {
    private String Add_By, Date_of_Adding, MRP, Name, Total_Items, Weight, flag, ID, NameSL, Subname, Manufacture_Date, Expiry_Date;

    private Model(){}

    private Model(String Add_By, String Date_of_Adding, String MRP, String Name, String Total_Items, String Weight, String flag, String ID, String NameSL, String Subname, String Manufacture_Date, String Expiry_Date){
        this.Add_By = Add_By;
        this.Date_of_Adding = Date_of_Adding;
        this.MRP = MRP;
        this.Name = Name;
        this.Total_Items = Total_Items;
        this.Weight = Weight;
        this.flag = flag;
        this.ID = ID;
        this.NameSL = NameSL;
        this.Subname = Subname;
        this.Manufacture_Date = Manufacture_Date;
        this.Expiry_Date = Expiry_Date;
    }

    public String getAdd_By() {
        return Add_By;
    }

    public void setAdd_By(String add_By) {
        Add_By = add_By;
    }

    public String getDate_of_Adding() {
        return Date_of_Adding;
    }

    public void setDate_of_Adding(String date_of_Adding) {
        Date_of_Adding = date_of_Adding;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTotal_Items() {
        return Total_Items;
    }

    public void setTotal_Items(String total_Items) {
        Total_Items = total_Items;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNameSL() {
        return NameSL;
    }

    public void setNameSL(String nameSL) {
        NameSL = nameSL;
    }

    public String getSubname() {
        return Subname;
    }

    public void setSubname(String subname) {
        Subname = subname;
    }

    public String getManufacture_Date() {
        return Manufacture_Date;
    }

    public void setManufacture_Date(String manufacture_Date) {
        Manufacture_Date = manufacture_Date;
    }

    public String getExpiry_Date() {
        return Expiry_Date;
    }

    public void setExpiry_Date(String expiry_Date) {
        Expiry_Date = expiry_Date;
    }
}
