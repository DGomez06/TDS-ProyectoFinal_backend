package com.tdsproject.apigateway.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Favorite {
    @Id
    @GeneratedValue
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    public Favorite(){}

    public Favorite(User user, Property property) {
        this.user = user;
        this.property = property;
    }

    public Integer getId() {
        return Id;
    }

    public User getUser() {
        return user;
    }

    public Property getProperty() {
        return property;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProperties(Property property) {
        this.property = property;
    }
}