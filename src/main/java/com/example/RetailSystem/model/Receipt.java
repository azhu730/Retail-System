package com.example.RetailSystem.model;

import javax.persistence.*;
import java.util.Date;

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

    public Long getReceiptNo() {
        return this.receiptNo;
    }

    public Date getDate() {
        return this.getDate();
    }

    public void setReceiptNo() {
        this.receiptNo = receiptNo;
    }

    public void setDate() {
        this.date = date;
    }
}
