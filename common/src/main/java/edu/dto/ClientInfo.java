package edu.dto;


import java.io.Serializable;


public class ClientInfo implements Serializable {

    private Integer id;
    private String name;

    public ClientInfo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
