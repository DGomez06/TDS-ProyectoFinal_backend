package com.tdsproject.apigateway.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Property {
    @Id
    @GeneratedValue
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
    private String address;
    @Column(columnDefinition = "varchar(20) default 'AVAILABLE'")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer size;
    private Integer rooms;
    private Integer bathrooms;
    private Double price;
    private String type;
    private Double latitude;
    private Double longitude;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property", cascade = CascadeType.ALL)
    public List<Images> images;


    public Property(){}

    public Property(
            User owner,
            String address,
            StatusEnum status,
            String description,
            Integer size,
            Integer rooms,
            Integer bathrooms,
            Double price,
            String type,
            Double latitude,
            Double longitude
    ) {
        this.owner = owner;
        this.address = address;
        this.status = status;
        this.description = description;
        this.size = size;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.price = price;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return Id;
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
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

    public List<Images> getImages() {
        return images;
    }
    public void setImages(List<Images> images) {
        this.images = images;
    }

    public void setLatitude(Double lat){
        this.latitude = lat;
    }

    public Double getLatitude(){
        return this.latitude;
    }

    public void setLongitude(Double lon){
        this.longitude = lon;
    }

    public Double getLongitude(){
        return this.longitude;
    }
}
