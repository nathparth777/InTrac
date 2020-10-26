package com.example.invento;

public class searchmodify_model {
    private String id;

    private searchmodify_model(){};

    private searchmodify_model(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
