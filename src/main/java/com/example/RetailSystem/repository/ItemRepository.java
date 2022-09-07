package com.example.RetailSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.RetailSystem.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    Optional<Item> findByDpci(Long dpci);
    Optional<Item> findByName(String name);

    List<Item> findByPriceLessThan(Double price);
    List<Item> findByPriceGreaterThan(Double price);
    List<Item> findByOnHandLessThan(Integer onHand);
    List<Item> findByOnHandGreaterThan(Integer onHand);
    List<Item> findByOnSaleTrue();
    List<Item> findByOnSaleFalse();
    List<Item> findByFloorLocation(String floorLocation);
}
