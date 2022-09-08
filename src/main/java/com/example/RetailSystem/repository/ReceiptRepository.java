package com.example.RetailSystem.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.RetailSystem.model.Receipt;

public interface ReceiptRepository extends CrudRepository<Receipt, Integer> {
    Optional<Receipt> findByReceiptNo(Integer receiptNo);

    List<Receipt> findByDate(Date date);
}
