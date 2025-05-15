package com.alessandra_alessandro.ketchapp.models;

public class UserRecord {
    private int id;
    private String name;

    // Costruttore
    public UserRecord(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}