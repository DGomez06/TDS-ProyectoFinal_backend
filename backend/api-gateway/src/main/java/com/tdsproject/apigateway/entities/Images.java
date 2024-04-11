package com.tdsproject.apigateway.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Images {
    @Id
    @GeneratedValue
    private Integer Id;
    private String URL;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    public Images(){}

    public Images(String URL, Property property) {
        this.URL = URL;
        this.property = property;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Integer getId() {
        return Id;
    }

    public String getURL() {
        return URL;
    }

    public Property getProperty() {return property;}
    public void setProperty(Property property){
        this.property = property;
    }
}
