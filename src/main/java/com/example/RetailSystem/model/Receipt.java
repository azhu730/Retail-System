package com.example.RetailSystem.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "RECEIPTS")
public class Receipt {

    @Id
    @GeneratedValue
    private Long receiptNo;

    @Column(name = "NUMBER_OF_ITEMS")
    private Integer numberOfItems;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "ITEMS")
    @ElementCollection(targetClass=Integer.class)
    private Set<Item> items;

    @Column(name = "RETURNED")
    @ElementCollection(targetClass=Integer.class)
    private Set<Item> returned;

    public Long getReceiptNo() {
        return this.receiptNo;
    }

    public Date getDate() {
        return this.getDate();
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public Set<Item> getReturned() {
        return this.returned;
    }

    public void setReceiptNo(Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void setReturned(Set<Item> returned) {
        this.returned = returned;
    }
}
