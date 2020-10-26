package com.example.invento;

public class employee_model {

    private String Address, Age, Contact, DOB, EmailId, Experience, LogVal, Name, Position;

    private employee_model(){};

    private employee_model(String Address, String Age, String Contact, String DOB, String EmailId, String Experience, String LogVal, String Name, String Position){
        this.Address = Address;
        this.Age = Age;
        this.Contact = Contact;
        this.DOB = DOB;
        this.EmailId = EmailId;
        this.Experience = Experience;
        this.LogVal = LogVal;
        this.Name = Name;
        this.Position = Position;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getLogVal() {
        return LogVal;
    }

    public void setLogVal(String logVal) {
        LogVal = logVal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }
}
