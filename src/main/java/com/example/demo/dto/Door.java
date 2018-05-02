package com.example.demo.dto;

public class Door {
    protected String id;
    protected String name;

    public Door(){ }

    public Door(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
