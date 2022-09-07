package com.example.RetailSystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    private Long dpci;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "ON_HAND")
    private Integer onHand;

    @Column(name = "ON_SALE")
    private Boolean onSale;

    @Column(name = "FLOOR_LOCATION")
    private String floorLocation;

    public Long getDpci() {
        return this.dpci;
    }

    public String getName() {
        return this.name;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getOnHand() {
        return this.onHand;
    }

    public Boolean getOnSale() {
        return this.onSale;
    }

    public String getFloorLocation() {
        return this.floorLocation;
    }

    public void setDpci(Long dpci) {
        this.dpci = dpci;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOnHand(Integer onHand) {
        this.onHand = onHand;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public void setFloorLocation(String floorLocation) {
        this.floorLocation = floorLocation;
    }
}
