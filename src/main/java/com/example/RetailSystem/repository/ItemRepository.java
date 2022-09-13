package com.example.RetailSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.RetailSystem.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    Optional<Item> findByDpci(String dpci);
    Optional<Item> findByName(String name);
    Optional<Item> findByUpc(String upc);
    Optional<Item> findByTcin(Integer tcin);

    List<Item> findByPriceLessThan(Double price);
    List<Item> findByPriceGreaterThan(Double price);
    List<Item> findByOnHandLessThan(Integer onHand);
    List<Item> findByOnHandGreaterThan(Integer onHand);
    List<Item> findByOnSaleTrue();
    List<Item> findByOnSaleFalse();
    List<Item> findByFloorLocation(String floorLocation);
    List<Item> findByDepartmentNo(Integer departmentNo);
    List<Item> findByClassNo(Integer classNo);
    List<Item> findByItemNo(Integer itemNo);
}
