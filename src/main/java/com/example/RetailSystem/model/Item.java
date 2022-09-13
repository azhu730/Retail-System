package com.example.RetailSystem.model;

import javax.persistence.*;

@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TCIN")
    private Integer tcin;

    @Column(name = "DPCI")
    private String dpci;

    @Column(name = "DEPARTMENT_NO")
    private Integer departmentNo;

    @Column(name = "CLASS_NO")
    private Integer classNo;

    @Column(name = "ITEM_NO")
    private Integer itemNo;

    @Column(name = "UPC")
    private String upc;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "ON_HAND")
    private Integer onHand;

    @Column(name = "ON_SALE")
    private Boolean onSale;

    @Column(name = "FLOOR_LOCATION")
    private String floorLocation;

    public Integer getTcin() {
        return this.tcin;
    }

    public String getDpci() {
        return this.dpci;
    }

    public Integer getDepartmentNo() {
        return this.departmentNo;
    }

    public Integer getClassNo() {
        return this.classNo;
    }

    public Integer getItemNo() {
        return this.itemNo;
    }

    public String getUpc() {
        return this.upc;
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

    public void setTcin(Integer tcin) {
        this.tcin = tcin;
    }

    public void setDpci(String dpci) {
        this.dpci = dpci;
    }

    public void setDepartmentNo(Integer departmentNo) {
        this.departmentNo = departmentNo;
    }

    public void setClassNo(Integer classNo) {
        this.classNo = classNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public void setUpc(String upc) {
        this.upc = upc;
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
