package com.tdsproject.apigateway.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonIgnore
    private User owner;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @JsonIgnore
    private User client;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    @Column(columnDefinition = "varchar(20) default 'IN_PROGRESS'")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private LocalDateTime createdAt;

    public Contract(){}
    public Contract(User owner, User client, Property property) {
        this.owner = owner;
        this.client = client;
        this.property = property;
        this.status = StatusEnum.IN_PROCESS;
        this.createdAt = LocalDateTime.now();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Integer getId() {
        return Id;
    }

    public User getOwner() {
        return owner;
    }

    public User getClient() {
        return client;
    }

    public Property getProperty() {
        return property;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
