package com.tdsproject.apigateway.entities;

import jakarta.persistence.*;

@Entity
public class Property {
    @Id
    @GeneratedValue
    public Integer id;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User owner;
    public String address;
    public Integer size;
    public Integer rooms;
    public Integer bathrooms;
    public Double price;
    public String type;

    public Property(){}

    public Property(User owner, String address, Integer size, Integer rooms, Integer bathrooms, Double price, String type) {
        this.owner = owner;
        this.address = address;
        this.size = size;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.price = price;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
